package com.abc12366.admin.web;

import com.abc12366.admin.model.*;
import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.model.bo.UserExtendBO;
import com.abc12366.admin.model.bo.UserPasswordBO;
import com.abc12366.admin.model.bo.UserUpdateBO;
import com.abc12366.admin.service.AreaService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lizhongwei
 * @create 2017-05-02 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AreaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AreaController.class);

    @Autowired
    private AreaService areaService;

    /**
     * 省列表
     * @param provinceId
     * @return
     */
    @GetMapping(path = "/province")
    public ResponseEntity selectProvinceList(@RequestParam(value = "provinceId", required = false) String provinceId) {
        List<Province> provinceList = areaService.selectProvinceList(provinceId);
        LOGGER.info("{}", provinceList);
        return ResponseEntity.ok(provinceList);
    }

    /**
     * 市列表
     * @param cityId
     * @return
     */
    @GetMapping(path = "/city")
    public ResponseEntity selectCityList(@RequestParam(value = "cityId", required = false) String cityId) {
        List<City> cityList = areaService.selectCityList(cityId);
        LOGGER.info("{}", cityList);
        return ResponseEntity.ok(cityList);
    }

    /**
     * 根据省ID查市
     * @param provinceId
     * @return
     */
    @GetMapping(path = "/city/{provinceId}")
    public ResponseEntity selectCityByProId(@PathVariable("provinceId") String provinceId) {
        List<City> cityList = areaService.selectCityByProId(provinceId);
        LOGGER.info("{}", cityList);
        return ResponseEntity.ok(cityList);
    }

    /**
     * 区列表
     * @param areaId
     * @return
     */
    @GetMapping(path = "/area")
    public ResponseEntity selectAreaList(@RequestParam(value = "areaId", required = false) String areaId) {
        List<Area> areaList = areaService.selectAreaList(areaId);
        LOGGER.info("{}", areaList);
        return ResponseEntity.ok(areaList);
    }

    /**
     * 根据市ID查区
     * @return
     */
    @GetMapping(path = "/area/{cityId}")
    public ResponseEntity selectAreaByCityId(@PathVariable("cityId") String cityId) {
        List<Area> areaList = areaService.selectAreaByCityId(cityId);
        LOGGER.info("{}", areaList);
        return ResponseEntity.ok(areaList);
    }


    /**
     * 查询所有省市区
     * @return
     */
    @GetMapping(path = "/provincecityarea")
    public ResponseEntity selectAll() {

        List<Province> provinceList = areaService.selectProvinceList("");
        List<City> cityList = areaService.selectCityList("");
        List<Area> areaList = areaService.selectAreaList("");
        LOGGER.info("{}", provinceList,cityList,areaList);
        return (provinceList == null) && (cityList == null) && (areaList == null) ?
                ResponseEntity.ok(null) :
                ResponseEntity.ok(Utils.kv("provinceList", provinceList, "cityList", cityList, "areaList",  areaList));
    }

}
