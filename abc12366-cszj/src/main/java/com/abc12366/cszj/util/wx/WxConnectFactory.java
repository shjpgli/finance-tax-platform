package com.abc12366.cszj.util.wx;



import java.util.Map;

import com.abc12366.cszj.model.weixin.bo.menu.Button;
import com.abc12366.cszj.model.weixin.bo.menu.Menu;
import com.abc12366.cszj.model.weixin.bo.message.FileContent;
import com.alibaba.fastjson.JSON;



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
	 
	 
     
     public static void main(String[] args) {
    	 
    	 /*Map<String,String> tks=new HashMap<String, String>();
    	 tks.put("grant_type", "client_credential");
    	 tks.put("appid", "wxcc69b5b49f2f232e");
    	 tks.put("secret", "b2fe7d55be613ffee9242869d538daa6");
    	 WxUseToken useToken= get("https://api.weixin.qq.com/cgi-bin/token",tks,null,WxUseToken.class);
    	 System.out.println(useToken.getAccess_token());*/
    	 
    	 //String token="h9jqMNQCcdpccfb_mudt1ZyRqgFjUxZuOyfpO8z2XVWERyd4Hdt8wLsIZYAQ2vZaDG3Sw6TZHPJzwOJ0S5dYCRnNfmhoCDb6RvtM6GL7Buw3SeXfLy0aiMVqTwGys2OKNIBjAFARMF";
    	 //WxUseToken useToken=  get("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wxcc69b5b49f2f232e&secret=b2fe7d55be613ffee9242869d538daa6",null,WxUseToken.class);
    	 //System.out.println(useToken.getAccess_token());
    	/* Map<String,String> tks=new HashMap<String, String>();
    	 tks.put("access_token", token);
    	 tks.put("next_openid", "");
    	 tks.put("lang", "zh_CN");
    	 
    	 https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=ACCESS_TOKEN
    	 
    	 
    	 
    	 OpenIdRs listRs=get("https://api.weixin.qq.com/cgi-bin/user/get",tks,null,OpenIdRs.class);
    	 String [] ids =listRs.getData().getOpenid();
     	 for(int i=0;i<ids.length;i++){
     		 Map<String,String> tks1=new HashMap<String, String>();
     		 tks1.put("access_token", token);
     		 tks1.put("openid", ids[i]);
     		 WxPerson person=get("https://api.weixin.qq.com/cgi-bin/user/info",tks1,null,WxPerson.class);
     		 System.out.println(person.getNickname());
     	 }*/
     	 
    	 Menu menu=new Menu();
    	 
    	 Button btn11 = new Button();
         btn11.setName("天气预报");
         btn11.setType("click");
         btn11.setKey("11");

         Button btn12 = new Button();
         btn12.setName("公交查询");
         btn12.setType("click");
         btn12.setKey("12");

         Button btn13 = new Button();
         btn13.setName("周边搜索");
         btn13.setType("click");
         btn13.setKey("13");

         Button btn14 = new Button();
         btn14.setName("历史上的今天");
         btn14.setType("click");
         btn14.setKey("14");
         
         Button bt1=new Button();
    	 bt1.setName("神州周边");
    	 bt1.setSub_button(new Button[]{btn11,btn12,btn13,btn14});
    	 
    	 Button bt2=new Button();
    	 bt2.setName("神州百度");
    	 bt2.setType("view");
    	 bt2.setUrl("http://www.baidu.com");
    	 
    	 menu.setButton(new Button[]{bt1,bt2});
    	 
    	 System.out.println(JSON.toJSONString(menu));
    	 
     	/* Map<String,String> tks=new HashMap<String, String>();
   	     tks.put("access_token", token);
     	 BaseWxRespon respon=post("https://api.weixin.qq.com/cgi-bin/menu/create",tks,menu,BaseWxRespon.class);
    	 System.out.println(respon.getErrcode());*/
    	 
    	/* Map<String,String> tks=new HashMap<String, String>();
    	 tks.put("access_token", token);
    	 Menu menu=get("https://api.weixin.qq.com/cgi-bin/menu/get",tks,null,Menu.class);
    	 System.out.println(menu.getErrcode());*/
    	 
    	 /*Map<String,String> tks1=new HashMap<String, String>();
    	 tks1.put("access_token", token);
    	 BaseWxRespon respon=get("https://api.weixin.qq.com/cgi-bin/menu/delete",tks1,null,BaseWxRespon.class);
    	 System.out.println(respon.getErrcode());*/
     	 
     	
       /*  Map<String,String> tks1=new HashMap<String, String>();
    	 tks1.put("access_token", token);
    	 //BaseWxRespon respon=get("https://api.weixin.qq.com/cgi-bin/template/get_all_private_template",tks1,null,BaseWxRespon.class);
     	 TemplateMsg msg=new TemplateMsg();
     	 msg.setTemplate_id("CiHtyuUVWkL76AVEaUjYqcJevaQJMqzl7OKhVVruVwU");
    	 BaseWxRespon respon=post("https://api.weixin.qq.com/cgi-bin/template/del_private_template",tks1,msg,BaseWxRespon.class);*/
    	 
	 }
     
}
