package com.abc12366.cszj.mapper.db2;

import java.util.List;

import com.abc12366.cszj.model.weixin.bo.gzh.GzhInfo;

public interface WxGzhRoMapper {

	List<GzhInfo> selectList(GzhInfo gzhInfo);

	GzhInfo selectOne(String id);

}
