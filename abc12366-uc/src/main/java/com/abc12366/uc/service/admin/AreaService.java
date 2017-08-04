package com.abc12366.uc.service.admin;

import com.abc12366.uc.model.admin.Area;
import com.abc12366.uc.model.admin.City;
import com.abc12366.uc.model.admin.Province;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 3:09 PM
 * @since 1.0.0
 */
public interface AreaService {
    /**
     * 查询省列表
     * @return
     */
    List<Province> selectProvinceList(String provinceId);

    /**
     * 查询市列表
     * @return
     */
    List<City> selectCityList(String cityId);

    /**
     * 根据省ID查询市列表
     * @param provinceId
     * @return
     */
    List<City> selectCityByProId(String provinceId);
    /**
     * 查询区列表
     * @param areaId
     * @return
     */
    List<Area> selectAreaList(String areaId);

    /**
     * 根据城市ID查区域
     * @param cityId
     * @return
     */
    List<Area> selectAreaByCityId(String cityId);
}
