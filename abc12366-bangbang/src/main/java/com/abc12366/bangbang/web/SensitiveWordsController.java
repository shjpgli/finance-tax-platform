package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.SensitiveWords;
import com.abc12366.bangbang.service.SensitiveWordFilter;
import com.abc12366.bangbang.service.SensitiveWordsService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 敏感词控制类
 *
 * @author lizhongwei
 * @create 2017-06-20
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/words", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class SensitiveWordsController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SensitiveWordsController.class);

    @Autowired
    private SensitiveWordsService sensitiveWordsService;

    @Autowired
    private SensitiveWordFilter sensitiveWordFilter;

    /**
     * 敏感词列表管理
     * @return
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int pageNum,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int pageSize,
                                     @RequestParam(value = "keywords", required = false) String keywords) {
        LOGGER.info("{}:{}", pageNum, pageSize);
        /*System.out.println("敏感词的数量：" + sensitiveWordFilter.getSensitiveWordMap().size());

        String string = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
                + "然后法轮功 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
                + "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。";
        System.out.println("待检测语句字数：" + string.length());
        Set<String> set = sensitiveWordFilter.getSensitiveWord(string, 1);
        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
*/
        SensitiveWords sensitiveWords = new SensitiveWords();
        sensitiveWords.setKeywords(keywords);
        PageHelper.startPage(pageNum, pageSize, true).pageSizeZero(true).reasonable(true);
        List<SensitiveWords> sensitiveWordsList = sensitiveWordsService.selectList(sensitiveWords);
        LOGGER.info("{}", sensitiveWordsList);
        return (sensitiveWordsList == null) ?
                new ResponseEntity<>(Utils.bodyStatus(4001), HttpStatus.BAD_REQUEST) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) sensitiveWordsList, "total", ((Page) sensitiveWordsList).getTotal()));
    }


    /**
     * 查询敏感词详情
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> selectOne(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        SensitiveWords sensitiveWords = sensitiveWordsService.selectOne(id);
        LOGGER.info("{}", sensitiveWords);
        return ResponseEntity.ok(Utils.kv("data", sensitiveWords));
    }

    /**
     * 敏感词新增
     * @return
     */
    @PostMapping
    public ResponseEntity addSensitiveWords(@Valid @RequestBody SensitiveWords sensitiveWords) {
        LOGGER.info("{}", sensitiveWords);
        SensitiveWords bo = sensitiveWordsService.add(sensitiveWords);
        LOGGER.info("{}", bo);
        return ResponseEntity.ok(Utils.kv("data", bo));
    }

    /**
     * 修改敏感词
     * @param sensitiveWords
     * @param id
     * @return
     */

    @PutMapping(path = "/{id}")
    public ResponseEntity update(@Valid @RequestBody SensitiveWords sensitiveWords, @PathVariable("id") String id) {
        LOGGER.info("{}", sensitiveWords);
        sensitiveWords.setId(id);
        SensitiveWords bo = sensitiveWordsService.update(sensitiveWords);
        LOGGER.info("{}", bo);
        return new ResponseEntity<>(bo, HttpStatus.OK);
    }

    /**
     * 删除敏感词
     * @param id
     * @return
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);
        SensitiveWords sensitiveWords = new SensitiveWords();
        sensitiveWords.setId(id);
        sensitiveWordsService.delete(sensitiveWords);
        return ResponseEntity.ok(Utils.kv());
    }

}
