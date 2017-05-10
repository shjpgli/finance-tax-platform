package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.uc.model.bo.NsrBO;
import com.abc12366.uc.model.bo.NsrSelectBO;
import com.abc12366.uc.model.bo.NsrUpdateBO;
import com.abc12366.uc.service.NsrService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 用户绑定纳税人信息控制器
 *
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/nsr", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class NsrController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NsrController.class);

    @Autowired
    private NsrService nsrService;

    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable String id) {
        LOGGER.info("{}", id);
        NsrBO nsrBO = nsrService.selectOne(id);
        LOGGER.info("{}", nsrBO);
        return ResponseEntity.ok(nsrBO);
    }

    @GetMapping
    public ResponseEntity selectList(@RequestParam String userId, @RequestParam(required = false) String djxh, @RequestParam(required = false) String nsrsbh, @RequestParam(required = false) String shxydm) {
        LOGGER.info("{}:{}:{}:{}", userId, djxh, nsrsbh, shxydm);
        NsrSelectBO nsrSelectBO = new NsrSelectBO(userId, djxh, nsrsbh, shxydm);
        List<NsrBO> nsrBOs = nsrService.selectList(nsrSelectBO);
        LOGGER.info("{}", nsrBOs);
        return ResponseEntity.ok(nsrBOs);
    }

    @PutMapping
    public ResponseEntity update(@Valid @RequestBody NsrUpdateBO nsrUpdateBO) {
        LOGGER.info("{}", nsrUpdateBO);
        NsrBO nsrBO = nsrService.update(nsrUpdateBO);
        LOGGER.info("{}", nsrBO);
        return ResponseEntity.ok(nsrBO);
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody NsrBO nsrBO) {
        LOGGER.info("{}", nsrBO);
        NsrBO nsrBO1 = nsrService.insert(nsrBO);
        LOGGER.info("{}", nsrBO1);
        return ResponseEntity.ok(nsrBO1);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
        LOGGER.info("{}", id);
        NsrBO nsrBO = nsrService.delete(id);
        LOGGER.info("{}", nsrBO);
        return ResponseEntity.ok(nsrBO);
    }
}
