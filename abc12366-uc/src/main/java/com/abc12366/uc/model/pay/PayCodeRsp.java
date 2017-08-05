package com.abc12366.uc.model.pay;

import java.io.Serializable;

public class PayCodeRsp implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String out_trade_no;
	private String qccodeStr;
	
	public PayCodeRsp(String outTradeNo, String creatQRcodeString) {
		this.out_trade_no=outTradeNo;
		this.qccodeStr=creatQRcodeString;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getQccodeStr() {
		return qccodeStr;
	}
	public void setQccodeStr(String qccodeStr) {
		this.qccodeStr = qccodeStr;
	}
    
    
}
