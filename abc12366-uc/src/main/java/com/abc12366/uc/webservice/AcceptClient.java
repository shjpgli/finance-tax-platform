package com.abc12366.uc.webservice;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.uc.jrxt.model.tiripPackage.TiripPackage;
import com.abc12366.uc.jrxt.model.util.PkgUtil;
import com.abc12366.uc.jrxt.model.util.XmlJavaParser;
import com.abc12366.uc.model.job.DzsbJob;
import com.abc12366.uc.model.job.DzsbXxInfo;
import com.alibaba.fastjson.JSONObject;

import org.apache.axis2.AxisFault;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * Created by p on 2016-04-28.
 */
@Component("client")
public class AcceptClient {
    @Autowired
    private PkgUtil pkgutil;

    public Map<String, String> process(Map<String, String> map) {
        //String target = "http://dzsb.abc12366.cn/tdps-accept/services/AcceptService?wsdl";
//        String target = "http://testhn.abc12366.cn/tdps-accept/services/AcceptService?wsdl";
        String target = SpringCtxHolder.getProperty("tdps.api.url") + "/tdps-accept/services/AcceptService?wsdl";

        try {
            AcceptServiceStub.AcceptRequest request = new AcceptServiceStub.AcceptRequest();
            request.setTiripPkgStr(pkgutil.generatorPkgStrbyTdps(map));
            AcceptServiceStub stub = new AcceptServiceStub(target);

            AcceptServiceStub.AcceptResponse processResponse = stub.accept(request);
            String response = processResponse.getTiripPkgStr();
            TiripPackage tiripPackage = (TiripPackage) XmlJavaParser.parseXmlToObject(TiripPackage.class, response);
            return pkgutil.processBackBusinessPkgBytdps(tiripPackage);
        } catch (AxisFault axisFault) {
            axisFault.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 业务数据获取
     * @param map  参数
     * @return
     */
    public DzsbJob processYw(Map<String, String> map) {
    	DzsbJob dzsbJob=new DzsbJob();
    	dzsbJob.setYwlx(map.get("ywid").toUpperCase());
        String target = "http://testdzsb.abc12366.com/tdps-accept/services/AcceptService/";
        try {
            AcceptServiceStub.AcceptRequest request = new AcceptServiceStub.AcceptRequest();
            request.setTiripPkgStr(pkgutil.generatorPkgStrbyTdps(map));
            AcceptServiceStub stub = new AcceptServiceStub(target);

            AcceptServiceStub.AcceptResponse processResponse = stub.accept(request);
            String response = processResponse.getTiripPkgStr();
            TiripPackage tiripPackage = (TiripPackage) XmlJavaParser.parseXmlToObject(TiripPackage.class, response);
            Map<String, String> resMap= pkgutil.processBackBusinessPkgBytdps(tiripPackage);
            dzsbJob.setRescode(resMap.get("rescode"));	
            dzsbJob.setMessage(resMap.get("message"));
            
            if("00000000".equals(dzsbJob.getRescode())){
            	String fileName=tiripPackage.getBusinessContent().getSubPackage(0).getParamList(0).getValue();
            	String value=resMap.get(fileName);
            	String jonstr=StringUtils.substringBetween(value,"<string>", "</string>");
            	JSONObject object=JSONObject.parseObject(jonstr);
            	dzsbJob.setIsExistData(object.getBoolean("isExistData"));
            	dzsbJob.setDataList(object.getJSONArray("dataList").toJavaList(DzsbXxInfo.class));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            dzsbJob.setRescode("999999");
            dzsbJob.setMessage(e.getMessage());
        }
        return dzsbJob;
    }
}
