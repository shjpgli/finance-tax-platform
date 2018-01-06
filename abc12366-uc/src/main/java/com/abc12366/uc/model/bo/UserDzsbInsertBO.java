package com.abc12366.uc.model.bo;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Admin: liuguiyao<435720953@qq.com> Date: 2017-07-26 Time: 16:58
 */
public class UserDzsbInsertBO {
	@NotEmpty
	private String nsrsbhOrShxydm;
	@NotEmpty
	private String fwmm;
	@Size(max = 10, message = "分组最大长度不能超过10位")
	private String bdgroup;
	@Size(max = 150, message = "分组最大长度不能超过150位")
	private String remark;

	public String getBdgroup() {
		return bdgroup;
	}

	public void setBdgroup(String bdgroup) {
		this.bdgroup = bdgroup;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public UserDzsbInsertBO() {
	}

	public String getFwmm() {
		return fwmm;
	}

	public void setFwmm(String fwmm) {
		this.fwmm = fwmm;
	}

	public String getNsrsbhOrShxydm() {
		return nsrsbhOrShxydm;
	}

	public void setNsrsbhOrShxydm(String nsrsbhOrShxydm) {
		this.nsrsbhOrShxydm = nsrsbhOrShxydm;
	}

	@Override
	public String toString() {
		return "UserDzsbInsertBO{" + "nsrsbhOrShxydm='" + nsrsbhOrShxydm + '\'' + ", fwmm='" + fwmm + '\'' + '}';
	}
}
