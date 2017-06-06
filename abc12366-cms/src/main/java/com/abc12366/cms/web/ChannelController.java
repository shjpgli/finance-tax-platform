package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.ChannelBo;
import com.abc12366.cms.model.bo.ChannelSaveBo;
import com.abc12366.cms.model.bo.ModelItemBo;
import com.abc12366.cms.service.ChannelService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 栏目管理模块
 *
 * @author xieyanmao
 * @create 2017-05-09
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/channel",headers = Constant.VERSION_HEAD + "=1")
public class ChannelController {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelController.class);

    @Autowired
    private ChannelService channelService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        //查询栏目列表
//        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<ChannelBo> dataList = channelService.selectList();
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", dataList));
//        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));
    }

    @GetMapping(path = "/init")
    public ResponseEntity init(@RequestParam(value = "modelId", required = false) String modelId) {
        //查询模型项
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("modelId",modelId);
        dataMap.put("isChannel",1);
        List<ModelItemBo> dataList = channelService.selectModeList(dataMap);
        LOGGER.info("{}", dataList);
        return ResponseEntity.ok(Utils.kv("dataList", dataList));
    }

    @PostMapping
    public ResponseEntity save(@RequestBody ChannelSaveBo channelSaveBo) {
        LOGGER.info("{}", channelSaveBo);
        //新增栏目信息
        channelSaveBo = channelService.save(channelSaveBo);
        LOGGER.info("{}", channelSaveBo);
        return ResponseEntity.ok(channelSaveBo);
    }

    @GetMapping(path = "/{channelId}")
    public ResponseEntity selectOne(@PathVariable String channelId) {
        LOGGER.info("{}", channelId);
        //根据栏目ID查询内容信息
        ChannelSaveBo channelSaveBo = channelService.selectChannel(channelId);
        LOGGER.info("{}", channelSaveBo);
        return ResponseEntity.ok(channelSaveBo);
    }

    @PutMapping(path = "/{channelId}")
    public ResponseEntity update(@PathVariable String channelId,
                                 @Valid @RequestBody ChannelSaveBo channelSaveBo) {

        LOGGER.info("{}", channelSaveBo);
        //更新栏目信息
        channelSaveBo = channelService.update(channelSaveBo);
        LOGGER.info("{}", channelSaveBo);
        return ResponseEntity.ok(channelSaveBo);
    }

    @DeleteMapping(path = "/{channelId}")
    public ResponseEntity delete(@PathVariable String channelId) {
        LOGGER.info("{}", channelId);
        //删除栏目信息
        String rtn = channelService.delete(channelId);
        LOGGER.info("{}", rtn);
        return ResponseEntity.ok(rtn);
    }




}
