package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.UserBO;
import com.abc12366.uc.model.bo.UserLossRateBO;
import com.abc12366.uc.model.bo.UserLivenessYearBO;
import com.abc12366.uc.model.bo.UserSimpleInfoBO;
import com.abc12366.uc.model.bo.UserStatisBO;
import com.abc12366.uc.service.UserService;
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
     * 用户活跃度统计接口
     * @param year 年份
     * @return ResponseEntity
     */
    @GetMapping(path = "/liveness")
    public ResponseEntity userLiveness(@RequestParam String year){
        LOGGER.info("查询用户活跃度统计：{}", year);
        UserLivenessYearBO userLivenessYearBO = userService.userLiveness(year);
        LOGGER.info("查询用户活跃度统计结果返回：{}", userLivenessYearBO);
        return ResponseEntity.ok(Utils.kv("dataList",userLivenessYearBO));
    }

    /**
     * 用户经验值等级统计
     * @param year 年份
     * @return ResponseEntity
     */
    @GetMapping(path = "/explevel")
    public ResponseEntity userExpLevel(@RequestParam String year){
        LOGGER.info("查询用户经验值等级统计：{}", year);
        userService.userExpLevel(year);
        LOGGER.info("查询用户经验值等级统计结果返回：{}");
        return ResponseEntity.ok(Utils.kv("dataList",null));
    }

    /**
     * 用户会员等级统计
     * @param year 年份
     * @return ResponseEntity
     */
    @GetMapping(path = "/viplevel")
    public ResponseEntity userVipLevel(@RequestParam String year){
        LOGGER.info("查询用户活跃度统计：{}", year);
        UserLivenessYearBO userLivenessYearBO = userService.userLiveness(year);
        LOGGER.info("查询用户活跃度统计结果返回：{}", userLivenessYearBO);
        return ResponseEntity.ok(Utils.kv("dataList",userLivenessYearBO));
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



}
