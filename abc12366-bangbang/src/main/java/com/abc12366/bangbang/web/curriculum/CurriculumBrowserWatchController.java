package com.abc12366.bangbang.web.curriculum;

import com.abc12366.bangbang.model.curriculum.CurriculumBrowserWatch;
import com.abc12366.bangbang.service.CurriculumBrowserWatchService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2018/1/30 16:58
 */
@RestController
@RequestMapping(path = "/curriculumBrowserWatch", headers = Constant.VERSION_HEAD + "=1")
public class CurriculumBrowserWatchController {

    @Autowired
    private CurriculumBrowserWatchService curriculumBrowserWatchService;


    /**
     * 查询今天数量
     */
    @GetMapping(path = "/selectTodayNum")
    public ResponseEntity selectTodayNum() {
        CurriculumBrowserWatch data = curriculumBrowserWatchService.selectTodayNum();
        return ResponseEntity.ok(Utils.kv("data", data));
    }


    /**
     * 查询本月数量
     */
    @GetMapping(path = "/selectCurrentMonthNum")
    public ResponseEntity selectCurrentMonthNum() {
        CurriculumBrowserWatch data = curriculumBrowserWatchService.selectCurrentMonthNum();
        return ResponseEntity.ok(Utils.kv("data", data));
    }


}
