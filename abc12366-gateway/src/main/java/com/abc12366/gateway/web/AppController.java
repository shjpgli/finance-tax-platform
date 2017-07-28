package com.abc12366.gateway.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.gateway.model.bo.AppBO;
import com.abc12366.gateway.service.AppService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 应用控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-05 12:59 PM
 * @since 1.0.0
 */
@Controller
@RequestMapping(path = "/app", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AppController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppService appService;

    @PostMapping(path = "/register")
    public ResponseEntity register(@Valid @RequestBody AppBO appBO) throws Exception {
        LOGGER.info("{}", appBO);
        AppBO app = appService.register(appBO);
        LOGGER.info("{}", app);
        return ResponseEntity.ok(Utils.kv("data", app));
    }

    @PostMapping(path = "/login")
    public ResponseEntity login(@Valid @RequestBody AppBO appBO) throws Exception {
        LOGGER.info("{}", appBO);
        String token = appService.login(appBO);
        return token != null ? ResponseEntity.ok(
                Utils.kv("token", token, "expires_in", Constant.APP_TOKEN_VALID_SECONDS))
                : new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(required = false,value = "name") String name,
                                     @RequestParam(required = false,value = "startTime") String startTime,
                                     @RequestParam(required = false,value = "endTime") String endTime,
                                     @RequestParam(required = false,value = "status") Boolean status
    ) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        if(name!=null&&StringUtils.isEmpty(name)){
        	name=null;
        }
        Date start=null;
        if(startTime!=null){
        	try {
				start=new SimpleDateFormat("yyyy-MM-dd").parse(startTime);
			} catch (ParseException e) {
				start=null;
			}
        }
        Date end=null;
        if(endTime!=null){
        	try {
				end=new SimpleDateFormat("yyyy-MM-dd").parse(endTime);
			} catch (ParseException e) {
				end=null;
			}
        }
        AppBO appBO=new AppBO();
        appBO.setName(name);
        appBO.setStartTime(start);
        appBO.setEndTime(end);
        appBO.setStatus(status);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<AppBO> appList = appService.selectList(appBO);
        LOGGER.info("{}", appList);
        return (appList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) appList, "total", ((Page) appList).getTotal()));
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity selectById(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        AppBO app = appService.selectById(id);
        LOGGER.info("{}", app);
        return ResponseEntity.ok(Utils.kv("data", app));
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@RequestBody AppBO appUpdateBO, @PathVariable("id") String id) {
        LOGGER.info("{}", appUpdateBO);
        appUpdateBO.setId(id);
        AppBO app = appService.update(appUpdateBO);
        LOGGER.info("{}", app);
        return ResponseEntity.ok(Utils.kv("data", app));
    }
}
