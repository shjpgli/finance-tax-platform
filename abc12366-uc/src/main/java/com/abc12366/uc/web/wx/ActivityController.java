package com.abc12366.uc.web.wx;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.weixin.WxActivity;
import com.abc12366.uc.service.IActivityService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 微信红包活动管理
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 10:30 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/wx/activity", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class ActivityController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityController.class);

    @Autowired
    private IActivityService iActivityService;

    @GetMapping()
    public ResponseEntity selectList(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{},{},{}", name, page, size);
        WxActivity activity = new WxActivity.Builder().name(name).build();
        List<WxActivity> dataList = iActivityService.selectList(activity, page, size);

        PageInfo<WxActivity> pageInfo = new PageInfo<>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {

        LOGGER.info("{}", id);
        WxActivity data = iActivityService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody WxActivity activity) {
        LOGGER.info("{}", activity);
        WxActivity data = iActivityService.insert(activity);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @Valid @RequestBody WxActivity activity) {

        activity.setId(id);
        LOGGER.info("{}", activity);
        WxActivity data = iActivityService.update(activity);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {

        LOGGER.info("{}", id);
        iActivityService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }
}
