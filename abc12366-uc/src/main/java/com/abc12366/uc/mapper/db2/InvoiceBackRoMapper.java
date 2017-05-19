package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.InvoiceBack;
import org.apache.ibatis.annotations.Param;

/**
 * 
 * InvoiceBackMapper数据库操作接口类
 * 
 **/

public interface InvoiceBackRoMapper {


	/**
	 * 
	 * 查询（根据主键ID查询）
	 * 
	 **/
	InvoiceBack  selectById(@Param("id") String id);


}