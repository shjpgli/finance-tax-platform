package com.abc12366.uc.mapper.db2;

import java.util.List;

import com.abc12366.cszj.model.weixin.bo.person.WxPerson;

public interface WxPersonRoMapper {

	long countPersonNum(WxPerson wxPerson);

	List<WxPerson> selectList(WxPerson person);

	WxPerson selectOne(String openid);

}
