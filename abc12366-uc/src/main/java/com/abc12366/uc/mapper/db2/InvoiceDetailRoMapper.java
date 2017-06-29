package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.InvoiceDetail;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * InvoiceDetailMapper数据库操作接口类
 * 
 **/

public interface InvoiceDetailRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	InvoiceDetail  selectByPrimaryKey(@Param("id") String id);


}