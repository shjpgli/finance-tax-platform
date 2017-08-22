package com.abc12366.uc.util.wx;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.abc12366.uc.config.SpringCtxHolder;
import com.abc12366.uc.model.weixin.bo.WxUseToken;
import com.abc12366.uc.model.weixin.bo.gzh.GzhInfo;
import com.abc12366.uc.service.IWxGzhService;

@Component
public class WxGzhClient {
    private static IWxGzhService iWxGzhService;
    // token获取失败，重复获取次数
    private static GzhInfo gzhInfo = null;
    
    public static GzhInfo getInstance(){    
        if (gzhInfo == null){
           synchronized(WxGzhClient.class){
                if (gzhInfo == null)
                	 iWxGzhService=(IWxGzhService) SpringCtxHolder.getApplicationContext().getBean("iWxGzhService");
                	 gzhInfo = iWxGzhService.wxgzhList(new GzhInfo(), 0, 1).get(0);
           }
        }
        return gzhInfo;
	}
    
    public static String getInstanceToken(){
    	GzhInfo temp=iWxGzhService.selectOne(gzhInfo.getAppid());
    	if(temp.getUserTokenUpdate()==null || isAfter(temp.getUserTokenUpdate())){
    		 Map<String, String> tks = new HashMap<String, String>();
             tks.put("grant_type", "client_credential");
             tks.put("appid", gzhInfo.getAppid());
             tks.put("secret", gzhInfo.getSecret());
             WxUseToken token = WxConnectFactory.get(WechatUrl.WXUSETOKEN, tks, null,
                     WxUseToken.class);
             if (0 == token.getErrcode()) {
            	 gzhInfo.setUserToken(token.getAccess_token());
            	 gzhInfo.setUserTokenUpdate(new Date());
            	 iWxGzhService.updateUserToken(gzhInfo);
            	 return token.getAccess_token();
             }
    	}
    	return temp.getTokenStr();
    }
    
    private static boolean isAfter(Date date){
    	Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, -1);
        return date.after(calendar.getTime());
    }
}
