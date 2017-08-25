package com.abc12366.uc.web.wx;

import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.model.weixin.bo.gzh.GzhInfo;
import com.abc12366.uc.model.weixin.bo.gzh.WxJsConfig;
import com.abc12366.uc.service.IWxGzhService;
import com.abc12366.uc.util.wx.SignUtil;
import com.abc12366.uc.util.wx.WxGzhClient;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


/**
 * 微信公众号
 *
 * @author zhushuai 2017-8-1
 */
@Controller
public class WxGzhController {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(WxGzhController.class);
    @Autowired
    private IWxGzhService iWxGzhService;

    @SuppressWarnings("rawtypes")
    @GetMapping("/wxgzh/list")
    public ResponseEntity wxgzhList(GzhInfo gzhInfo,
                                    @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                    @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{},{},{}", gzhInfo, page, size);
        List<GzhInfo> dataList = iWxGzhService.wxgzhList(gzhInfo, page, size);

        PageInfo<GzhInfo> pageInfo = new PageInfo<GzhInfo>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    //从数据库获取单个菜单信息
    @SuppressWarnings("rawtypes")
    @GetMapping("/wxgzh/{id}")
    public ResponseEntity wxgzhInfo(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        GzhInfo gzhInfo = iWxGzhService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", gzhInfo));

        LOGGER.info("{}", responseEntity);
        return responseEntity;

    }

    //新增微信公众号信息
    @SuppressWarnings("rawtypes")
    @PostMapping("/wxgzh/creat")
    public ResponseEntity wxgzhcreat(@Valid @RequestBody GzhInfo gzhInfo) {
        LOGGER.info("{}", gzhInfo);

        GzhInfo v = iWxGzhService.insert(gzhInfo);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @SuppressWarnings("rawtypes")
    @PutMapping("/wxgzh/{id}")
    public ResponseEntity wxmenudbEdit(@PathVariable("id") String id, @Valid @RequestBody GzhInfo gzhInfo) {
        LOGGER.info("{},{}", id, gzhInfo);

        gzhInfo.setId(id);
        GzhInfo v = iWxGzhService.update(gzhInfo);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @SuppressWarnings("rawtypes")
    @DeleteMapping("/wxgzh/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        iWxGzhService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }
    
    @PostMapping("/wxgzh/getwxJsConfig")
    public @ResponseBody WxJsConfig getwxJsConfig(@RequestBody WxJsConfig config){
    	LOGGER.info(JSON.toJSONString(config));
    	return SignUtil.jsign(WxGzhClient.getInstance().getAppid(), WxGzhClient.getInstanceJstiket(), config.getUrl());
    }
    
    
}
