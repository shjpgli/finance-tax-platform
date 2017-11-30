package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.UserService;
import com.abc12366.uc.util.StringUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * User用户统计
 *
 * @author lizhongwei
 * @create 2017-11-21 3:18 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/user/statis", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class UserStatisController {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserStatisController.class);

    @Autowired
    private UserService userService;

    /**
     * 统计用户，统计维度为【月份】
     *
     * @param startTime  开始时间
     * @param endTime  结束时间
     * @return
     */
    @GetMapping(path = "/month")
    public ResponseEntity statisUser(@RequestParam(value = "startTime", required = false) String startTime,
                                      @RequestParam(value = "endTime", required = false) String endTime) {
        Map<String,Object> map = new HashMap<>();
        if (startTime != null && !"".equals(startTime)) {
            map.put("startTime", DateUtils.strToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            map.put("endTime", DateUtils.strToDate(endTime));
        }

        List<UserStatisBO> list = userService.statisUserByMonth(map);
        PageInfo<UserStatisBO> pageInfo = new PageInfo<>(list);
        LOGGER.info("{}", list);
        return (list == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 统计用户，列表查询
     *
     * @param startTime  开始时间
     * @param endTime  结束时间
     * @return
     */
    @GetMapping(path = "/list")
    public ResponseEntity statisUserList(@RequestParam(value = "startTime", required = false) String startTime,
                                      @RequestParam(value = "endTime", required = false) String endTime) {
        Map<String,Object> map = new HashMap<>();
        if (startTime != null && !"".equals(startTime)) {
            map.put("startTime", DateUtils.strToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            map.put("endTime", DateUtils.strToDate(endTime));
        }

        List<UserSimpleInfoBO> list = userService.statisUserList(map);
        PageInfo<UserSimpleInfoBO> pageInfo = new PageInfo<>(list);
        LOGGER.info("{}", list);
        return (list == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 用户流失率统计
     * @param yearTime 时间（年度）
     * @param months 流失间隔周期（1个月、2个月、3个月…12个月）
     * @return
     */
    @GetMapping(path = "/loss")
    public ResponseEntity statisUserLossRate(@RequestParam(value = "yearTime", required = false) String yearTime,
                                         @RequestParam(value = "months", required = false) Integer months) {
        Map<String,Object> map = new HashMap<>();
        if (yearTime != null && !"".equals(yearTime)) {
            map.put("yearTime", DateUtils.strToDateMonth(yearTime));
        }
        if (months != null) {
            map.put("months", months);
            map.put("startTime", DateUtils.getAddMonth(DateUtils.strToDateMonth(yearTime),months-1));
            map.put("endTime", DateUtils.strToDateMonth(yearTime));
        }

        UserLossRateBO data = userService.statisUserLossRate(map);
//        PageInfo<UserLossRateBO> pageInfo = new PageInfo<>(list);
        LOGGER.info("{}", data);
        return (data == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("data",data));
    }

    /**
     * 用户留存率统计
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @GetMapping(path = "/reta")
    public ResponseEntity statisUserRetainedRate(@RequestParam(value = "startTime", required = true) String startTime,
                                                 @RequestParam(value = "endTime", required = true) String endTime) {
        Map<String,Object> map = new HashMap<>();
        if (startTime != null && !"".equals(startTime)) {
            map.put("startTime", startTime);
        }
        if (endTime != null && !"".equals(endTime)) {
            map.put("endTime", endTime);
        }
        List<UserRetainedRateListBO> list = userService.statisUserRetainedRate(map);
        PageInfo<UserRetainedRateListBO> pageInfo = new PageInfo<>(list);
        LOGGER.info("{}", list);
        return (list == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    /**
     * 用户消费能力分析，用户列表
     * R表示最近一次消费间隔统计时间段截止日期的天数，F表示统计时间段内消费次数，M表示统计时间段内容消费总金额。
     * @param startDay R开始天数
     * @param endDay   R结束天数
     * @param startCount F开始次数
     * @param endCount F结束次数
     * @param startPrice M开始总金额
     * @param endPrice M结束总金额
     * @return
     */
    @GetMapping(path = "/consume")
    public ResponseEntity statisUserConsumeLevel(@RequestParam(value = "startDay", required = true) int startDay,
                                                 @RequestParam(value = "endDay", required = true) int endDay,
                                                 @RequestParam(value = "startCount", required = true) int startCount,
                                                 @RequestParam(value = "endCount", required = true) int endCount,
                                                 @RequestParam(value = "startPrice", required = true) double startPrice,
                                                 @RequestParam(value = "endPrice", required = true) double endPrice,
                                                 @RequestParam(value = "startTime", required = true) String startTime,
                                                 @RequestParam(value = "endTime", required = true) String endTime,
                                                 @RequestParam(value = "tradeMethod", required = true) String tradeMethod
                                                 ) {
        Map<String,Object> map = new HashMap<>();
        map.put("startDay",startDay);
        map.put("endDay",endDay);
        map.put("startCount",startCount);
        map.put("endCount",endCount);
        map.put("startPrice",startPrice);
        map.put("endPrice",endPrice);
        map.put("tradeMethod", tradeMethod);
        map.put("orderStatus", "6");
        if (startTime != null && !"".equals(startTime)) {
            map.put("startTime", DateUtils.strToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            map.put("endTime", DateUtils.strToDate(endTime));
        }
        List<UserExprotInfoBO> data = userService.statisUserConsumeLevel(map);
        //PageInfo< Map<Object, Object>> pageInfo = new PageInfo<>((List<Object>) list);
        LOGGER.info("list{}", data);
        return (data == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("data",data));
    }

    /**
     * 用户消费能力分析
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @GetMapping(path = "/consume/rfm")
    public ResponseEntity statisUserConsumeRFM(@RequestParam(value = "startTime", required = true) String startTime,
                                                 @RequestParam(value = "endTime", required = true) String endTime,
                                               @RequestParam(value = "tradeMethod", required = true) String tradeMethod) {
        Map<String,Object> map = new HashMap<>();
        if (startTime != null && !"".equals(startTime)) {
            map.put("startTime", DateUtils.strToDate(startTime));
        }
        if (endTime != null && !"".equals(endTime)) {
            map.put("endTime", DateUtils.strToDate(endTime));
        }
        map.put("tradeMethod", tradeMethod);
        map.put("orderStatus", "6");
        UserRFMBO data = userService.statisUserRFM(map);
        LOGGER.info("{}", data);
        return (data == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4104), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("data",data));
    }


}
