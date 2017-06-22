package com.abc12366.uc.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.model.bo.NsrBindQueryBO;
import com.abc12366.uc.model.bo.NsrBindQueryParamBO;
import com.abc12366.uc.model.bo.TagBO;
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
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-16
 * Time: 11:35
 */
@RestController
@RequestMapping(path = "/nsrbindquery", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class NsrBindQueryController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SubjectTagController.class);

    @Autowired
    private NsrBindQueryService nsrBindQueryService;

    @GetMapping()
    public ResponseEntity selectList(@RequestParam(required = false) String username,
                                     @RequestParam(required = false) String nsrsbh,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}:{}:{}", username, nsrsbh, page, size);
        if(username!=null&&username.equals("")){
            username=null;
        }
        if(nsrsbh!=null&&nsrsbh.equals("")){
            nsrsbh=null;
        }
        NsrBindQueryParamBO nsrBindQueryParamBO = new NsrBindQueryParamBO(username, nsrsbh);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<NsrBindQueryBO> nsrBindQueryBOList = nsrBindQueryService.selectList(nsrBindQueryParamBO);
        return (nsrBindQueryBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) nsrBindQueryBOList, "total", ((Page) nsrBindQueryBOList).getTotal()));
    }
/*    @GetMapping()
    public ResponseEntity selectDzsb(
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{}:{}", page, size);
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<NsrBindQueryBO> nsrBindQueryBOList = nsrBindQueryService.selectDzsb();
        return (nsrBindQueryBOList == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) nsrBindQueryBOList, "total", ((Page) nsrBindQueryBOList).getTotal()));
    }*/

}
