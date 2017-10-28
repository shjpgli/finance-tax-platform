package com.abc12366.bangbang.web.question;

import com.abc12366.bangbang.model.question.QuestionFactionRewardSetting;
import com.abc12366.bangbang.model.question.bo.QuestionFactionRewardSettingBo;
import com.abc12366.bangbang.model.question.bo.QuestionFactionRewardSettingParamBo;
import com.abc12366.bangbang.service.QuestionFactionRewardSettingService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author liuQi
 * @Date 2017/10/28 15:35
 */
@RestController
@RequestMapping(path = "/questionFactionRewardSetting", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class QuestionFactionRewardSettingController {

    @Autowired
    private QuestionFactionRewardSettingService questionFactionRewardSettingService;

    /* 列表查询 */
    @GetMapping(path = "/list")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     QuestionFactionRewardSettingParamBo param){
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionFactionRewardSettingBo> list = questionFactionRewardSettingService.selectFactionRewardSettingList(param);

        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }

    /* 添加 */
    @PostMapping(path = "/add")
    public ResponseEntity add(@RequestBody QuestionFactionRewardSetting record) {
        questionFactionRewardSettingService.setting(record);
        return ResponseEntity.ok(Utils.kv("data", record));
    }



}
