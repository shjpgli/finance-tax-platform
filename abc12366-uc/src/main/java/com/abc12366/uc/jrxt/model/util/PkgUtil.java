package com.abc12366.uc.jrxt.model.util;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.uc.jrxt.model.tiripPackage.*;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 15-5-15.
 */
@Component
public class PkgUtil extends BaseObject{
    @Autowired
    private CrypTdpsUtil cryptutiltdps;
    @Autowired
    private ZipUtilTdps zipUtiltdps;
    @Autowired
    private CodeUtilTdps codeUtiltdps;

    public String generatorPkgStrbyTdps(final Map<String, String> map) throws Exception {
        String result = null;
        String serviceId = map.get("serviceid").toUpperCase();
        if(serviceId.equalsIgnoreCase("TY11")){//从电子申报获取纳税人基础信息查询
            result = makeTiripPackageBytdps(makeTY11(map), serviceId, "ABC_4000");
//            result=requestBuild.doBuildRequestXml(serviceId, "qqqqqq", map.get("nsrsbh"), "201605", new String[]{"TY11"}, new String[]{makeTY11(map)});
        }
        return result;
    }


    private static String makeTY11(final Map<String, String> map){
        com.abc12366.uc.jrxt.model.TY11Request.REQUEST request = new com.abc12366.uc.jrxt.model.TY11Request.REQUEST();
        request.setNSRSBH(map.get("nsrsbh"));
        com.abc12366.uc.jrxt.model.TY11Request.NSRJBXX nsrjbxx=new  com.abc12366.uc.jrxt.model.TY11Request.NSRJBXX();
        com.abc12366.uc.jrxt.model.TY11Request.MXXX  mxxx=new com.abc12366.uc.jrxt.model.TY11Request.MXXX();
        mxxx.setCODE("NSRSBH");
        mxxx.setVALUE(map.get("nsrsbh"));
        nsrjbxx.addMXXX(mxxx);
        request.setNSRJBXX(nsrjbxx);
        try {
            return XmlJavaParser.parseObjectToXml(request);
        } catch (IOException e) {
            _log.error("IOException: " + e);
            e.printStackTrace();
        } catch (MarshalException e) {
            _log.error("MarshalException: " + e);
            e.printStackTrace();
        } catch (ValidationException e) {
            _log.error("ValidationException: " + e);
            e.printStackTrace();
        }
        return null;
    }

    public static String makeTiripPackage(String contentStr, String serviceId, String yhmc){
        _log.info("subPackage: \n" + contentStr);
        String content = processBusinessPkg(contentStr, "cszj");
        TiripPackage tiripPackage = new TiripPackage();

        // make Identity
        Identity identity = new Identity();
        identity.setServiceId(serviceId);
        identity.setChannelId(yhmc);
        identity.setPassword("qqqqqq");
        tiripPackage.setIdentity(identity);

        // make ContentControl
        ContentControl contentControl = new ContentControl();
        Control control1 = new Control();
        control1.setId("1");
        control1.setType("zip");
        control1.setImpl("Zlib");
        contentControl.addControl(control1);

        Control control2 = new Control();
        control2.setId("2");
        control2.setType("crypt");
        control2.setImpl("YHMMMJM");
        contentControl.addControl(control2);

        Control control3 = new Control();
        control3.setId("3");
        control3.setType("code");
        control3.setImpl("Base64");
        contentControl.addControl(control3);
        tiripPackage.setContentControl(contentControl);

        //make RouterSession
        RouterSession routerSession = new RouterSession();
        ParamList paramList = new ParamList();
        paramList.setName("CREATETIME");
        paramList.setValue(DateUtil.getCurrentTime("yyyy-MM-dd HH:mm:ss"));
        routerSession.addParamList(paramList);
        tiripPackage.setRouterSession(routerSession);

        // make BussinessContent
        BusinessContent businessContent =  new BusinessContent();
        SubPackage subPackage = new SubPackage();
        subPackage.setId("1");
        subPackage.setContent(content);
        businessContent.addSubPackage(subPackage);
        tiripPackage.setBusinessContent(businessContent);
        tiripPackage.setVersion("1.0");
        String xml = null;
        try {
             xml = XmlJavaParser.parseObjectToXml(tiripPackage);
        } catch (IOException e) {
            _log.error("IOException: " + e);
            e.printStackTrace();
        } catch (MarshalException e) {
            _log.error("MarshalException: " + e);
            e.printStackTrace();
        } catch (ValidationException e) {
            _log.error("ValidationException: " + e);
            e.printStackTrace();
        }
        _log.info("tiripPackage:\n" + xml);
        return xml;
    }

    public String makeTiripPackageBytdps(String contentStr, String serviceId, String yhmc){
        _log.info("subPackage: \n" + contentStr);
        String mm=CommonUtils.getRandom();
        //加密后的密码
        String jmmm=cryptutiltdps.b64AsymmetricEncrypt(mm);
        String content = processBusinessPkgBytdps(contentStr, "abc",mm);
        TiripPackage tiripPackage = new TiripPackage();

        // make Identity
        Identity identity = new Identity();
        identity.setServiceId(serviceId);
        identity.setChannelId(yhmc);
        identity.setPassword("qqqqqq");
        tiripPackage.setIdentity(identity);

        // make ContentControl
        ContentControl contentControl = new ContentControl();
        Control control1 = new Control();
        control1.setId("1");
        control1.setType("zip");
        control1.setImpl("Zlib");
        contentControl.addControl(control1);

        Control control2 = new Control();
        control2.setId("2");
        control2.setType("crypt");
        control2.setImpl("SBMMJM");
        contentControl.addControl(control2);

        Control control3 = new Control();
        control3.setId("3");
        control3.setType("code");
        control3.setImpl("Base64");
        contentControl.addControl(control3);
        tiripPackage.setContentControl(contentControl);

        //make RouterSession
        RouterSession routerSession = new RouterSession();
        ParamList paramList = new ParamList();
        paramList.setName("MM");
        paramList.setValue(jmmm);
        routerSession.addParamList(paramList);
        tiripPackage.setRouterSession(routerSession);

        // make BussinessContent
        BusinessContent businessContent =  new BusinessContent();
        SubPackage subPackage = new SubPackage();
        subPackage.setId("1");
        subPackage.setContent(content);
        businessContent.addSubPackage(subPackage);
        tiripPackage.setBusinessContent(businessContent);
        tiripPackage.setVersion("1.0");
        String xml = null;
        try {
            xml = XmlJavaParser.parseObjectToXml(tiripPackage);
        } catch (IOException e) {
            _log.error("IOException: " + e);
            e.printStackTrace();
        } catch (MarshalException e) {
            _log.error("MarshalException: " + e);
            e.printStackTrace();
        } catch (ValidationException e) {
            _log.error("ValidationException: " + e);
            e.printStackTrace();
        }
        _log.info("tiripPackage:\n" + xml);
        return xml;
    }

    private static String processBusinessPkg(String pkgStr, String name){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(name, pkgStr);
        return processBusinessPkg(map);
    }

    private static String processBusinessPkg(HashMap<String, String> map){
        try {
            ZipUtil zu = new ZipUtil();
            byte[] bytes2 = zu.compress(map);

            CryptUtil cu = new CryptUtil();
            byte[] i = cu.encryptContent(bytes2, "88888888");

            CodeUtil code = new CodeUtil();
            return code.encodeContent(i);
        }catch (Exception e){
            _log.error("Exception: " + e);
            e.printStackTrace();
        }
        return null;
    }

    private String processBusinessPkgBytdps(String pkgStr, String name,String mm){
        HashMap<String, String> map = new HashMap<String, String>();
        map.put(name, pkgStr);
        return processBusinessPkgBytdps(map,mm);
    }

    private  String processBusinessPkgBytdps(HashMap<String, String> map,String mm){
        try {
            byte[] bytes2 = zipUtiltdps.compress(map);

            byte[] i = cryptutiltdps.encryptContent(bytes2, mm.getBytes());

            return codeUtiltdps.encodeContent(i);
        }catch (Exception e){
            _log.error("Exception: " + e);
            e.printStackTrace();
        }
        return null;
    }
    public  Map<String, String> processBackBusinessPkgBytdps(TiripPackage tiripPackage){
        Map<String, String> map = new HashMap<>();
        String returnCode = tiripPackage.getReturnState().getReturnCode();

        if(returnCode.equals("00000000") && tiripPackage.getBusinessContent().getSubPackageCount() > 0) {
            String contents = tiripPackage.getBusinessContent().getSubPackage(0).getContent();
            try {
                byte[] contentBytes = codeUtiltdps.decodeContent("BASE64", contents);
                com.abc12366.uc.jrxt.model.tiripPackage.ParamList[] paramlist=tiripPackage.getRouterSession().getParamList();
                for(ParamList param :paramlist){
                    if("MM".equals(param.getName())&&!"".equals(param.getValue())){
                        contentBytes = cryptutiltdps.decryptContent(cryptutiltdps.b64AsymmetricDecrypt(param.getValue()), "SBMMJM", contentBytes);
                    }
                }
                map = zipUtiltdps.decompress(contentBytes);
            } catch (Exception e) {
                _log.error("Exception: " + e);
                throw new ServiceException(4631);
            }
        }
        map.put("rescode", returnCode);
        map.put("message", tiripPackage.getReturnState().getReturnMessage());
        return map;
    }

    public static Map<String, String> processBackBusinessPkg(TiripPackage tiripPackage){
        Map<String, String> map = new HashMap<String, String>();
        String returnCode = tiripPackage.getReturnState().getReturnCode();

        if(returnCode.equals("00000000") && tiripPackage.getBusinessContent().getSubPackageCount() > 0) {
            String contents = tiripPackage.getBusinessContent().getSubPackage(0).getContent();
            try {
                CodeUtil code = new CodeUtil();
                byte[] contentBytes = code.decodeContent("BASE64", contents);

                CryptUtil cu = new CryptUtil();
                contentBytes = cu.decryptContent("88888888", "YHMMMJM", contentBytes);

                ZipUtil zu = new ZipUtil();
                map = zu.decompress(contentBytes);
            } catch (Exception e) {
                _log.error("Exception: " + e);
                e.printStackTrace();
            }
        }
        map.put("rescode", returnCode);
        map.put("message", tiripPackage.getReturnState().getReturnMessage());
        return map;
    }
}
