package com.abc12366.uc.model.dzfp;

import java.io.Serializable;


public class Einvocie implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String returnCode;
	private String returnMessage;
    private String FPQQLSH;//发票请求流水号
    private String FP_DM;//发票代码
    private String FP_HM;//发票号码
    private String JYM;//发票校验码
    private String KPRQ;//开票日期
    private String PDF_URL;//pdf下载地址
    private String SP_URL;//收票地址
    
    
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
	public String getFPQQLSH() {
		return FPQQLSH;
	}
	public void setFPQQLSH(String fPQQLSH) {
		FPQQLSH = fPQQLSH;
	}
	public String getFP_DM() {
		return FP_DM;
	}
	public void setFP_DM(String fP_DM) {
		FP_DM = fP_DM;
	}
	public String getFP_HM() {
		return FP_HM;
	}
	public void setFP_HM(String fP_HM) {
		FP_HM = fP_HM;
	}
	public String getJYM() {
		return JYM;
	}
	public void setJYM(String jYM) {
		JYM = jYM;
	}
	public String getKPRQ() {
		return KPRQ;
	}
	public void setKPRQ(String kPRQ) {
		KPRQ = kPRQ;
	}
	public String getPDF_URL() {
		return PDF_URL;
	}
	public void setPDF_URL(String pDF_URL) {
		PDF_URL = pDF_URL;
	}
	public String getSP_URL() {
		return SP_URL;
	}
	public void setSP_URL(String sP_URL) {
		SP_URL = sP_URL;
	}

	@Override
	public String toString() {
		return "Einvocie{" +
				"returnCode='" + returnCode + '\'' +
				", returnMessage='" + returnMessage + '\'' +
				", FPQQLSH='" + FPQQLSH + '\'' +
				", FP_DM='" + FP_DM + '\'' +
				", FP_HM='" + FP_HM + '\'' +
				", JYM='" + JYM + '\'' +
				", KPRQ='" + KPRQ + '\'' +
				", PDF_URL='" + PDF_URL + '\'' +
				", SP_URL='" + SP_URL + '\'' +
				'}';
	}
}
