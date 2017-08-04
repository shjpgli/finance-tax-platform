package com.abc12366.uc.service;

import com.abc12366.cszj.model.weixin.bo.person.WxPerson;

import java.util.List;

public interface IWxPersonService {
	public boolean startUsersynchro();

	public List<WxPerson> selectList(WxPerson person, int page, int size);

	public WxPerson selectOne(String openid);

	public WxPerson synchroOne(String openid);
}
