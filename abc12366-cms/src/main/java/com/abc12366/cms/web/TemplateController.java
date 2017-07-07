package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.TemplateBo;
import com.abc12366.cms.service.TemplateService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
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
 * 定时任务模块
 *
 * @author xieyanmao
 * @create 2017-06-27
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/template",headers = Constant.VERSION_HEAD + "=1")
public class TemplateController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TemplateController.class);

    @Autowired
    private TemplateService templateService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "templateProperty", required = false) String templateProperty,
                                     @RequestParam(value = "siteId", required = false) String siteId,
                                     @RequestParam(value = "parentPath", required = false) String parentPath,
                                     @RequestParam(value = "state", required = false) String state) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("templateProperty",templateProperty);
        dataMap.put("siteId",siteId);
        dataMap.put("parentPath",parentPath);
        dataMap.put("state",state);
        List<TemplateBo> dataList = templateService.selectList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", dataList));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody TemplateBo templateBo) {
        LOGGER.info("{}", templateBo);
        //新增定时任务信息
        templateBo = templateService.save(templateBo);
        LOGGER.info("{}", templateBo);
        return ResponseEntity.ok(Utils.kv("data", templateBo));
    }

    @GetMapping(path = "/{templateId}")
    public ResponseEntity selectOne(@PathVariable String templateId) {
        LOGGER.info("{}", templateId);
        //查询定时任务信息
        TemplateBo templateBo = templateService.selectTemplate(templateId);
        LOGGER.info("{}", templateBo);
        return ResponseEntity.ok(Utils.kv("data", templateBo));
    }

    @PutMapping(path = "/{templateId}")
    public ResponseEntity update(@PathVariable String templateId,
                                 @Valid @RequestBody TemplateBo templateBo) {

        LOGGER.info("{}", templateBo);
        //更新定时任务信息
        templateBo = templateService.update(templateBo);
        LOGGER.info("{}", templateBo);
        return ResponseEntity.ok(Utils.kv("data", templateBo));
    }

    @DeleteMapping(path = "/{templateId}")
    public ResponseEntity delete(@PathVariable String templateId) {
        LOGGER.info("{}", templateId);
        //删除定时任务信息
        String rtn = templateService.delete(templateId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

}
