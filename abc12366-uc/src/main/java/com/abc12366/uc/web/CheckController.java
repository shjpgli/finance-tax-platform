package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.Check;
import com.abc12366.uc.model.CheckRank;
import com.abc12366.uc.model.ReCheck;
import com.abc12366.uc.model.bo.CheckListBO;
import com.abc12366.uc.service.CheckService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-21
 * Time: 10:39
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class CheckController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckController.class);

    @Autowired
    private CheckService checkService;

    @PostMapping(path = "/check")
    public ResponseEntity check(@Valid @RequestBody Check check){
        LOGGER.info("{}", check);
        int points = checkService.check(check);
        return ResponseEntity.ok(Utils.kv("data", points));
    }

    @PostMapping(path = "/recheck")
    public ResponseEntity reCheck(@Valid @RequestBody ReCheck check){
        LOGGER.info("{}", check);
        checkService.reCheck(check);
        return ResponseEntity.ok(Utils.kv());
    }

    @GetMapping(path = "/check/rank")
    public ResponseEntity rankingList(@RequestParam(required = false) String year,
                                        @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                      @RequestParam(value = "size", defaultValue = Constant.pageSize) int size){
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<CheckRank> rankList = checkService.rank(year);
        return (rankList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) rankList, "total", ((Page) rankList).getTotal()));
    }

    /**
     * 获取用户的签到情况
     * @param yearMonth
     * @param request
     * @return
     */
    @GetMapping(path = "/check/list")
    public ResponseEntity checklist(@RequestParam(required = true) String yearMonth){
        LOGGER.info("{}", yearMonth);
        List<CheckListBO> checkList = checkService.checklist(yearMonth);
        return ResponseEntity.ok(Utils.kv("dataList", checkList));
    }
}
