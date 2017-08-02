package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Properties;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.UserBindMapper;
import com.abc12366.uc.mapper.db2.UserBindRoMapper;
import com.abc12366.uc.model.UserDzsb;
import com.abc12366.uc.model.UserHnds;
import com.abc12366.uc.model.UserHngs;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.util.UserUtil;
import com.abc12366.uc.wsbssoa.response.HngsAppLoginResponse;
import com.abc12366.uc.wsbssoa.response.HngsNsrLoginResponse;
import com.abc12366.uc.wsbssoa.service.MainService;
import com.abc12366.uc.wsbssoa.utils.MD5;
import com.abc12366.uc.wsbssoa.utils.RSAUtil;
import com.abc12366.uc.wsbssoa.utils.soaUtil;
import com.alibaba.fastjson.JSON;
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
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
@Service
public class UserBindServiceImpl implements UserBindService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBindServiceImpl.class);

    @Autowired
    private UserBindMapper userBindMapper;

    @Autowired
    private UserBindRoMapper userBindRoMapper;

    private static Properties properties = new Properties("application.properties");

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MainService mainService;

    @Override
    public UserDzsbBO dzsbBind(UserDzsbInsertBO userDzsbInsertBO, HttpServletRequest request) {
        if (userDzsbInsertBO == null) {
            LOGGER.warn("新增失败，参数：null");
            throw new ServiceException(4101);
        }

        //调外系统接口获取电子申报绑定信息


        UserDzsb userDzsb = new UserDzsb();
        //BeanUtils.copyProperties(userDzsbBO, userDzsb);


        userDzsb.setId(Utils.uuid());
        Date date = new Date();
        userDzsb.setCreateTime(date);
        userDzsb.setLastUpdate(date);
        userDzsb.setStatus(true);
        userDzsb.setUserId(UserUtil.getUserId(request));
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
    private HngsNsrLoginResponse loginWsbsHngs(UserHngsInsertBO userHngsInsertBO, HttpServletRequest request) throws Exception {
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
                pw = new String(RSAUtil.encrypt(pbk, new MD5(pw + timestamp.getTime()).compute().getBytes("iso-8859-1")), "iso-8859-1");
                nsrsbh = new String(RSAUtil.encrypt(pbk, (timestamp.getTime() + nsrsbh).getBytes("iso-8859-1")), "iso-8859-1");
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
                return JSON.parseObject(String.valueOf(responseEntity.getBody()), HngsNsrLoginResponse.class);
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
            HngsAppLoginResponse hngsAppLoginResponse = JSON.parseObject(String.valueOf(responseEntity.getBody()), HngsAppLoginResponse.class);
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
        userHngs.setSwjgMc("网上报税接口暂时不返回这个字段。");
        userHngs.setSwjgDm(hngsNsrLoginResponse.getZgswjDm());
        Date date = new Date();
        userHngs.setId(Utils.uuid());
        userHngs.setSmrzzt(false);
        userHngs.setStatus(true);
        userHngs.setCreateTime(date);
        userHngs.setLastUpdate(date);
        userHngs.setUserId(UserUtil.getUserId(request));
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


    public static void main(String[] args) {
//        new UserBindServiceImpl().testPost();
//        try {
//            new UserBindServiceImpl().appLoginWsbs();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void testPost() {
        String url = "http://localhost:9100/uc/app/register";
        HttpHeaders headers = new HttpHeaders();
        headers.add(Constant.VERSION_HEAD, Constant.VERSION_1);
        headers.add("Content-Type", "application/json");

        Map<String, Object> requestBody = new HashMap<>();
        String name = "abc_test";
        String password = "12345678";
        requestBody.put("name", name);
        requestBody.put("password", password);
        requestBody.put("status", true);
//        String requestBody = "{\"name\":\"abc-test\",\"password\":\"12345678\",\"status\":\"true\"}";

//        String requestBody = "{\"appId\":\"ETAX_PC\",\"secret\":\"3A6ABF6B62EA0190E053550C483DD05A\"}";

        HttpEntity requestEntity = new HttpEntity(requestBody, headers);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
//        if (soaUtil.isExchangeSuccessful(responseEntity)) {
//            return JSON.parseObject(String.valueOf(responseEntity.getBody()), HngsAppLoginResponse.class);
//        }
//        return null;
        System.out.println("" + responseEntity);
    }
}
