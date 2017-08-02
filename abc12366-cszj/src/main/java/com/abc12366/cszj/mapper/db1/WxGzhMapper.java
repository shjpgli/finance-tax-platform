package com.abc12366.cszj.mapper.db1;

import com.abc12366.cszj.model.weixin.bo.gzh.GzhInfo;

public interface WxGzhMapper {

	void insert(GzhInfo gzhInfo);

	int update(GzhInfo gzhInfo);

	void delete(String id);

}
