package com.abc12366.gateway.web;

import com.abc12366.gateway.model.AdminLog;
import com.abc12366.gateway.model.ApiLog;
import com.abc12366.gateway.model.bo.AdminLogBO;
import com.abc12366.gateway.service.AdminLogService;
import com.abc12366.gateway.service.ApiLogService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-28 3:51 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ApiLogController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ApiLogService apiLogService;

    @Autowired
    private AdminLogService adminLogService;

    @GetMapping(path = "/log")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<ApiLog> dataList = apiLogService.selectList();
        LOGGER.info("{}", dataList);
        PageInfo<ApiLog> pageInfo = new PageInfo<>(dataList);
        return ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
    }

    @GetMapping(path = "/adminlog")
    public ResponseEntity selectList(@RequestParam(value = "username", required = false) String username,
                                     @RequestParam(value = "nickname", required = false) String nickname,
                                     @RequestParam(value = "businessData", required = false) String businessData,
                                     @RequestParam(value = "businessName", required = false) String businessName,
                                     @RequestParam(value = "startDate", required = false) String startDate,
                                     @RequestParam(value = "endDate", required = false) String endDate,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize) {
        if (startDate == null) {
            startDate = DateUtils.getDateFormat(new Date(), "yyyy-MM-dd");
        }
        if (endDate == null) {
            endDate = DateUtils.getDateFormat(new Date(), "yyyy-MM-dd");
        }
        AdminLog adminLog = new AdminLog.Builder()
                .username(username)
                .nickname(nickname)
                .businessName(businessName)
                .businessData(businessData)
                .startDate(DateUtils.StrToDate(startDate))
                .endDate(DateUtils.StrToDate(endDate))
                .build();
        LOGGER.info("{}", adminLog);
        List<AdminLog> dataList = adminLogService.selectList(pageNum, pageSize, adminLog);
        PageInfo<AdminLog> pageInfo = new PageInfo<>(dataList);

        ResponseEntity re = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
        LOGGER.info("{}", re);
        return re;
    }

    @PostMapping(path = "/adminlog")
    public ResponseEntity insert(@Valid @RequestBody AdminLogBO bo) {
        LOGGER.info("{}", bo);
        AdminLog data = adminLogService.insert(bo);

        ResponseEntity re = ResponseEntity.ok(Utils.kv("data", data));
        LOGGER.info("{}", re);
        return re;
    }
}
