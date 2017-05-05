package com.abc12366.gateway.web;

import com.abc12366.common.util.Constant;
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
 * @create 2017-04-27 10:21 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(name = "/api", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
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
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info(id);
        Api api = apiService.selectOne(id);
        LOGGER.info("{}", api);
        return ResponseEntity.ok(api);
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody ApiBO apiBO) {
        LOGGER.info("{}", apiBO);
        Api api = apiService.insert(apiBO);
        LOGGER.info("{}", api);
        return ResponseEntity.ok(api);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id,
                                 @Valid @RequestBody ApiBO apiBO) {
        apiBO.setId(id);
        LOGGER.info("{}", apiBO);
        Api api = apiService.update(apiBO);
        LOGGER.info("{}", api);
        return ResponseEntity.ok(api);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id")String id) {
        LOGGER.info("{}", id);
        apiService.delete(id);
        return ResponseEntity.ok(null);
    }
}
