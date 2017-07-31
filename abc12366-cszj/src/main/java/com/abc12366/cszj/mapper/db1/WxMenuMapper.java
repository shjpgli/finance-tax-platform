package com.abc12366.cszj.mapper.db1;

import com.abc12366.cszj.model.weixin.bo.menu.Button;

public interface WxMenuMapper {

	void insert(Button button);

	int update(Button button);

	void deleteByPrimaryKey(String id);

}
