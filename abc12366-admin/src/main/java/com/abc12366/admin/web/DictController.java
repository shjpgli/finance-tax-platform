package com.abc12366.admin.web;

import com.abc12366.admin.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 字典控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-04-24 2:51 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    public ResponseEntity selectList() {
        dictService.selectList();
        return ResponseEntity.ok(null);
    }
}
