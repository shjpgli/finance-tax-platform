package com.abc12366.admin.service.impl;

import com.abc12366.admin.mapper.db1.RoleMapper;
import com.abc12366.admin.mapper.db1.RoleMenuMapper;
import com.abc12366.admin.mapper.db1.UserRoleMapper;
import com.abc12366.admin.mapper.db2.*;
import com.abc12366.admin.model.*;
import com.abc12366.admin.model.bo.RoleBO;
import com.abc12366.admin.service.AreaService;
import com.abc12366.admin.service.RoleService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

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
