package com.abc12366.uc.web.pay;

import java.util.Date;
import java.util.Random;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.pay.WxPayOrder;
import com.abc12366.uc.model.pay.WxpayOrderRsp;
import com.abc12366.uc.util.QRCodeUtil;
import com.abc12366.uc.util.wx.SignUtil;
import com.abc12366.uc.util.wx.WechatUrl;
import com.abc12366.uc.util.wx.WxMchConnectFactory;
import com.alibaba.fastjson.JSONObject;

/**
 * 微信支付
 * 
 * @author Administrator
 *
 */
@RestController
@RequestMapping(path = "/wxpay", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class WxPayController {

	private static final Logger LOGGER = LoggerFactory.getLogger(WxPayController.class);

	/**
	 * 统一下单接口
	 * 
	 * @param wxpayorder
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PostMapping("/payorder")
	public ResponseEntity wxPayOrder(@Valid @RequestBody WxPayOrder wxpayorder) {
		LOGGER.info("微信支付统一下单，收到信息:{}", JSONObject.toJSONString(wxpayorder));
		Date date = new Date();
		wxpayorder.setAppid(SpringCtxHolder.getProperty("abc.appid"))
				.setMch_id(SpringCtxHolder.getProperty("abc.mch_id")).setNonce_str(getRandomString(30))
				.setTime_start(DateUtils.getDateFormat(date, "yyyyMMddHHmmss"))
				.setTime_expire(DateUtils.getDateFormat(DateUtils.addHours(date, 2), "yyyyMMddHHmmss"))
				.setNotify_url(SpringCtxHolder.getProperty("abc.mch_rturl"))
				.setSign(SignUtil.signKey(wxpayorder));

		WxpayOrderRsp wxpayorderrsp = WxMchConnectFactory.post(WechatUrl.PAYORDER, null, wxpayorder,
				WxpayOrderRsp.class);
		LOGGER.info("微信支付统一下单，微信返回信息:{}", JSONObject.toJSONString(wxpayorderrsp));
		if (wxpayorderrsp != null) {
			if ("SUCCESS".equals(wxpayorderrsp.getReturn_code())) {
				if ("SUCCESS".equals(wxpayorderrsp.getResult_code())) {
					if ("NATIVE".equals(wxpayorderrsp.getTrade_type())) {
						wxpayorderrsp
								.setCode_img(QRCodeUtil.getCreatQRcodeString(wxpayorderrsp.getCode_url(), 200, "bmp"));
					}
				} else {
					return ResponseEntity
							.ok(Utils.bodyStatus(wxpayorderrsp.getResult_code(), wxpayorderrsp.getErr_code_des()));
				}
			} else {
				return ResponseEntity
						.ok(Utils.bodyStatus(wxpayorderrsp.getReturn_code(), wxpayorderrsp.getReturn_msg()));
			}
			return ResponseEntity.ok(Utils.kv("data", wxpayorderrsp.getReturn()));
		}else{
			return ResponseEntity.ok(Utils.bodyStatus(9999, "微信支付下单异常!!"));
		}
	}

	/**
	 * 获取指定位数随机字符串
	 * @param length
	 * @return
	 */
	private static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; ++i) {
			int number = random.nextInt(52);
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

}
