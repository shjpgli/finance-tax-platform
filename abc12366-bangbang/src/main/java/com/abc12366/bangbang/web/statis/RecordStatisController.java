package com.abc12366.bangbang.web.statis;

/**
 * 用户操作日志控制器
 *
 * @author lizhongwei
 * @create 2017-12-12
 * @since 1.0.0
 */

import com.abc12366.bangbang.model.SystemRecordCompany;
import com.abc12366.bangbang.model.SystemRecordStatis;
import com.abc12366.bangbang.service.SystemRecordService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/record/statis", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class RecordStatisController {
    private static final Logger LOGGER = LoggerFactory.getLogger(RecordStatisController.class);

    @Autowired
    private SystemRecordService systemRecordService;

    /**
     * 软件用户行为统计
     * @param startTime
     * @param endTime
     * @param page
     * @param size
     * @return
     */
    @GetMapping
    public ResponseEntity statisList(@RequestParam(value = "startTime", required = true) String startTime,
                                     @RequestParam(value = "endTime", required = true) String endTime,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(required = false, defaultValue = Constant.pageNum) int page,
                                     @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        Map<String,Object> map = new HashMap<>();
        if (startTime != null && !"".equals(startTime)) {
            map.put("startTime", DateUtils.strToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            map.put("endTime", DateUtils.strToDate(endTime));
        }
        map.put("name",name);
        List<SystemRecordStatis> data = systemRecordService.statisList(map);
        PageInfo<SystemRecordStatis> pageInfo = new PageInfo<>(data);

        LOGGER.info("{}", data);
        return (data == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 企业使用情况统计
     * @param startTime
     * @param endTime
     * @param page
     * @param size
     * @return
     */
    @GetMapping(path = "/company")
    public ResponseEntity statisCompanyList(@RequestParam(value = "startTime", required = true) String startTime,
                                     @RequestParam(value = "endTime", required = true) String endTime,
                                     @RequestParam(value = "name", required = true) String name,
                                     @RequestParam(required = false, defaultValue = Constant.pageNum) int page,
                                     @RequestParam(required = false, defaultValue = Constant.pageSize) int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        Map<String,Object> map = new HashMap<>();
        if (startTime != null && !"".equals(startTime)) {
            map.put("startTime", DateUtils.strToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            map.put("endTime", DateUtils.strToDate(endTime));
        }
        map.put("name",name);
        List<SystemRecordCompany> data = systemRecordService.statisCompanyList(map);
        PageInfo<SystemRecordCompany> pageInfo = new PageInfo<>(data);

        LOGGER.info("{}", data);
        return (data == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }
}
