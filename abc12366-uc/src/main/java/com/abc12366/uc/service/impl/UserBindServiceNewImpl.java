package com.abc12366.uc.service.impl;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.exception.DzsbServiceException;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.*;
import com.abc12366.uc.jrxt.model.util.XmlJavaParser;
import com.abc12366.uc.mapper.db1.UserBindMapper;
import com.abc12366.uc.mapper.db2.UserBindRoMapper;
import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHngs;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.model.order.bo.RegsAndNsrloginStatBO;
import com.abc12366.uc.model.tdps.TY21Xml2Object;
import com.abc12366.uc.service.RSAService;
import com.abc12366.uc.service.TodoTaskService;
import com.abc12366.uc.service.UserBindServiceNew;
import com.abc12366.uc.webservice.AcceptClient;
import com.abc12366.uc.wsbssoa.dto.AuthorizationDto;
import com.abc12366.uc.wsbssoa.response.HngsNsrLoginResponse;
import com.abc12366.uc.wsbssoa.service.MainService;
import com.abc12366.uc.wsbssoa.utils.MD5;
import com.abc12366.uc.wsbssoa.utils.RSAUtil;
import com.alibaba.fastjson.JSON;
import org.exolab.castor.xml.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.interfaces.RSAPublicKey;
import java.sql.Timestamp;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class UserBindServiceNewImpl implements UserBindServiceNew {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserBindServiceImpl.class);
    @Autowired
    private UserBindMapper userBindMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MainService mainService;

    @Autowired
    private AcceptClient client;

    @Autowired
    private RSAService rsaService;

    @Autowired
    private TodoTaskService todoTaskService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Resource(name = "redisTemplate")
    private ValueOperations<String, String> valueOperations;

    @Autowired
    private UserBindRoMapper userBindRoMapper;
    
    
    public String fwmmEncode(String code) throws Exception {
        String fristSbmm = new MD5(code).compute().toUpperCase();
        return new MD5(fristSbmm + TaskConstant.TDPS_LOGIN_PWD_APPOINT_CODE).compute().toUpperCase();
    }
	
	@Override
	public UserDzsbBO dzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request) throws Exception{
		//用户会员绑定企业数量限制
        String userId = Utils.getUserId(request);
        //bindLimit(userId);

        redisTemplate.delete(userId + "_DzsbList");

        //查看是否重复绑定
        UserDzsb queryParam = new UserDzsb();
        queryParam.setUserId(userId);
        queryParam.setNsrsbh(userDzsbInsertBO.getNsrsbhOrShxydm());
        queryParam.setShxydm(userDzsbInsertBO.getNsrsbhOrShxydm());
        List<UserDzsb> nsrxxboList = userBindMapper.selectListByUserIdAndNsrsbhOrShxydm(queryParam);
        if (nsrxxboList != null && nsrxxboList.size() >= 1) {
            throw new ServiceException(4632);
        }

        //调外系统接口获取电子申报绑定信息
        Map<String, String> map = new HashMap<>(16);
        map.put("serviceid", "TY21");
        map.put("nsrsbh", userDzsbInsertBO.getNsrsbhOrShxydm());
        String pwdDecode1 = rsaService.decodeStringFromJsNew(userDzsbInsertBO.getFwmm());
        String pwdDecode2 = fwmmEncode(pwdDecode1);
        map.put("fwmm", pwdDecode2);
        map.put("userid", userId);
        Map<String, String> resMap = client.process(map);
        LOGGER.info("{}", resMap);
        TY21Xml2Object ty21Object = analyzeXmlTY21(resMap, userDzsbInsertBO.getNsrsbhOrShxydm());
        LOGGER.info("{}", ty21Object);

        //查看是否重复绑定
        queryParam.setUserId(userId);
        queryParam.setNsrsbh(ty21Object.getY_NSRSBH());
        List<UserDzsb> nsrxxboList2 = userBindMapper.selectListByUserIdAndNsrsbh(queryParam);

        UserDzsb userDzsb = new UserDzsb();
        userDzsb.setId(Utils.uuid());
        Date date = new Date();
        userDzsb.setCreateTime(date);
        userDzsb.setLastUpdate(date);
        userDzsb.setStatus(true);
        userDzsb.setUserId(userId);
        userDzsb.setDjxh(ty21Object.getDJXH());
        userDzsb.setNsrsbh(ty21Object.getY_NSRSBH());
        userDzsb.setNsrmc(ty21Object.getNSRMC());
        userDzsb.setShxydm(ty21Object.getSHXYDM());
        userDzsb.setLastLoginTime(new Date());
        userDzsb.setNsrlx(ty21Object.getNSRLX());
        userDzsb.setSfgtjzh(ty21Object.getSFGTJZH());
        if (ty21Object.getSHXYDM() == null || "".equals(ty21Object.getSHXYDM().trim())) {
            userDzsb.setShxydm(ty21Object.getY_NSRSBH());
        }
        userDzsb.setSwjgMc(ty21Object.getSWJGMC());
        userDzsb.setSwjgDm(ty21Object.getSWJGDM());
        if (ty21Object.getRJDQR() != null && !"".equals(ty21Object.getRJDQR().trim())) {
            userDzsb.setExpireTime(DateUtils.strToDate(ty21Object.getRJDQR()));
        }
        if (ty21Object.getYQDQR() != null && !"".equals(ty21Object.getYQDQR().trim())) {
            userDzsb.setExpandExpireTime(DateUtils.strToDate(ty21Object.getYQDQR()));
        }
        userDzsb.setFrmc(ty21Object.getFRXM());
        userDzsb.setFrzjh(ty21Object.getFRZJH());
        userDzsb.setDjrq(ty21Object.getDJRQ());
        userDzsb.setBdgroup(userDzsbInsertBO.getBdgroup());
        userDzsb.setRemark(userDzsbInsertBO.getRemark());
        int result;
        if(nsrxxboList2==null||nsrxxboList2.size()==0){
            result = userBindMapper.dzsbBind(userDzsb);
        } else if(nsrxxboList2.size()==1){
            userDzsb.setId(nsrxxboList2.get(0).getId());
            result = userBindMapper.update(userDzsb);
        } else{
            userDzsb.setId(nsrxxboList2.get(0).getId());
            result = userBindMapper.update(userDzsb);
            nsrxxboList2.remove(nsrxxboList2.get(0));
            for(UserDzsb ud : nsrxxboList2){
                userBindMapper.deleteDzsb(ud.getId());
            }
        }
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userDzsb.toString());
            throw new ServiceException(4101);
        }
        UserDzsbBO userDzsbBO1 = new UserDzsbBO();
        BeanUtils.copyProperties(userDzsb, userDzsbBO1);

        //绑定税号任务埋点
        todoTaskService.doTask(userId, TaskConstant.SYS_TASK_COURSE_BDSH_CODE);

        return userDzsbBO1;
	}

	@Override
	public UserHngsBO hngsBind(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) {
		if (userHngsInsertBO == null) {
            LOGGER.warn("新增失败，参数：null");
            throw new ServiceException(4101);
        }

        //用户会员绑定企业数量限制
        String userId = Utils.getUserId(request);
        //bindLimit(userId);

        redisTemplate.delete(userId + "_HngsList");

        userHngsInsertBO.setUserId(Utils.getUserId(request));
        //查看是否重复绑定
        List<UserHngs> userHngsList = userBindMapper.userHngsListExist(userHngsInsertBO);
        if (userHngsList != null && userHngsList.size() >= 1) {
            throw new ServiceException(4632);
        }

        //访问网上报税系统
        HngsNsrLoginResponse hngsNsrLoginResponse = loginWsbsHngs(userHngsInsertBO, request);
        if (hngsNsrLoginResponse == null) {
            throw new ServiceException(4840);
        } else {
            if (!hngsNsrLoginResponse.isSuccess()) {
                throw new ServiceException(hngsNsrLoginResponse.getCode(), hngsNsrLoginResponse.getMsg());
            }
        }
        //办税员角色
        String bsyjs;
        switch (userHngsInsertBO.getRole().trim().toUpperCase()) {
            case "R0001":
                bsyjs = "办税员01";
                break;
            case "R0002":
                bsyjs = "办税员02";
                break;
            case "R0003":
                bsyjs = "办税员03";
                break;
            default:
                bsyjs = "办税员01";
        }

        UserHngs userHngs = new UserHngs();
        userHngs.setDjxh(hngsNsrLoginResponse.getDjxh());
        userHngs.setNsrsbh(hngsNsrLoginResponse.getNsrsbh());
        userHngs.setBsy(bsyjs);
        userHngs.setNsrmc(hngsNsrLoginResponse.getNsrmc());
        userHngs.setShxydm(hngsNsrLoginResponse.getNsrsbh());
        userHngs.setSwjgMc(hngsNsrLoginResponse.getZgswjmc());
        userHngs.setSwjgDm(hngsNsrLoginResponse.getZgswjDm());
        Date date = new Date();
        userHngs.setId(Utils.uuid());
        if ((hngsNsrLoginResponse.getGxruuid() != null && !"".equals(hngsNsrLoginResponse.getGxruuid().trim()))) {
            userHngs.setSmrzzt("已认证");
        } else {
            userHngs.setSmrzzt("未认证");
        }

        userHngs.setStatus(true);
        userHngs.setCreateTime(date);
        userHngs.setLastUpdate(date);
        userHngs.setUserId(Utils.getUserId(request));
        userHngs.setRoleId(hngsNsrLoginResponse.getRoleId());
        int result = userBindMapper.hngsBind(userHngs);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userHngs.toString());
            throw new ServiceException(4101);
        }
        UserHngsBO userHngsBO1 = new UserHngsBO();
        BeanUtils.copyProperties(userHngs, userHngsBO1);
        //绑定税号任务埋点
        todoTaskService.doTask(userId, TaskConstant.SYS_TASK_COURSE_BDSH_CODE);

        return userHngsBO1;
	}
	
	@SuppressWarnings("rawtypes")
	public TY21Xml2Object analyzeXmlTY21(Map resMap, String nsrsbh) throws ValidationException {
        if (resMap == null || resMap.isEmpty()) {
            throw new ServiceException(4629);
        }
        if (!"00000000".equals(resMap.get("rescode"))) {
            throw new DzsbServiceException((String) resMap.get("rescode"), (String) resMap.get("message"));
        }
        if (!resMap.containsKey("taxML_SSHDXX_" + nsrsbh + ".xml")) {
            throw new ServiceException(4634);
        }
        TY21Xml2Object object = new TY21Xml2Object();
        com.abc12366.uc.tdps.vo.TY21Response.JBXXCX jbxxcx;
        try {
            jbxxcx = (com.abc12366.uc.tdps.vo.TY21Response.JBXXCX) XmlJavaParser.parseXmlToObject(com.abc12366.uc
                    .tdps.vo.TY21Response.JBXXCX.class, String.valueOf(resMap.get("taxML_SSHDXX_" + nsrsbh + ".xml")));
        } catch (org.exolab.castor.xml.MarshalException e) {
            e.printStackTrace();
            throw new ServiceException(4633);
        }
        String cxjg = jbxxcx.getCXJG();
        if ("1".equals(cxjg)) {
            com.abc12366.uc.tdps.vo.TY21Response.MXXX[] mxxxes = jbxxcx.getMXXXS().getMXXX();
            for (com.abc12366.uc.tdps.vo.TY21Response.MXXX mx : mxxxes) {
                if ("LOGINTOKEN".equals(mx.getCODE())) {
                    object.setLOGINTOKEN(mx.getVALUE());
                }
                if ("DLSJ".equals(mx.getCODE())) {
                    object.setDLSJ(mx.getVALUE());
                }
                if ("Y_NSRSBH".equals(mx.getCODE())) {
                    object.setY_NSRSBH(mx.getVALUE());
                }
                if ("NSRMC".equals(mx.getCODE())) {
                    object.setNSRMC(mx.getVALUE());
                }
                if ("SHXYDM".equals(mx.getCODE())) {
                    object.setSHXYDM(mx.getVALUE());
                }
                if ("SWJGDM".equals(mx.getCODE())) {
                    object.setSWJGDM(mx.getVALUE());
                }
                if ("SWJGMC".equals(mx.getCODE())) {
                    object.setSWJGMC(mx.getVALUE());
                }
                if ("DJXH".equals(mx.getCODE())) {
                    object.setDJXH(mx.getVALUE());
                }
                if ("FRXM".equals(mx.getCODE())) {
                    object.setFRXM(mx.getVALUE());
                }
                if ("FRZJH".equals(mx.getCODE())) {
                    object.setFRZJH(mx.getVALUE());
                }
                if ("RJDQR".equals(mx.getCODE())) {
                    object.setRJDQR(mx.getVALUE());
                }
                if ("YQDQR".equals(mx.getCODE())) {
                    object.setYQDQR(mx.getVALUE());
                }
                if ("NSRLX".equals(mx.getCODE())) {
                    object.setNSRLX(mx.getVALUE());
                }
                if ("SFGTJZH".equals(mx.getCODE())) {
                    object.setSFGTJZH(mx.getVALUE());
                }
                if ("DJRQ".equals(mx.getCODE())) {
                    object.setDJRQ(mx.getVALUE());
                }
            }
        } else {
            throw new DzsbServiceException("9999", jbxxcx.getCWYY());
        }
        return object;
    }
	
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public HngsNsrLoginResponse loginWsbsHngs(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));
        headers.add(Constant.VERSION_HEAD, Constant.VERSION_1);
        headers.add(Constant.USER_TOKEN_HEAD, request.getHeader(Constant.USER_TOKEN_HEAD));
        String api = "/login";
        String url = SpringCtxHolder.getProperty("abc12366.message.url") + "/hngs/post?api=" + api;
        Map<String, Object> requestBody = new HashMap<>(16);
        requestBody.put("nsrsbh", userHngsInsertBO.getBsy());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        requestBody.put("timestamp", Long.toString(timestamp.getTime()));
        requestBody.put("roleId", userHngsInsertBO.getRole());
        String nsrsbh = userHngsInsertBO.getBsy().trim().toUpperCase();
        String pw;
        try {
            pw = Utils.md5(rsaService.decodeStringFromJsNew(userHngsInsertBO.getPassword()));
            RSAPublicKey pbk = (RSAPublicKey) mainService.getRSAPublicKey(request);
            pw = new String(RSAUtil.encrypt(pbk, new MD5(pw + timestamp.getTime()).compute().getBytes
                    ("iso-8859-1")), "iso-8859-1");
            nsrsbh = new String(RSAUtil.encrypt(pbk, (timestamp.getTime() + nsrsbh).getBytes("iso-8859-1")),
                    "iso-8859-1");
        } catch (Exception e) {
            String msg = "获取公钥并加密时异常。";
            LOGGER.error(msg, e);
            throw new ServiceException(4192);
        }
        requestBody.put("djm", pw);
        requestBody.put("nsrsbh", nsrsbh);
        //生成伪密码
        Random rd = new Random();
        int randomInt = rd.nextInt(10000);
        requestBody.put("p", new MD5(Integer.toString(randomInt)).compute());

        HttpEntity requestEntity = new HttpEntity(requestBody, headers);
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (RestTemplateUtil.isExchangeSuccessful(responseEntity)) {
            HngsNsrLoginResponse nsrLoginResponse = JSON.parseObject(String.valueOf(responseEntity.getBody()),
                    HngsNsrLoginResponse.class);
            if (nsrLoginResponse == null) {
                throw new ServiceException(4840);
            } else {
                if (!nsrLoginResponse.isSuccess()) {
                    throw new ServiceException(nsrLoginResponse.getCode(), nsrLoginResponse.getMsg());
                }
            }
            if (nsrLoginResponse.getMenuList() != null && nsrLoginResponse.getMenuList().size() > 0) {
                List<AuthorizationDto> authList = nsrLoginResponse.getMenuList();
                List<AuthorizationDto> filteredAuthList = authList.stream().filter(auth -> auth.getYyfwDm().trim()
                        .startsWith("FU")).collect(Collectors.toList());
                nsrLoginResponse.setMenuList(filteredAuthList);
            }
            return nsrLoginResponse;
        }
        return null;
    }

	@Override
	public TY21Xml2Object nsrLogin(NsrLogin login, HttpServletRequest request) throws Exception{
		LOGGER.info("{}", login);
        String userId = Utils.getUserId(request);
        Map<String, String> map = new HashMap<>(16);
        map.put("serviceid", "TY21");
        map.put("nsrsbh", login.getNsrsbhOrShxydm());
        String pwdDecode1 = rsaService.decodeStringFromJsNew(login.getFwmm());
        String pwdDecode2 = fwmmEncode(pwdDecode1);
        map.put("fwmm", pwdDecode2);
        map.put("userid", userId);
        Map<String, String> resMap = client.process(map);
        LOGGER.info("{}", resMap);

        TY21Xml2Object ty21Object = analyzeXmlTY21(resMap, login.getNsrsbhOrShxydm());
        LOGGER.info("{}", ty21Object);
        //更新用户绑定信息
        UserDzsb queryParam = new UserDzsb();
        queryParam.setUserId(userId);
        queryParam.setNsrsbh(ty21Object.getY_NSRSBH());
        List<UserDzsb> nsrxxboList2 = userBindMapper.selectListByUserIdAndNsrsbh(queryParam);
        if(nsrxxboList2.size()==1){
            updateDzsb(nsrxxboList2.get(0).getId(),userId, ty21Object);
        } else if(nsrxxboList2.size()>1){
            updateDzsb(nsrxxboList2.get(0).getId(),userId, ty21Object);
            nsrxxboList2.remove(nsrxxboList2.get(0));
            for(UserDzsb ud : nsrxxboList2){
                userBindMapper.deleteDzsb(ud.getId());
            }
        }
        //统计纳税人登录次数
        recordNsrLoginTimes();
        return ty21Object;
	}
	
	
	
    public UserDzsb updateDzsb(String id,String userId, TY21Xml2Object ty21Object) {
        redisTemplate.delete(userId + "_DzsbList");

        if (StringUtils.isEmpty(userId) || ty21Object == null || StringUtils.isEmpty(ty21Object.getY_NSRSBH()) ||
                StringUtils.isEmpty(ty21Object.getDJXH())) {
            return null;
        }
        //更新用户绑定信息
        UserDzsb userDzsb = new UserDzsb();
        Date date = new Date();
        userDzsb.setId(id);
        userDzsb.setStatus(true);
        userDzsb.setLastUpdate(date);
        userDzsb.setUserId(userId);
        userDzsb.setDjxh(ty21Object.getDJXH());
        userDzsb.setNsrsbh(ty21Object.getY_NSRSBH());
        userDzsb.setNsrmc(ty21Object.getNSRMC());
        userDzsb.setShxydm(ty21Object.getSHXYDM());
        userDzsb.setNsrlx(ty21Object.getNSRLX());
        userDzsb.setSfgtjzh(ty21Object.getSFGTJZH());
        if (ty21Object.getSHXYDM() == null || "".equals(ty21Object.getSHXYDM().trim())) {
            userDzsb.setShxydm(ty21Object.getY_NSRSBH());
        }
        userDzsb.setSwjgMc(ty21Object.getSWJGMC());
        userDzsb.setSwjgDm(ty21Object.getSWJGDM());
        if (ty21Object.getRJDQR() != null && !"".equals(ty21Object.getRJDQR().trim())) {
            userDzsb.setExpireTime(DateUtils.strToDate(ty21Object.getRJDQR()));
        }
        if (ty21Object.getYQDQR() != null && !"".equals(ty21Object.getYQDQR().trim())) {
            userDzsb.setExpandExpireTime(DateUtils.strToDate(ty21Object.getYQDQR()));
        }
        userDzsb.setFrmc(ty21Object.getFRXM());
        userDzsb.setFrzjh(ty21Object.getFRZJH());
        userDzsb.setDjrq(ty21Object.getDJRQ());
        userBindMapper.update(userDzsb);
        return userDzsb;
    }

	@Override
	public UserDzsbBO cszjdzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request)  throws Exception{
		//用户会员绑定企业数量限制
        String userId = Utils.getUserId(request);
        //bindLimit(userId);

        redisTemplate.delete(userId + "_DzsbList");

        //查看是否重复绑定
        UserDzsb queryParam = new UserDzsb();
        queryParam.setUserId(userId);
        queryParam.setNsrsbh(userDzsbInsertBO.getNsrsbhOrShxydm());
        queryParam.setShxydm(userDzsbInsertBO.getNsrsbhOrShxydm());
        List<UserDzsb> nsrxxboList = userBindMapper.selectListByUserIdAndNsrsbhOrShxydm(queryParam);
        if (nsrxxboList != null && nsrxxboList.size() >= 1) {
            throw new ServiceException(4632);
        }

        //调外系统接口获取电子申报绑定信息
        Map<String, String> map = new HashMap<>(16);
        map.put("serviceid", "TY21");
        map.put("nsrsbh", userDzsbInsertBO.getNsrsbhOrShxydm());
        String pwdDecode1 = rsaService.decodenNew(userDzsbInsertBO.getFwmm());
        String pwdDecode2 = fwmmEncode(pwdDecode1);
        map.put("fwmm", pwdDecode2);
        map.put("userid", userId);
        Map<String, String> resMap = client.process(map);
        LOGGER.info("{}", resMap);
        TY21Xml2Object ty21Object = analyzeXmlTY21(resMap, userDzsbInsertBO.getNsrsbhOrShxydm());
        LOGGER.info("{}", ty21Object);

        //查看是否重复绑定
        queryParam.setUserId(userId);
        queryParam.setNsrsbh(ty21Object.getY_NSRSBH());
        List<UserDzsb> nsrxxboList2 = userBindMapper.selectListByUserIdAndNsrsbh(queryParam);

        UserDzsb userDzsb = new UserDzsb();
        userDzsb.setId(Utils.uuid());
        Date date = new Date();
        userDzsb.setCreateTime(date);
        userDzsb.setLastUpdate(date);
        userDzsb.setStatus(true);
        userDzsb.setUserId(userId);
        userDzsb.setDjxh(ty21Object.getDJXH());
        userDzsb.setNsrsbh(ty21Object.getY_NSRSBH());
        userDzsb.setNsrmc(ty21Object.getNSRMC());
        userDzsb.setShxydm(ty21Object.getSHXYDM());
        userDzsb.setLastLoginTime(new Date());
        userDzsb.setNsrlx(ty21Object.getNSRLX());
        userDzsb.setSfgtjzh(ty21Object.getSFGTJZH());
        if (ty21Object.getSHXYDM() == null || "".equals(ty21Object.getSHXYDM().trim())) {
            userDzsb.setShxydm(ty21Object.getY_NSRSBH());
        }
        userDzsb.setSwjgMc(ty21Object.getSWJGMC());
        userDzsb.setSwjgDm(ty21Object.getSWJGDM());
        if (ty21Object.getRJDQR() != null && !"".equals(ty21Object.getRJDQR().trim())) {
            userDzsb.setExpireTime(DateUtils.strToDate(ty21Object.getRJDQR()));
        }
        if (ty21Object.getYQDQR() != null && !"".equals(ty21Object.getYQDQR().trim())) {
            userDzsb.setExpandExpireTime(DateUtils.strToDate(ty21Object.getYQDQR()));
        }
        userDzsb.setFrmc(ty21Object.getFRXM());
        userDzsb.setFrzjh(ty21Object.getFRZJH());
        userDzsb.setDjrq(ty21Object.getDJRQ());
        userDzsb.setBdgroup(userDzsbInsertBO.getBdgroup());
        userDzsb.setRemark(userDzsbInsertBO.getRemark());
        int result;
        if(nsrxxboList2==null||nsrxxboList2.size()==0){
            result = userBindMapper.dzsbBind(userDzsb);
        } else if(nsrxxboList2.size()==1){
            userDzsb.setId(nsrxxboList2.get(0).getId());
            result = userBindMapper.update(userDzsb);
        } else{
            userDzsb.setId(nsrxxboList2.get(0).getId());
            result = userBindMapper.update(userDzsb);
            nsrxxboList2.remove(nsrxxboList2.get(0));
            for(UserDzsb ud : nsrxxboList2){
                userBindMapper.deleteDzsb(ud.getId());
            }
        }
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userDzsb.toString());
            throw new ServiceException(4101);
        }
        UserDzsbBO userDzsbBO1 = new UserDzsbBO();
        BeanUtils.copyProperties(userDzsb, userDzsbBO1);

        //绑定税号任务埋点
        todoTaskService.doTask(userId, TaskConstant.SYS_TASK_COURSE_BDSH_CODE);

        return userDzsbBO1;
	}

	@Override
	public UserHngsBO cszjhngsBind(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) {
		if (userHngsInsertBO == null) {
            LOGGER.warn("新增失败，参数：null");
            throw new ServiceException(4101);
        }

        //用户会员绑定企业数量限制
        String userId = Utils.getUserId(request);
        //bindLimit(userId);

        redisTemplate.delete(userId + "_HngsList");

        userHngsInsertBO.setUserId(Utils.getUserId(request));
        //查看是否重复绑定
        List<UserHngs> userHngsList = userBindMapper.userHngsListExist(userHngsInsertBO);
        if (userHngsList != null && userHngsList.size() >= 1) {
            throw new ServiceException(4632);
        }

        //访问网上报税系统
        HngsNsrLoginResponse hngsNsrLoginResponse = cszjloginWsbsHngs(userHngsInsertBO, request);
        if (hngsNsrLoginResponse == null) {
            throw new ServiceException(4840);
        } else {
            if (!hngsNsrLoginResponse.isSuccess()) {
                throw new ServiceException(hngsNsrLoginResponse.getCode(), hngsNsrLoginResponse.getMsg());
            }
        }
        //办税员角色
        String bsyjs;
        switch (userHngsInsertBO.getRole().trim().toUpperCase()) {
            case "R0001":
                bsyjs = "办税员01";
                break;
            case "R0002":
                bsyjs = "办税员02";
                break;
            case "R0003":
                bsyjs = "办税员03";
                break;
            default:
                bsyjs = "办税员01";
        }

        UserHngs userHngs = new UserHngs();
        userHngs.setDjxh(hngsNsrLoginResponse.getDjxh());
        userHngs.setNsrsbh(hngsNsrLoginResponse.getNsrsbh());
        userHngs.setBsy(bsyjs);
        userHngs.setNsrmc(hngsNsrLoginResponse.getNsrmc());
        userHngs.setShxydm(hngsNsrLoginResponse.getNsrsbh());
        userHngs.setSwjgMc(hngsNsrLoginResponse.getZgswjmc());
        userHngs.setSwjgDm(hngsNsrLoginResponse.getZgswjDm());
        Date date = new Date();
        userHngs.setId(Utils.uuid());
        if ((hngsNsrLoginResponse.getGxruuid() != null && !"".equals(hngsNsrLoginResponse.getGxruuid().trim()))) {
            userHngs.setSmrzzt("已认证");
        } else {
            userHngs.setSmrzzt("未认证");
        }

        userHngs.setStatus(true);
        userHngs.setCreateTime(date);
        userHngs.setLastUpdate(date);
        userHngs.setUserId(Utils.getUserId(request));
        userHngs.setRoleId(hngsNsrLoginResponse.getRoleId());
        int result = userBindMapper.hngsBind(userHngs);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userHngs.toString());
            throw new ServiceException(4101);
        }
        UserHngsBO userHngsBO1 = new UserHngsBO();
        BeanUtils.copyProperties(userHngs, userHngsBO1);
        //绑定税号任务埋点
        todoTaskService.doTask(userId, TaskConstant.SYS_TASK_COURSE_BDSH_CODE);

        return userHngsBO1;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HngsNsrLoginResponse cszjloginWsbsHngs(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.APP_TOKEN_HEAD, request.getHeader(Constant.APP_TOKEN_HEAD));
        headers.add(Constant.VERSION_HEAD, Constant.VERSION_1);
        headers.add(Constant.USER_TOKEN_HEAD, request.getHeader(Constant.USER_TOKEN_HEAD));
        String api = "/login";
        String url = SpringCtxHolder.getProperty("abc12366.message.url") + "/hngs/post?api=" + api;
        Map<String, Object> requestBody = new HashMap<>(16);
        requestBody.put("nsrsbh", userHngsInsertBO.getBsy());
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        requestBody.put("timestamp", Long.toString(timestamp.getTime()));
        requestBody.put("roleId", userHngsInsertBO.getRole());
        String nsrsbh = userHngsInsertBO.getBsy().trim().toUpperCase();
        String pw;
        try {
            pw = Utils.md5(rsaService.decodenNew(userHngsInsertBO.getPassword()));
            RSAPublicKey pbk = (RSAPublicKey) mainService.getRSAPublicKey(request);
            pw = new String(RSAUtil.encrypt(pbk, new MD5(pw + timestamp.getTime()).compute().getBytes
                    ("iso-8859-1")), "iso-8859-1");
            nsrsbh = new String(RSAUtil.encrypt(pbk, (timestamp.getTime() + nsrsbh).getBytes("iso-8859-1")),
                    "iso-8859-1");
        } catch (Exception e) {
            String msg = "获取公钥并加密时异常。";
            LOGGER.error(msg, e);
            throw new ServiceException(4192);
        }
        requestBody.put("djm", pw);
        requestBody.put("nsrsbh", nsrsbh);
        //生成伪密码
        Random rd = new Random();
        int randomInt = rd.nextInt(10000);
        requestBody.put("p", new MD5(Integer.toString(randomInt)).compute());

        HttpEntity requestEntity = new HttpEntity(requestBody, headers);
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (RestTemplateUtil.isExchangeSuccessful(responseEntity)) {
            HngsNsrLoginResponse nsrLoginResponse = JSON.parseObject(String.valueOf(responseEntity.getBody()),
                    HngsNsrLoginResponse.class);
            if (nsrLoginResponse == null) {
                throw new ServiceException(4840);
            } else {
                if (!nsrLoginResponse.isSuccess()) {
                    throw new ServiceException(nsrLoginResponse.getCode(), nsrLoginResponse.getMsg());
                }
            }
            if (nsrLoginResponse.getMenuList() != null && nsrLoginResponse.getMenuList().size() > 0) {
                List<AuthorizationDto> authList = nsrLoginResponse.getMenuList();
                List<AuthorizationDto> filteredAuthList = authList.stream().filter(auth -> auth.getYyfwDm().trim()
                        .startsWith("FU")).collect(Collectors.toList());
                nsrLoginResponse.setMenuList(filteredAuthList);
            }
            return nsrLoginResponse;
        }
        return null;
    }

	@Override
	public TY21Xml2Object cszjnsrLoginShb(NsrLogin login, HttpServletRequest request) throws Exception {
		LOGGER.info("{}", login);
        String userId = Utils.getUserId(request);
        Map<String, String> map = new HashMap<>(16);
        map.put("serviceid", "TY21");
        map.put("nsrsbh", login.getNsrsbhOrShxydm());
        String pwdDecode1 = rsaService.decodenNew(login.getFwmm());
        String pwdDecode2 = fwmmEncode(pwdDecode1);
        map.put("fwmm", pwdDecode2);
        map.put("userid", userId);
        Map<String, String> resMap = client.process(map);
        LOGGER.info("{}", resMap);

        TY21Xml2Object ty21Object = analyzeXmlTY21(resMap, login.getNsrsbhOrShxydm());
        LOGGER.info("{}", ty21Object);
        //更新用户绑定信息
        UserDzsb queryParam = new UserDzsb();
        queryParam.setUserId(userId);
        queryParam.setNsrsbh(ty21Object.getY_NSRSBH());
        List<UserDzsb> nsrxxboList2 = userBindMapper.selectListByUserIdAndNsrsbh(queryParam);
        if(nsrxxboList2.size()==1){
            updateDzsb(nsrxxboList2.get(0).getId(),userId, ty21Object);
        } else if(nsrxxboList2.size()>1){
            updateDzsb(nsrxxboList2.get(0).getId(),userId, ty21Object);
            nsrxxboList2.remove(nsrxxboList2.get(0));
            for(UserDzsb ud : nsrxxboList2){
                userBindMapper.deleteDzsb(ud.getId());
            }
        }
        //统计纳税人登录次数
        recordNsrLoginTimes();
        return ty21Object;
	}

	@Override
	public HngsNsrLoginResponse nsrLoginDzsj(UserHngsInsertBO login, HttpServletRequest request) {
		HngsNsrLoginResponse loginResponse = cszjloginWsbsHngs(login, request);
        //更新绑定关系
        if (loginResponse != null) {

            String userId = Utils.getUserId(request);
            if (redisTemplate.hasKey(userId + "_HngsList")) {
                redisTemplate.delete(userId + "_HngsList");
            }

            UserHngs userHngs = new UserHngs();
            userHngs.setUserId(userId);
            userHngs.setDjxh(loginResponse.getDjxh());
            userHngs.setNsrsbh(loginResponse.getNsrsbh());
            userHngs.setNsrmc(loginResponse.getNsrmc());
            userHngs.setShxydm(loginResponse.getNsrsbh());
            userHngs.setSwjgDm(loginResponse.getZgswjDm());
            userHngs.setSwjgMc(loginResponse.getZgswjmc());
            userHngs.setLastUpdate(new Date());
            userHngs.setRoleId(loginResponse.getRoleId());
            //办税员角色
            String bsyjs;
            switch (loginResponse.getRoleId().trim().toUpperCase()) {
                case "R0001":
                    bsyjs = "办税员01";
                    break;
                case "R0002":
                    bsyjs = "办税员02";
                    break;
                case "R0003":
                    bsyjs = "办税员03";
                    break;
                default:
                    bsyjs = "办税员01";
            }
            userHngs.setBsy(bsyjs);
            userHngs.setSmrzzt(StringUtils.isEmpty(loginResponse.getGxruuid()) ? "未认证" : "已认证");
            userHngs.setStatus(true);
            userBindMapper.updateHngs(userHngs);
        }
        return loginResponse;
	}

    @Override
    public RegsAndNsrloginStatBO staRegsAndNsrlogins() {
        return userBindRoMapper.staRegsAndNsrlogins();
    }

    @Override
    public void dzsbnsrLoginTimesDay() {
        String dayKey = RedisConstant.DZSBNSR_LOGIN_TIMES_DAY;
        if (redisTemplate.hasKey(dayKey)) {
            valueOperations.increment(dayKey, 1L);
        } else {
            redisTemplate.delete(dayKey);
            redisTemplate.opsForValue().set(dayKey, "1", DateUtils.milliSecondsBetween(new Date(), DateUtils.getFirstHourOfLastDay()), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public void nsrLoginTimesMonth() {
        String monthKey = RedisConstant.NSR_LOGIN_TIMES_MONTH;
        if (redisTemplate.hasKey(monthKey)) {
            valueOperations.increment(monthKey, 1L);
        } else {
            redisTemplate.delete(monthKey);
            redisTemplate.opsForValue().set(monthKey, "1", DateUtils.milliSecondsBetween(new Date(), DateUtils.getFirstDayOfLastMonth()), TimeUnit.MILLISECONDS);
        }
    }

    @Override
    public int getDzsbnsrLoginTimesDay() {
        String dayKey = RedisConstant.DZSBNSR_LOGIN_TIMES_DAY;
        return redisTemplate.hasKey(dayKey) ? Integer.parseInt(valueOperations.get(dayKey)) : 0;
    }

    @Override
    public int getNnsrLoginTimesMonth() {
        String monthKey = RedisConstant.NSR_LOGIN_TIMES_MONTH;
        return redisTemplate.hasKey(monthKey) ? Integer.parseInt(valueOperations.get(monthKey)) : 0;
    }

    @Override
    public void recordNsrLoginTimes(){
        try{
            //redis加入电子申报纳税人一天内登录次数统计
            dzsbnsrLoginTimesDay();
            //redis加入纳税人一个月内登录次数统计
            nsrLoginTimesMonth();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
