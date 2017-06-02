package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.GoodsCategory;
import com.abc12366.uc.model.bo.GoodsCategoryBO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 
 * GoodsCategoryMapper数据库操作接口类
 * 
 **/

public interface GoodsCategoryRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	GoodsCategory  selectByPrimaryKey(@Param("id") String id);

    GoodsCategoryBO selectById(String id);

    List<GoodsCategoryBO> selectByParentId(String id);

    List<GoodsCategoryBO> selectList(GoodsCategory goodsCategory);
}