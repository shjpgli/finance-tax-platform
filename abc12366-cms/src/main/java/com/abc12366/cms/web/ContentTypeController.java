package com.abc12366.cms.web;

import com.abc12366.cms.model.ContentType;
import com.abc12366.cms.service.ContentTypeService;
import com.abc12366.gateway.util.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 内容类型管理模块
 *
 * @author xieyanmao
 * @create 2017-05-03
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/contentType", headers = Constant.VERSION_HEAD + "=1")
public class ContentTypeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContentTypeController.class);

    @Autowired
    private ContentTypeService contentTypeService;

    @GetMapping
    public ResponseEntity selectList() {
        //查询内容类型列表
        List<ContentType> dataList = contentTypeService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(dataList);
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ContentType contentType) {
        LOGGER.info("{}", contentType);
        //新增内容类型
        String rtn = contentTypeService.save(contentType);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }

    @GetMapping(path = "/{typeId}")
    public ResponseEntity selectOne(@PathVariable String typeId) {
        LOGGER.info("{}", typeId);
        //查询内容类型
        ContentType contentType = contentTypeService.selectContentType(typeId);
        LOGGER.info("{}", contentType);
        return ResponseEntity.ok(contentType);
    }

    @PutMapping(path = "/{typeId}")
    public ResponseEntity update(@PathVariable String typeId,
                                 @Valid @RequestBody ContentType contentType) {

        LOGGER.info("{}", contentType);
        //更新内容类型
        String rtn = contentTypeService.update(contentType);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }

    @DeleteMapping(path = "/{typeId}")
    public ResponseEntity delete(@PathVariable String typeId) {
        LOGGER.info("{}", typeId);
        //删除内容类型
        String rtn = contentTypeService.delete(typeId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }


}
