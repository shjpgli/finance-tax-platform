package com.abc12366.uc.web;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.NsrBindQueryService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 查询所有绑定关系接口控制器
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 11:35
 */
@RestController
@RequestMapping(path = "/nsrbindquery", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class NsrBindQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NsrBindQueryController.class);

    @Autowired
    private NsrBindQueryService nsrBindQueryService;

    /**
     * 查询纳税人绑定关系列表
     * @param username
     * @param nsrsbh
     * @param status
     * @param page
     * @param size
     * @return
     */
    @GetMapping()
    public ResponseEntity selectList(@RequestParam(required = false) String username,
                                     @RequestParam(required = false) String nsrsbh,
                                     @RequestParam(required = false) Boolean status,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}", username, nsrsbh, page, size);
        long start = System.currentTimeMillis();
        if (username != null && username.equals("")) {
            username = null;
        }
        if (nsrsbh != null && nsrsbh.equals("")) {
            nsrsbh = null;
        }
        NsrBindQueryParamBO nsrBindQueryParamBO = new NsrBindQueryParamBO(username, nsrsbh,status);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<NsrBindQueryBO> nsrBindQueryBOList = nsrBindQueryService.selectList(nsrBindQueryParamBO);
        long end = System.currentTimeMillis();
        LOGGER.warn("消耗时间：{}" , end-start);
        return (nsrBindQueryBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) nsrBindQueryBOList, "total", ((Page)
                        nsrBindQueryBOList).getTotal()));

    }

    /**
     * 查询一条电子申报绑定
     * @param id
     * @return
     */
    @GetMapping(path = "/dzsb/{id}")
    public ResponseEntity selectDzsb(@PathVariable String id) {
        LOGGER.info("{}", id);
        UserDzsbBO userDzsbBO = nsrBindQueryService.selectDzsb(id);
        return ResponseEntity.ok(Utils.kv("data", userDzsbBO));
    }

    /**
     * 查询一条湖南地税绑定
     * @param id
     * @return
     */
    @GetMapping(path = "/hnds/{id}")
    public ResponseEntity selectHnds(@PathVariable String id) {
        LOGGER.info("{}", id);
        UserHndsBO userHndsBO = nsrBindQueryService.selectHnds(id);
        return ResponseEntity.ok(Utils.kv("data", userHndsBO));
    }

    /**
     * 查询一条湖南国税绑定
     * @param id
     * @return
     */
    @GetMapping(path = "/hngs/{id}")
    public ResponseEntity selectHngs(@PathVariable String id) {
        LOGGER.info("{}", id);
        UserHngsBO userHngsBO = nsrBindQueryService.selectHngs(id);
        return ResponseEntity.ok(Utils.kv("data", userHngsBO));
    }
}
