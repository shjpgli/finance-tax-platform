package com.abc12366.cms.web.curriculum;

import com.abc12366.cms.model.curriculum.bo.CurriculumApplyBo;
import com.abc12366.cms.service.CurrApplyService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学堂课程报名签到管理模块
 *
 * @author xieyanmao
 * @create 2017-08-11
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/currApply", headers = Constant.VERSION_HEAD + "=1")
public class CurrApplyController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrApplyController.class);

    @Autowired
    private CurrApplyService currApplyService;

    /**
     * 课程报名签到列表查询
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "curriculumId", required = false) String curriculumId,
                                     @RequestParam(value = "begintime", required = false) String begintime,
                                     @RequestParam(value = "endtime", required = false) String endtime) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("curriculumId",curriculumId);
        dataMap.put("begintime", begintime);//开始时间
        dataMap.put("endtime", endtime);//结束时间
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<CurriculumApplyBo> dataList = currApplyService.selectList(dataMap);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 课程报名签到新增
     */
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody CurriculumApplyBo applyBo) {
        //新增课程报名签到信息
        applyBo = currApplyService.save(applyBo);
        return ResponseEntity.ok(Utils.kv("data", applyBo));
    }

    /**
     * 查询单个课程报名签到信息
     */
    @GetMapping(path = "/{applyId}")
    public ResponseEntity selectOne(@PathVariable String applyId) {
        //查询课程报名签到信息
        CurriculumApplyBo applyBo = currApplyService.selectApply(applyId);
        return ResponseEntity.ok(Utils.kv("data", applyBo));
    }

    /**
     * 更新课程报名签到信息
     */
    @PutMapping(path = "/{applyId}")
    public ResponseEntity update(@PathVariable String applyId,
                                 @Valid @RequestBody CurriculumApplyBo applyBo) {
        //更新课程报名签到信息
        applyBo = currApplyService.update(applyBo);
        return ResponseEntity.ok(Utils.kv("data", applyBo));
    }

    /**
     * 更新课程报名签到状态
     *
     * @param status
     * @param applyId
     * @return
     */
    @PutMapping(path = "/updateStatus/{applyId}")
    public ResponseEntity updateStatus(@Valid @RequestBody String status, @PathVariable("applyId") String applyId) {
        currApplyService.updateStatus(applyId, status);
        return ResponseEntity.ok(Utils.kv("data", applyId));
    }

    /**
     * 删除课程报名签到信息
     */
    @DeleteMapping(path = "/{applyId}")
    public ResponseEntity delete(@PathVariable String applyId) {
        //删除课程报名签到信息
        String rtn = currApplyService.delete(applyId);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

}
