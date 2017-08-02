package com.abc12366.cszj.service;

import com.abc12366.cszj.model.weixin.BaseWxRespon;
import com.abc12366.cszj.model.weixin.bo.menu.Button;
import com.abc12366.cszj.model.weixin.bo.menu.Menu;

public interface IWxMenuService {
     
	 public BaseWxRespon creatWxMenu(Menu menu);
	 
	 public Menu getWxMenu();
	 
	 public BaseWxRespon delWxMenu();

	 public Menu getWxMenuDb();

	public Button selectOne(String id);

	public Button insert(Button button);

	public Button update(Button button);

	public void delete(String id);
}
