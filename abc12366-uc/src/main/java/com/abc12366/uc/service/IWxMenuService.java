package com.abc12366.uc.service;

import com.abc12366.uc.model.weixin.BaseWxRespon;
import com.abc12366.uc.model.weixin.bo.menu.Button;
import com.abc12366.uc.model.weixin.bo.menu.WxMenu;

public interface IWxMenuService {

    public BaseWxRespon creatWxMenu(WxMenu wxMenu);

    public WxMenu getWxMenu();

    public BaseWxRespon delWxMenu();

    public WxMenu getWxMenuDb();

    public Button selectOne(String id);

    public Button insert(Button button);

    public Button update(Button button);

    public void delete(String id);
}
