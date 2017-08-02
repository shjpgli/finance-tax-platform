package com.abc12366.cszj.service;

import java.util.List;

import com.abc12366.cszj.model.weixin.bo.gzh.GzhInfo;

public interface IWxGzhService {

	List<GzhInfo> wxgzhList(GzhInfo gzhInfo, int page, int size);

	GzhInfo selectOne(String id);

	GzhInfo insert(GzhInfo gzhInfo);

	GzhInfo update(GzhInfo gzhInfo);

	void delete(String id);

}
