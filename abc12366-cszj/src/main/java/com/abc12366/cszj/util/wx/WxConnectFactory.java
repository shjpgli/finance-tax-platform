package com.abc12366.cszj.util.wx;



import java.util.Map;
import com.abc12366.cszj.model.weixin.bo.message.FileContent;




/**
 * 
 * @author zhushuai 2017-7-28
 *
 */
public class WxConnectFactory {
	 private static final String HTTP_GET="GET";
	 private static final String HTTP_POST="POST";


	 public static  <T extends Object> T get(WechatUrl url,Map<String, String> headparamters,Object bodyparamters,Class<T> _class) {
    	 WxConnect<T> connect=new WxConnect<>(url, HTTP_GET, headparamters,bodyparamters, _class);
    	 return doConect(connect);
     }
	 
	 
	 public static  <T extends Object> T post(WechatUrl url,Map<String, String> headparamters,Object bodyparamters,Class<T> _class) {
    	 WxConnect<T> connect=new WxConnect<>(url, HTTP_POST, headparamters,bodyparamters, _class);
    	 return doConect(connect);
     }
	 
	 public static  <T extends Object> T postFile(WechatUrl url,Map<String, String> headparamters,Object bodyparamters,Class<T> _class,FileContent file){
		 WxConnect<T> connect=new WxConnect<>(url, headparamters,bodyparamters, _class,file);
		 connect.initJson();
    	 connect.httpPostFile();
    	 return connect.parseObject();
	 }
	 
	 
	 
	 private static <T extends Object> T doConect(WxConnect<T> connect){
		 connect.initJson();
    	 connect.httpsRequest();
    	 return connect.parseObject();
	 }
	 
     
}
