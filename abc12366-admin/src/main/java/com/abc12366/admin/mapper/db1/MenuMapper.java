package com.abc12366.admin.mapper.db1;

import com.abc12366.admin.model.Menu;

/**
 *
 *
 * @author liuguiyao
 * @create 2017-04-27 10:08 AM
 * @since 1.0.0
 */
public interface MenuMapper {
    int insert(Menu menu);

    int delete(String menuId);

    int update(Menu menu);
}
