package com.abc12366.cszj.model.weixin.bo.templateMsg;

import java.io.Serializable;

public class IndustryInfo implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String first_class;
    private String second_class;
	public String getFirst_class() {
		return first_class;
	}
	public void setFirst_class(String first_class) {
		this.first_class = first_class;
	}
	public String getSecond_class() {
		return second_class;
	}
	public void setSecond_class(String second_class) {
		this.second_class = second_class;
	}
    
    
}
