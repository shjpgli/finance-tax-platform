package com.abc12366.uc.model.weixin.bo.menu;


import com.abc12366.uc.model.weixin.BaseWxRespon;

/**
 * 微信菜单
 * @author zhushuai 2017-7-31
 *
 */
public class Menu extends BaseWxRespon {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Button[] button;

	public Button[] getButton() {
		return button;
	}

	public void setButton(Button[] button) {
		this.button = button;
	}
}
