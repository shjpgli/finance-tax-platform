package com.abc12366.uc.web.wx;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.weixin.bo.qrcode.Qrcode;
import com.abc12366.uc.service.IWxQrcodeService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 微信渠道二维码
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-29 4:02 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/qrcode", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class WxQrcodeController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WxQrcodeController.class);

    @Autowired
    IWxQrcodeService iWxQrcodeService;

    @GetMapping()
    public ResponseEntity selectList(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{},{},{}", name, page, size);

        Qrcode qrcode = new Qrcode.Builder().name(name).build();

        List<Qrcode> dataList = iWxQrcodeService.selectList(qrcode, page, size);
        PageInfo<Qrcode> pageInfo = new PageInfo<>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        Qrcode data = iWxQrcodeService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;

    }

    @PostMapping()
    public ResponseEntity insert(@Valid @RequestBody Qrcode qrcode) throws UnsupportedEncodingException {
        LOGGER.info("{}", qrcode);

        Qrcode data = iWxQrcodeService.insert(qrcode);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @Valid @RequestBody Qrcode qrcode) throws
            UnsupportedEncodingException {
        LOGGER.info("{},{}", id, qrcode);

        qrcode.setId(id);
        Qrcode data = iWxQrcodeService.update(qrcode);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        iWxQrcodeService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }
}
