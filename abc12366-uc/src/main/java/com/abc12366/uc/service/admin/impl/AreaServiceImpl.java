package com.abc12366.uc.service.admin.impl;

import com.abc12366.uc.mapper.db2.AreaRoMapper;
import com.abc12366.uc.mapper.db2.CityRoMapper;
import com.abc12366.uc.mapper.db2.ProvinceRoMapper;
import com.abc12366.uc.model.Area;
import com.abc12366.uc.model.City;
import com.abc12366.uc.model.Province;
import com.abc12366.uc.service.admin.AreaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    private static Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);

    @Autowired
    private AreaRoMapper areaRoMapper;

    @Autowired
    private CityRoMapper cityRoMapper;

    @Autowired
    private ProvinceRoMapper provinceRoMapper;

    @Override
    public List<Province> selectProvinceList(String provinceId) {
        return provinceRoMapper.selectProvinceList(provinceId);
    }

    @Override
    public List<City> selectCityList(String cityId) {
        return cityRoMapper.selectCityList(cityId);
    }

    @Override
    public List<City> selectCityByProId(String provinceId) {
        return cityRoMapper.selectCityByProId(provinceId);
    }

    @Override
    public List<Area> selectAreaList(String areaId) {
        return areaRoMapper.selectAreaList(areaId);
    }

    @Override
    public List<Area> selectAreaByCityId(String cityId) {
        return areaRoMapper.selectAreaByCityId(cityId);
    }
}
