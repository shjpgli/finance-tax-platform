package com.abc12366.uc.webservice;

import com.abc12366.uc.jrxt.model.tiripPackage.TiripPackage;
import com.abc12366.uc.jrxt.model.util.PkgUtil;
import com.abc12366.uc.jrxt.model.util.XmlJavaParser;
import org.apache.axis2.AxisFault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * Created by p on 2016-04-28.
 */
@Component
public class AcceptClient {
    @Autowired
    private PkgUtil pkgutil;

    public Map<String, String> process(Map<String, String> map){
        //String target = "http://dzsb.abc12366.cn/tdps-accept/services/AcceptService?wsdl";
        String target = "http://testhn.abc12366.cn/tdps-accept/services/AcceptService?wsdl";
        //String target = "http://118.118.116.126:6688/tdps-accept/services/AcceptService?wsdl";

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
        } catch (RemoteException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    

}
