package com.abc12366.cms.web;

import com.abc12366.cms.model.ModelItem;
import com.abc12366.cms.model.bo.ContentSaveBo;
import com.abc12366.cms.model.bo.ContentListBo;
import com.abc12366.cms.service.ContentService;
import com.abc12366.common.util.Constant;
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
 * 内容管理模块
 *
 * @author xieyanmao
 * @create 2017-04-27
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/content",headers = Constant.VERSION_HEAD + "=1")
public class ContentController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentController.class);

    @Autowired
    private ContentService contentService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "minNum", required = false) String minNum,
                                     @RequestParam(value = "maxNum", required = false) String maxNum,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "topLevel", required = false) String topLevel,
                                     @RequestParam(value = "typeId", required = false) String typeId,
                                     @RequestParam(value = "author", required = false) String author,
                                     @RequestParam(value = "status", required = false) String status,
                                     @RequestParam(value = "recommendLevel", required = false) String recommendLevel) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("title", title);
        dataMap.put("topLevel", topLevel);
        dataMap.put("typeId", typeId);
        dataMap.put("author", author);
        dataMap.put("status", status);
        dataMap.put("recommendLevel", recommendLevel);
        dataMap.put("minNum", minNum);
        dataMap.put("maxNum", maxNum);
        List<ContentListBo> contents = contentService.selectList(dataMap);
        LOGGER.info("{}", contents);
        return ResponseEntity.ok(contents);
    }

    @GetMapping(path = "/init")
    public ResponseEntity init(@RequestParam(value = "modelId", required = false) String modelId) {
        List<ModelItem> contents = contentService.selectModeList(modelId);
        LOGGER.info("{}", contents);
        return ResponseEntity.ok(contents);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ContentSaveBo contentSaveDto) {
        LOGGER.info("{}", contentSaveDto);
        String rtn = contentService.save(contentSaveDto);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }

    @GetMapping(path = "/{contentId}")
    public ResponseEntity selectOne(@PathVariable String contentId) {
        LOGGER.info("{}", contentId);
        ContentSaveBo contentSaveDto = contentService.selectContent(contentId);
        LOGGER.info("{}", contentSaveDto);
        return ResponseEntity.ok(contentSaveDto);
    }

    @PutMapping(path = "/{contentId}")
    public ResponseEntity update(@PathVariable String contentId,
                                 @Valid @RequestBody ContentSaveBo contentSaveDto) {

        LOGGER.info("{}", contentSaveDto);
        String rtn = contentService.update(contentSaveDto);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }

    @DeleteMapping(path = "/{contentId}")
    public ResponseEntity delete(@PathVariable Long contentId) {
        LOGGER.info("{}", contentId);
        String rtn = contentService.delete(contentId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }




}
