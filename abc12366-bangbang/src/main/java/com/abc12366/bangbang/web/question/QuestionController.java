package com.abc12366.bangbang.web.question;

import com.abc12366.bangbang.model.question.bo.QuestionBo;
import com.abc12366.bangbang.model.question.bo.QuestionClassifyTagBo;
import com.abc12366.bangbang.model.question.bo.QuestionTagBo;
import com.abc12366.bangbang.service.QuestionService;
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
 * 帮帮问题管理模块
 *
 * @author xieyanmao
 * @create 2017-09-14
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/question", headers = Constant.VERSION_HEAD + "=1")
public class QuestionController {
    private static final Logger LOGGER = LoggerFactory.getLogger(QuestionController.class);

    @Autowired
    private QuestionService questionService;

    /**
     * 问题列表查询(最新)
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "tag", required = false) String tag,
                                     @RequestParam(value = "classifyCode", required = false) String classifyCode) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);//
        dataMap.put("tag", tag);//
        dataMap.put("classifyCode", classifyCode);//
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionBo> dataList = questionService.selectList(dataMap);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 查询热门问题
     */
    @GetMapping(path = "/selectListByBrowseNum")
    public ResponseEntity selectListByBrowseNum(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "tag", required = false) String tag,
                                     @RequestParam(value = "classifyCode", required = false) String classifyCode) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);//
        dataMap.put("tag", tag);//
        dataMap.put("classifyCode", classifyCode);//
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionBo> dataList = questionService.selectListByBrowseNum(dataMap);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 查询等你回答的问题
     */
    @GetMapping(path = "/selectListWait")
    public ResponseEntity selectListWait(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "title", required = false) String title,
                                         @RequestParam(value = "userId", required = false) String userId,
                                     @RequestParam(value = "tag", required = false) String tag,
                                     @RequestParam(value = "classifyCode", required = false) String classifyCode) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);//
        dataMap.put("userId", userId);//
        dataMap.put("tag", tag);//
        dataMap.put("classifyCode", classifyCode);//
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionBo> dataList = questionService.selectListWait(dataMap);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 查询已解决的问题
     */
    @GetMapping(path = "/selectListAccept")
    public ResponseEntity selectListAccept(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "tag", required = false) String tag,
                                     @RequestParam(value = "classifyCode", required = false) String classifyCode) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);//
        dataMap.put("tag", tag);//
        dataMap.put("classifyCode", classifyCode);//
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionBo> dataList = questionService.selectListAccept(dataMap);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 帮友热议列表查询
     */
    @GetMapping(path = "/selectListry")
    public ResponseEntity selectListry(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        Map<String, Object> dataMap = new HashMap<>();
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<QuestionBo> dataList = questionService.selectListry(dataMap);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 问题新增
     */
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody QuestionBo questionBo) {
        //新增问题信息
        questionBo = questionService.save(questionBo);
        return ResponseEntity.ok(Utils.kv("data", questionBo));
    }

    /**
     * 查询单个问题信息
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        //查询问题信息
        QuestionBo questionBo = questionService.selectQuestion(id);
        return ResponseEntity.ok(Utils.kv("data", questionBo));
    }

    /**
     * 更新问题信息
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable String id,
                                 @Valid @RequestBody QuestionBo questionBo) {
        //更新问题信息
        questionBo = questionService.update(questionBo);
        return ResponseEntity.ok(Utils.kv("data", questionBo));
    }

    /**
     * 更新问题状态
     *
     * @param status
     * @param id
     * @return
     */
    @PutMapping(path = "/updateStatus/{id}")
    public ResponseEntity updateStatus(@Valid @RequestBody String status, @PathVariable("id") String id) {
        questionService.updateStatus(id, status);
        return ResponseEntity.ok(Utils.kv("data", id));
    }

    /**
     * 删除问题信息
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        //删除问题信息
        String rtn = questionService.delete(id);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    /**
     * 查询热议标签
     */
    @GetMapping(path = "/selectTagList")
    public ResponseEntity selectTagList() {
        List<QuestionTagBo> dataList = questionService.selectTagList();
        return ResponseEntity.ok(Utils.kv("dataList", dataList));

    }


}
