package com.abc12366.uc.service.impl.pay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.uc.model.pay.WxRefund;
import com.abc12366.uc.model.pay.WxRefundRsp;
import com.abc12366.uc.service.pay.IWxPayService;
import com.abc12366.uc.util.wx.SignUtil;
import com.abc12366.uc.util.wx.WechatUrl;
import com.abc12366.uc.util.wx.WxMchConnectFactory;
import com.alibaba.fastjson.JSONObject;
@Service
public class WxPayServiceImpl implements IWxPayService{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(WxPayServiceImpl.class);

	@Override
	public WxRefundRsp doWxRefund(WxRefund wxrefund) {
		LOGGER.info("微信支付退款申请:{}", JSONObject.toJSONString(wxrefund));
		wxrefund.setAppid(SpringCtxHolder.getProperty("abc.appid"))
		.setMch_id(SpringCtxHolder.getProperty("abc.mch_id")).setNonce_str(SignUtil.getRandomString(30))
		.setSign(SignUtil.signKey(wxrefund));
		
		WxRefundRsp wxpayorderrsp = WxMchConnectFactory.post(WechatUrl.WXREFUND, null, wxrefund,
				WxRefundRsp.class);
		
		return wxpayorderrsp;
	}

}
