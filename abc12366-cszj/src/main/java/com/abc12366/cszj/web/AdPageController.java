package com.abc12366.cszj.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.cszj.model.bo.AdPageBO;
import com.abc12366.cszj.service.AdPageService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 广告图片管理控制类
 *
 * @author yuanluo <ljun51@outlook.com>
 * @create 2017-07-26 4:02 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class AdPageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdPageController.class);
    @Autowired
    private AdPageService adPageService;

    @GetMapping("/adpage")
    public ResponseEntity selectList(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{},{},{}", name, page, size);

        AdPageBO adPage = new AdPageBO();
        adPage.setName(name);
        List<AdPageBO> dataList = adPageService.selectList(adPage, page, size);

        PageInfo<AdPageBO> pageInfo = new PageInfo<AdPageBO>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PostMapping("/adpage")
    public ResponseEntity insert(@Valid @RequestBody AdPageBO adPage) {
        LOGGER.info("{}", adPage);

        AdPageBO v = adPageService.insert(adPage);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/adpage/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        AdPageBO adPage = adPageService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", adPage));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/adpages/{id}")
    public ResponseEntity selectOneForqt(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        AdPageBO adPage = adPageService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", adPage));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PutMapping("/adpage/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @Valid @RequestBody AdPageBO adPage) {
        LOGGER.info("{},{}", id, adPage);

        adPage.setId(id);
        AdPageBO v = adPageService.update(adPage);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @DeleteMapping("/adpage/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        adPageService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

}
