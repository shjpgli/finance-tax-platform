package com.abc12366.cszj.model.weixin.bo.templateMsg;

import com.abc12366.cszj.model.weixin.BaseWxRespon;

public class AllIndustryInfo extends BaseWxRespon{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  IndustryInfo primary_industry;
    private  IndustryInfo secondary_industry;
    
	public IndustryInfo getPrimary_industry() {
		return primary_industry;
	}
	public void setPrimary_industry(IndustryInfo primary_industry) {
		this.primary_industry = primary_industry;
	}
	public IndustryInfo getSecondary_industry() {
		return secondary_industry;
	}
	public void setSecondary_industry(IndustryInfo secondary_industry) {
		this.secondary_industry = secondary_industry;
	}
    
    
}
