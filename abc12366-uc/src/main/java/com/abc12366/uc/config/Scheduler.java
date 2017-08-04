package com.abc12366.uc.config;

import com.abc12366.uc.model.weixin.bo.WxUseToken;
import com.abc12366.uc.util.wx.WechatUrl;
import com.abc12366.uc.util.wx.WxConnectFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 定时器获取微信usertoken
 * 
 * @author zhushuai 2017-7-27
 * 
 */
@Component
public class Scheduler {

	public static WxUseToken token = null;
	// token获取失败，重复获取次数
	private Integer regetTime = 10;

	@Scheduled(fixedRate = 3600000)
	public void getUserToken() {
		int time = 0;
		while (true) {
			Map<String, String> tks = new HashMap<String, String>();
			tks.put("grant_type", "client_credential");
			tks.put("appid", SpringCtxHolder.getProperty("abc.wx-appid"));
			tks.put("secret", SpringCtxHolder.getProperty("abc.wx-secret"));
			token = WxConnectFactory.get(WechatUrl.WXUSETOKEN, tks, null,
					WxUseToken.class);
			if (0==token.getErrcode()) {
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
