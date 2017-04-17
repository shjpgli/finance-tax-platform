package com.abc12366.gateway.web;

import com.abc12366.gateway.model.Api;
import com.abc12366.gateway.model.bo.ApiBO;
import com.abc12366.gateway.service.ApiService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-27 3:58 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/api", headers = "version=1")
public class ApiController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private ApiService apiService;

    @GetMapping
    public ResponseEntity selectList() {
        List<Api> apiList = apiService.selectList();
        LOGGER.info("{}", apiList);
        return ResponseEntity.ok(apiList);
    }

    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        Api api = apiService.selectOne(id);
        LOGGER.info("{}", api);
        return ResponseEntity.ok(api);
    }

    @PostMapping
    public ResponseEntity insert(@Valid ApiBO apiBO) {
        LOGGER.info("{}", apiBO);
        Api api = apiService.insert(apiBO);
        LOGGER.info("{}", api);
        return ResponseEntity.ok(api);
    }

    @PutMapping
    public ResponseEntity edit(@Valid ApiBO apiBO) {
        LOGGER.info("{}", apiBO);
        Api api = apiService.update(apiBO);
        LOGGER.info("{}", api);
        return ResponseEntity.ok(api);
    }

    @DeleteMapping
    public ResponseEntity delete(ApiBO apiBO) {
        LOGGER.info("{}", apiBO);
        int rows = apiService.delete(apiBO);
        LOGGER.info("{}", rows);
        return ResponseEntity.ok(rows);
    }
}
