package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.bo.LetterBO;
import com.abc12366.bangbang.model.bo.LetterInsertBO;
import com.abc12366.bangbang.model.bo.LetterListBO;
import com.abc12366.bangbang.service.LetterService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-24
 * Time: 17:39
 */
@RestController
@RequestMapping(path = "/letter", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class LetterController {
    private static final Logger LOGGER = LoggerFactory.getLogger(LetterController.class);

    @Autowired
    private LetterService letterService;

    @PostMapping(path = "/{fromId}/to/{toId}")
    public ResponseEntity send(@PathVariable String fromId, @PathVariable String toId, @Valid @RequestBody
    LetterInsertBO letterInsertBO) {
        LOGGER.info("{}:{}:{}", fromId, toId, letterInsertBO);
        LetterBO letterBO = letterService.send(fromId, toId, letterInsertBO);
        return ResponseEntity.ok(Utils.kv("data", letterBO));
    }

    @GetMapping()
    public ResponseEntity selectList(HttpServletRequest request,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}", request, page, size);
        request.setAttribute("page", page);
        request.setAttribute("size", size);
        LetterListBO letterListBO = letterService.selectList(request);
        return ResponseEntity.ok(letterListBO);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity read(@PathVariable String id) {
        LOGGER.info("{}", id);
        letterService.read(id);
        return ResponseEntity.ok(Utils.kv());
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("{}", id);
        letterService.delete(id);
        return ResponseEntity.ok(Utils.kv());
    }

}
