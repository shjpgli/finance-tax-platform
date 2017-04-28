package com.abc12366.gateway.web;

import com.abc12366.common.util.Constant;
import com.abc12366.gateway.model.Blacklist;
import com.abc12366.gateway.model.bo.BlacklistBO;
import com.abc12366.gateway.service.BlacklistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-28 11:06 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/blacklist", headers = Constant.VERSION_HEAD + "=1")
public class BlacklistController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private BlacklistService blacklistService;

    @GetMapping
    public ResponseEntity selectList() {
        List<Blacklist> dataList = blacklistService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(dataList);
    }

    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info(id);
        Blacklist blacklist = blacklistService.selectOne(id);
        LOGGER.info("{}", blacklist);
        return ResponseEntity.ok(blacklist);
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody BlacklistBO bo) {
        LOGGER.info("{}", bo);
        Blacklist blacklist = blacklistService.insert(bo);
        LOGGER.info("{}", blacklist);
        return ResponseEntity.ok(blacklist);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id,
                                 @Valid @RequestBody BlacklistBO bo) {
        bo.setId(id);
        LOGGER.info("{}", bo);
        Blacklist blacklist = blacklistService.update(bo);
        LOGGER.info("{}", blacklist);
        return ResponseEntity.ok(blacklist);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info(id);
        blacklistService.delete(id);
        return ResponseEntity.ok(null);
    }
}
