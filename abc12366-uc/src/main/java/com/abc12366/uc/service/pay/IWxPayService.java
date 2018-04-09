package com.abc12366.uc.service.pay;

import com.abc12366.uc.model.pay.WxRefund;
import com.abc12366.uc.model.pay.WxRefundRsp;

public interface IWxPayService {
     public WxRefundRsp doWxRefund(WxRefund wxrefund);
}
