package com.abc12366.uc.model.gift.bo;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;
import java.io.Serializable;


/**
 * 
 * 礼包发货BO
 * 
 **/
@SuppressWarnings("serial")
public class GiftSendBO implements Serializable {


	/**申请单号**/
	private String applyId;

	/**快递单号**/
    @NotEmpty
	@Size(min = 0, max = 16)
	private String expressNo;

	/**快递公司**/
    @NotEmpty
	@Size(min = 0, max = 100)
	private String expressComp;

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getExpressNo() {
		return expressNo;
	}

	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}

	public String getExpressComp() {
		return expressComp;
	}

	public void setExpressComp(String expressComp) {
		this.expressComp = expressComp;
	}
}
