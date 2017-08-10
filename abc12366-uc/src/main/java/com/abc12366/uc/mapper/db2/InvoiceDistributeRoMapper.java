package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.invoice.InvoiceDistribute;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * InvoiceDistributeMapper数据库操作接口类
 * 
 **/

public interface InvoiceDistributeRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	InvoiceDistribute  selectByPrimaryKey(@Param("id") String id);


}