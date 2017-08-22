package com.abc12366.uc.job.wx;

import com.abc12366.uc.model.weixin.bo.WxUseToken;
import com.abc12366.uc.service.IWxGzhService;
import com.abc12366.uc.util.wx.WechatUrl;
import com.abc12366.uc.util.wx.WxConnectFactory;
import com.abc12366.uc.util.wx.WxGzhClient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时器获取微信usertoken
 *
 * @author zhushuai 2017-7-27
 */
public class WxUserTokenJob{

    public static WxUseToken token = null;
    @Autowired
    private IWxGzhService iWxGzhService;
    // token获取失败，重复获取次数
    private Integer regetTime = 10;

	public void execute(){

        int time = 0;
        while (true) {
            Map<String, String> tks = new HashMap<String, String>();
            tks.put("grant_type", "client_credential");
            tks.put("appid", WxGzhClient.getAppid());
            tks.put("secret",  WxGzhClient.getInstance().getSecret());
            token = WxConnectFactory.get(WechatUrl.WXUSETOKEN, tks, null,
                    WxUseToken.class);
            if (0 == token.getErrcode()) {
            	 WxGzhClient.getInstance().setUserToken(token.getAccess_token());
            	 iWxGzhService.updateUserToken(WxGzhClient.getInstance());
                 break;
            } else {
                if (time >= regetTime) {
                    break;
                }
                try {
                    Thread.sleep(30 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                time += 1;
            }
        }
	}
}
