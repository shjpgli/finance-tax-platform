package com.abc12366.uc.model.invoice.bo;
import java.io.Serializable;


/**
 *
 * 发票领用明细表
 *
 **/
@SuppressWarnings("serial")
public class InvoiceUseDetailBO implements Serializable {

	/****/
	private String useId;

	/**发票种类代码，字典表ID**/
	private String invoiceTypeCode;

	/**库存数量**/
	private Integer repoNum;

	/**申请数量**/
	private Integer applyNum;

	/**实发数量**/
	private Integer realNum;

	/**备注**/
	private String remark;

	/**可用份数**/
	private Integer usableShare;

	/**库存本数**/
	private Integer repoBook;




	public void setUseId(String useId){
		this.useId = useId;
	}

	public String getUseId(){
		return this.useId;
	}

	public void setInvoiceTypeCode(String invoiceTypeCode){
		this.invoiceTypeCode = invoiceTypeCode;
	}

	public String getInvoiceTypeCode(){
		return this.invoiceTypeCode;
	}

	public void setRepoNum(Integer repoNum){
		this.repoNum = repoNum;
	}

	public Integer getRepoNum(){
		return this.repoNum;
	}

	public void setApplyNum(Integer applyNum){
		this.applyNum = applyNum;
	}

	public Integer getApplyNum(){
		return this.applyNum;
	}

	public void setRealNum(Integer realNum){
		this.realNum = realNum;
	}

	public Integer getRealNum(){
		return this.realNum;
	}

	public void setRemark(String remark){
		this.remark = remark;
	}

	public String getRemark(){
		return this.remark;
	}

	public Integer getUsableShare() {
		return usableShare;
	}

	public void setUsableShare(Integer usableShare) {
		this.usableShare = usableShare;
	}

	public Integer getRepoBook() {
		return repoBook;
	}

	public void setRepoBook(Integer repoBook) {
		this.repoBook = repoBook;
	}
}
