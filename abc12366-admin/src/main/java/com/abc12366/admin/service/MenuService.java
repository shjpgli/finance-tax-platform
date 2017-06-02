package com.abc12366.admin.service;

import com.abc12366.admin.model.Menu;
import com.abc12366.admin.model.bo.MenuBO;
import com.abc12366.admin.model.bo.MenuUpdateBO;

import java.util.List;

/**
 *
 * @author liuguiyao
 * @create 2017-04-27 10:08 AM
 * @since 1.0.0
 */
public interface MenuService {
    List<MenuBO> selectList(Menu menu);

    MenuBO selectByParentId(String parentId);

    MenuBO selectByMenuId(String menuId);

    //MenuBO selectByMenuName(String MenuName);

    MenuBO insert(MenuBO menuBO);

    void delete(String menuId);

    MenuBO update(MenuBO menuBO);

    List<MenuBO> selectFirstLevel();

    void disableAll();

    void enable(MenuUpdateBO updateBO);
}
