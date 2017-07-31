package com.abc12366.cszj.mapper.db2;

import java.util.List;

import com.abc12366.cszj.model.weixin.bo.menu.Button;



public interface WxMenuRoMapper {

	List<Button> seletSec(String parentId);

	List<Button> seletFisrt();

	Button selectOne(String id);

}
