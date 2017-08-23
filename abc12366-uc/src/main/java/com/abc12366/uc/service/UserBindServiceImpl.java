package com.abc12366.uc.service;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Properties;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.tdps.TY21Xml2Object;
import com.abc12366.uc.jrxt.model.util.XmlJavaParser;
import com.abc12366.uc.mapper.db1.UserBindMapper;
import com.abc12366.uc.mapper.db2.UserBindRoMapper;
import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHnds;
import com.abc12366.uc.model.UserHngs;
import com.abc12366.uc.model.abc4000.NSRXXBO;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.tdps.vo.CrmnsrmmGxResponse.NSRMMGX;
import com.abc12366.uc.tdps.vo.nsraqxxSzResponse.XGJG;
import com.abc12366.uc.tdps.vo.nsraqxxSzResponse.XGJGS;
import com.abc12366.uc.util.DateUtils;
import com.abc12366.uc.util.UserUtil;
import com.abc12366.uc.webservice.AcceptClient;
import com.abc12366.uc.wsbssoa.dto.AuthorizationDto;
import com.abc12366.uc.wsbssoa.response.HngsAppLoginResponse;
import com.abc12366.uc.wsbssoa.response.HngsNsrLoginResponse;
import com.abc12366.uc.wsbssoa.service.MainService;
import com.abc12366.uc.wsbssoa.utils.MD5;
import com.abc12366.uc.wsbssoa.utils.RSAUtil;
import com.abc12366.uc.wsbssoa.utils.soaUtil;
import com.alibaba.fastjson.JSON;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.interfaces.RSAPublicKey;
import java.sql.Timestamp;
import java.util.*;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
@Service
public class UserBindServiceImpl implements UserBindService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBindServiceImpl.class);
    private static Properties properties = new Properties("application.properties");
    @Autowired
    private UserBindMapper userBindMapper;
    @Autowired
    private UserBindRoMapper userBindRoMapper;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MainService mainService;

    @Autowired
    private AcceptClient client;

    @Override
    public UserDzsbBO dzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request) throws Exception {
        if (userDzsbInsertBO == null) {
            LOGGER.warn("新增失败，参数：null");
            throw new ServiceException(4101);
        }

        //查看是否重复绑定
        String userId = UserUtil.getUserId(request);
        UserDzsb queryParam = new UserDzsb();
        queryParam.setUserId(userId);
        queryParam.setNsrsbh(userDzsbInsertBO.getNsrsbhOrShxydm());
        queryParam.setShxydm(userDzsbInsertBO.getNsrsbhOrShxydm());
        List<NSRXXBO> nsrxxboList = userBindRoMapper.selectListByUserIdAndNsrsbhOrShxydm(queryParam);
        if (nsrxxboList != null && nsrxxboList.size() >= 1) {
            throw new ServiceException(4632);
        }

        //调外系统接口获取电子申报绑定信息
        Map<String, String> map = new HashMap<>();
        map.put("serviceid", "TY21");
        map.put("nsrsbh", userDzsbInsertBO.getNsrsbhOrShxydm());
        map.put("fwmm", fwmmEncode(userDzsbInsertBO.getFwmm()));
        map.put("userid", userId);
        Map<String, String> resMap = client.process(map);
        TY21Xml2Object ty21Object = analyzeXmlTY21(resMap, userDzsbInsertBO.getNsrsbhOrShxydm());


        //查看是否重复绑定
        queryParam.setUserId(userId);
        queryParam.setNsrsbh(ty21Object.getY_NSRSBH());
        queryParam.setShxydm(ty21Object.getSHXYDM());
        List<NSRXXBO> nsrxxboList2 = userBindRoMapper.selectListByUserIdAndNsrsbhOrShxydm(queryParam);
        if (nsrxxboList2 != null && nsrxxboList2.size() >= 1) {
            throw new ServiceException(4632);
        }

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
        userDzsb.setSwjgMc(ty21Object.getSWJGMC());
        userDzsb.setSwjgDm(ty21Object.getSWJGDM());
        userDzsb.setExpireTime(DateUtils.StrToDate(ty21Object.getRJDQR()));
        userDzsb.setExpandExpireTime(DateUtils.StrToDate(ty21Object.getYQDQR()));
        userDzsb.setFrmc(ty21Object.getFRXM());
        userDzsb.setFrzjh(ty21Object.getFRZJH());
        int result = userBindMapper.dzsbBind(userDzsb);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userDzsb.toString());
            throw new ServiceException(4101);
        }
        UserDzsbBO userDzsbBO1 = new UserDzsbBO();
        BeanUtils.copyProperties(userDzsb, userDzsbBO1);
        return userDzsbBO1;
    }

    //登录网上报税接口
    @Override
    public HngsNsrLoginResponse loginWsbsHngs(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) throws
            Exception {
        HngsAppLoginResponse hngsAppLoginResponse = appLoginWsbs(request);
        if (hngsAppLoginResponse != null) {
            String url = properties.getValue("wsbssoa.hngs.url") + "/login";
            HttpHeaders headers = new HttpHeaders();
            headers.add("accessToken", (String) request.getAttribute("accessToken"));
            headers.add("Content-Type", "application/json");

            Map<String, Object> requestBody = new HashMap<>();

            requestBody.put("nsrsbh", userHngsInsertBO.getBsy());
            Timestamp timestamp = new Timestamp(new Date().getTime());
            requestBody.put("timestamp", Long.toString(timestamp.getTime()));
            requestBody.put("roleId", userHngsInsertBO.getRole());
            String nsrsbh = userHngsInsertBO.getBsy().trim().toUpperCase();
            String pw = Utils.md5(userHngsInsertBO.getPassword());

            try {
                RSAPublicKey pbk = (RSAPublicKey) mainService.getRSAPublicKey(request);
                pw = new String(RSAUtil.encrypt(pbk, new MD5(pw + timestamp.getTime()).compute().getBytes
                        ("iso-8859-1")), "iso-8859-1");
                nsrsbh = new String(RSAUtil.encrypt(pbk, (timestamp.getTime() + nsrsbh).getBytes("iso-8859-1")),
                        "iso-8859-1");
//                nsrsbh = mainService.RSAEncrypt(request, new MD5(nsrsbh + timestamp.getTime()).compute());
//                pw = mainService.RSAEncrypt(request, new MD5(pw + timestamp.getTime()).compute());
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
            if (soaUtil.isExchangeSuccessful(responseEntity)) {
                HngsNsrLoginResponse nsrLoginResponse = JSON.parseObject(String.valueOf(responseEntity.getBody()), HngsNsrLoginResponse.class);
                if (nsrLoginResponse != null && nsrLoginResponse.getMenuList() != null && nsrLoginResponse.getMenuList().size() > 0) {
                    List<AuthorizationDto> authList = nsrLoginResponse.getMenuList();
                    List<AuthorizationDto> filteredAuthList = new ArrayList<>();
                    for (int i = 0; i < authList.size(); i++) {
                        AuthorizationDto auth = authList.get(i);
                        if (auth.getYyfwDm().trim().startsWith("FU")) {
                            filteredAuthList.add(auth);
                        }
                    }
                    nsrLoginResponse.setMenuList(filteredAuthList);
                }
                return nsrLoginResponse;
            }
        }
        return null;
    }

    private HngsAppLoginResponse appLoginWsbs(HttpServletRequest request) throws IOException {
        String url = properties.getValue("wsbssoa.hngs.app.login.url");
        HttpHeaders headers = new HttpHeaders();

        Map<String, Object> requestBody = new HashMap<>();
        String appId = properties.getValue("APPID");
        String secret = properties.getValue("SECRET");
        requestBody.put("appId", appId);
        requestBody.put("secret", secret);

//        String requestBody = "{\"appId\":\"ETAX_PC\",\"secret\":\"3A6ABF6B62EA0190E053550C483DD05A\"}";

        HttpEntity requestEntity = new HttpEntity(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        if (soaUtil.isExchangeSuccessful(responseEntity)) {
            HngsAppLoginResponse hngsAppLoginResponse = JSON.parseObject(String.valueOf(responseEntity.getBody()),
                    HngsAppLoginResponse.class);
            request.setAttribute("accessToken", hngsAppLoginResponse.getAccessToken());
            return hngsAppLoginResponse;
        }
        return null;
    }

    @Override
    public boolean dzsbUnbind(String id) {
        UserDzsb userDzsb = userBindRoMapper.userDzsbSelectById(id);
        if (userDzsb == null) {
            LOGGER.warn("修改失败，参数：null");
            throw new ServiceException(4102);
        }
        int result = userBindMapper.dzsbUnbind(id);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：{}" + userDzsb.toString());
            throw new ServiceException(4102);
        }
        return true;
    }

    @Override
    public UserHngsBO hngsBind(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) throws Exception {
        if (userHngsInsertBO == null) {
            LOGGER.warn("新增失败，参数：null");
            throw new ServiceException(4101);
        }

        userHngsInsertBO.setUserId(UserUtil.getUserId(request));
        //查看是否重复绑定
        List<UserHngs> userHngsList = userBindRoMapper.userHngsListExist(userHngsInsertBO);
        if (userHngsList != null && userHngsList.size() >= 1) {
            throw new ServiceException(4632);
        }

        //访问网上报税系统
        HngsNsrLoginResponse hngsNsrLoginResponse = loginWsbsHngs(userHngsInsertBO, request);
        if (hngsNsrLoginResponse == null || !hngsNsrLoginResponse.isSuccess()) {
            throw new ServiceException(4840);
        }

        UserHngs userHngs = new UserHngs();
        userHngs.setDjxh(hngsNsrLoginResponse.getDjxh());
        userHngs.setNsrsbh(hngsNsrLoginResponse.getNsrsbh());
        userHngs.setBsy(userHngsInsertBO.getBsy());
        userHngs.setNsrmc(hngsNsrLoginResponse.getNsrmc());
        userHngs.setShxydm(hngsNsrLoginResponse.getNsrsbh());
        userHngs.setSwjgMc(hngsNsrLoginResponse.getZgswjmc());
        userHngs.setSwjgDm(hngsNsrLoginResponse.getZgswjDm());
        Date date = new Date();
        userHngs.setId(Utils.uuid());
        userHngs.setSmrzzt(false);
        userHngs.setStatus(true);
        userHngs.setCreateTime(date);
        userHngs.setLastUpdate(date);
        userHngs.setUserId(UserUtil.getUserId(request));
        userHngs.setRoleId(hngsNsrLoginResponse.getRoleId());
        int result = userBindMapper.hngsBind(userHngs);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userHngs.toString());
            throw new ServiceException(4101);
        }
        UserHngsBO userHngsBO1 = new UserHngsBO();
        BeanUtils.copyProperties(userHngs, userHngsBO1);
        return userHngsBO1;
    }

    @Override
    public boolean hngsUnbind(String id) {
        UserHngs userHngs = userBindRoMapper.userHngsSelectById(id);
        if (userHngs == null) {
            LOGGER.warn("修改失败，参数：null");
            throw new ServiceException(4102);
        }
        int result = userBindMapper.hngsUnbind(id);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：{}" + id);
            throw new ServiceException(4102);
        }
        return true;
    }

    @Override
    public UserHndsBO hndsBind(UserHndsInsertBO userHndsInsertBO, HttpServletRequest request) {
        if (userHndsInsertBO == null) {
            LOGGER.warn("新增失败，参数：{}" + null);
            throw new ServiceException(4101);
        }
        UserHnds userHnds = new UserHnds();
        //BeanUtils.copyProperties(userHndsInsertBO, userHnds);
        Date date = new Date();
        userHnds.setId(Utils.uuid());
        userHnds.setStatus(true);
        userHnds.setCreateTime(date);
        userHnds.setLastUpdate(date);
        userHnds.setUserId(UserUtil.getUserId(request));
        int result = userBindMapper.hndsBind(userHnds);
        if (result < 1) {
            LOGGER.warn("新增失败，参数：{}" + userHnds);
            throw new ServiceException(4101);
        }
        UserHndsBO userHndsBO1 = new UserHndsBO();
        BeanUtils.copyProperties(userHnds, userHndsBO1);
        return userHndsBO1;
    }

    @Override
    public boolean hndsUnbind(String id) {
        UserHnds userHnds = userBindRoMapper.userHndsSelectById(id);
        if (userHnds == null) {
            LOGGER.warn("修改失败，参数：{}" + id);
            throw new ServiceException(4102);
        }
        int result = userBindMapper.hndsUnbind(id);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：{}" + id);
            throw new ServiceException(4102);
        }
        return true;
    }

    @Override
    public List<UserDzsbListBO> getUserDzsbBind(String userId) {
        return userBindRoMapper.getUserDzsbBind(userId);
    }

    @Override
    public List<UserHngsListBO> getUserhngsBind(String userId) {
        return userBindRoMapper.getUserhngsBind(userId);
    }

    @Override
    public List<UserHndsBO> getUserhndsBind(String userId) {
        return userBindRoMapper.getUserhndsBind(userId);
    }

    @Override
    public TY21Xml2Object nsrLogin(NsrLogin login, HttpServletRequest request) throws Exception {
        LOGGER.info("{}", login);
        String userId = UserUtil.getUserId(request);
        Map<String, String> map = new HashMap<>();
        map.put("serviceid", "TY21");
        map.put("nsrsbh", login.getNsrsbhOrShxydm());
        map.put("fwmm", fwmmEncode(login.getFwmm()));
        map.put("userid", userId);
        Map<String, String> resMap = client.process(map);

        TY21Xml2Object ty21Object = analyzeXmlTY21(resMap, login.getNsrsbhOrShxydm());
        return ty21Object;
    }

    @Override
    public void resetPassword(NsrResetPwd data, HttpServletRequest request) throws IOException, MarshalException, ValidationException {
        //1.验证码校验
//        VerifyingCodeBO param = new VerifyingCodeBO();
//        param.setPhone(data.getPhone());
//        param.setType(data.getType());
//        param.setCode(data.getCode());
//        authService.verifyCode(param, request);

        Map<String, String> map = new HashMap<>();
        map.put("serviceid", "TY12");
        map.put("NSRSBH", data.getNsrsbh());
        Map respMap = client.process(map);
        analyzeXmlTY12(respMap, data.getNsrsbh());

    }

    private void analyzeXmlTY12(Map resMap, String nsrsbh) throws MarshalException, ValidationException {
        if (resMap == null || resMap.isEmpty() || resMap.get("rescode") == null) {
            throw new ServiceException(4629);
        }

        if (!resMap.get("rescode").equals("00000000")) {
            throw new ServiceException((String) resMap.get("rescode"), (String) resMap.get("message"));
        }
        if (!resMap.containsKey("taxML_CRM_NSRMMGX_" + nsrsbh + ".xml")) {
            throw new ServiceException(4634);
        }

        try {
            NSRMMGX nsrmmgx = (NSRMMGX) XmlJavaParser.parseXmlToObject(NSRMMGX.class, String.valueOf(resMap.get("taxML_CRM_NSRMMGX_" + nsrsbh + ".xml")));
            if (nsrmmgx == null || nsrmmgx.getCLJG() == null) {
                throw new ServiceException(4633);
            }
            if (nsrmmgx.getCLJG() != null && !nsrmmgx.getCLJG().trim().equals("0")) {
                throw new ServiceException(nsrmmgx.getCLJG(), nsrmmgx.getCWYY());
            }
        } catch (org.exolab.castor.xml.MarshalException e) {
            e.printStackTrace();
            throw new ServiceException(4633);
        }

    }

    @Override
    public void updatePassword(UpdatePwd data) throws MarshalException, ValidationException {
        Map<String, String> map = new HashMap<>();
        map.put("serviceid", "TY03");
        map.put("NSRSBH", data.getNsrsbh());
        map.put("OLDPASS", data.getOldpwd());
        map.put("NEWPASS", data.getNewpwd());
        Map respMap = client.process(map);
        analyzeXmlTY03(respMap, data.getNsrsbh());
        System.out.println(respMap);
    }

    public TY21Xml2Object analyzeXmlTY21(Map resMap, String nsrsbh) throws MarshalException, ValidationException {
        if (resMap == null || resMap.isEmpty() || !resMap.get("rescode").equals("00000000")) {
            throw new ServiceException(4629);
        }
        if (!resMap.get("rescode").equals("00000000")) {
            throw new ServiceException((String) resMap.get("rescode"), (String) resMap.get("message"));
        }
        if (!resMap.containsKey("taxML_SSHDXX_" + nsrsbh + ".xml")) {
            throw new ServiceException(4634);
        }
        TY21Xml2Object object = new TY21Xml2Object();
        com.abc12366.uc.tdps.vo.TY21Response.JBXXCX jbxxcx;
        try {
            jbxxcx = (com.abc12366.uc.tdps.vo.TY21Response.JBXXCX) XmlJavaParser.parseXmlToObject(com.abc12366.uc.tdps.vo.TY21Response.JBXXCX.class, String.valueOf(resMap.get("taxML_SSHDXX_" + nsrsbh + ".xml")));
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
            }
        } else {
            String msg = jbxxcx.getCWYY();
            throw new ServiceException(cxjg, msg);
        }
        return object;
    }

    public String fwmmEncode(String code) throws Exception {
//        String appointCode = "abchngs";
//        String encodedCode = "";
        //1.先BASE64编码，
//        encodedCode = Utils.encode(code);
        //2.编码串MD5，
//        encodedCode = Utils.md5(encodedCode);
        //3.统一转成大写，
//        encodedCode = encodedCode.toUpperCase();
        //4.生成的字符串加一串约定码，
//        encodedCode = encodedCode + appointCode;
        //5.再进行MD5，
//        encodedCode = Utils.md5(encodedCode);
        //6.再转一次大写
//        encodedCode = encodedCode.toUpperCase();

//        return encodedCode;

        String frist_sbmm = new MD5(code).compute().toUpperCase();
        String second_gssbmm = new MD5(frist_sbmm + "abchngs").compute().toUpperCase();
        return second_gssbmm;
    }

    private void analyzeXmlTY03(Map resMap, String nsrsbh) throws MarshalException, ValidationException {
        if (resMap == null || resMap.isEmpty() || resMap.get("rescode") == null) {
            throw new ServiceException(4629);
        }
        if (!resMap.get("rescode").equals("00000000")) {
            throw new ServiceException((String) resMap.get("rescode"), (String) resMap.get("message"));
        }
        if (!resMap.containsKey("taxML_NSRAQSZ_" + nsrsbh + ".xml")) {
            throw new ServiceException(4634);
        }

        try {
            XGJGS xgjgs = (XGJGS) XmlJavaParser.parseXmlToObject(XGJGS.class, String.valueOf(resMap.get("taxML_NSRAQSZ_" + nsrsbh + ".xml")));
            if (xgjgs == null || xgjgs.getXGJG() == null) {
                throw new ServiceException(4633);
            }
            for (XGJG xgjg : xgjgs.getXGJG()) {
                if (xgjg.getGSCG() != "0") {
                    throw new ServiceException(xgjg.getGSCG(), xgjg.getCWYY());
                }
            }
        } catch (org.exolab.castor.xml.MarshalException e) {
            e.printStackTrace();
            throw new ServiceException(4633);
        }

    }
}
