package com.abc12366.admin.mapper.db2;

import com.abc12366.admin.model.Menu;
import com.abc12366.admin.model.bo.MenuBO;

import java.util.List;

/**
 *
 * @author liuguiyao
 * @create 2017-04-27 10:08 AM
 * @since 1.0.0
 */
public interface MenuRoMapper {
    List<Menu> selectList();

    List<Menu> selectByParentId(String parentId);

    Menu selectByMenuId(String id);

    Menu selectByMenuName(String name);

    List<Menu> selectFirstLevel();
}
