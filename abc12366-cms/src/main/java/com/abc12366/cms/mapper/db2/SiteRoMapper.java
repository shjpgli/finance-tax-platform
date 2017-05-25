package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Site;
import com.abc12366.cms.model.bo.SiteBo;
import com.abc12366.cms.model.bo.SiteListBo;
import com.abc12366.cms.vo.SiteVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * SiteMapper数据库操作接口类
 **/

public interface SiteRoMapper {


    /**
     * 查询（根据主键ID查询）
     **/
    Site selectByPrimaryKey(@Param("siteId") String siteId);

    /**
     * 查询所有
     **/
    List<SiteListBo> selectList();

}