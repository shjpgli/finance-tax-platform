package com.abc12366.message.web;

import com.abc12366.gateway.model.BodyStatus;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.abc12366.message.model.BusinessMessage;
import com.abc12366.message.service.BusinessMsgService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户消息服务
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-07-27 11:20 AM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/business", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class BusinessMsgController {
    private static final Logger LOGGER = LoggerFactory.getLogger(BusinessMsgController.class);

    @Autowired
    private BusinessMsgService businessMsgService;

    /**
     * 获取当前用户消息列表
     *
     * @param page 当前页
     * @param size 每页大小
     * @return ResponseEntity
     */
    @GetMapping()
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     HttpServletRequest request) {
        LOGGER.info("{},{}", page, size);

        // request USER_ID为空
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.bodyStatus(4193));
        String userId = (String) request.getAttribute(Constant.USER_ID);

        if (!StringUtils.isEmpty(userId)) {
            BusinessMessage bm = new BusinessMessage.Builder().userId(userId).build();
            List<BusinessMessage> dataList = businessMsgService.selectList(bm, page, size);

            PageInfo<BusinessMessage> pageInfo = new PageInfo<>(dataList);
            responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(), "total", pageInfo.getTotal()));
        }

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 发送用户消息
     *
     * @param data UserMessage
     * @return ResponseEntity
     */
    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody BusinessMessage data) {
        LOGGER.info("{}", data);

        data = businessMsgService.insert(data);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 用户查看消息，如果消息状态为'未读'，则将消息状态置为'已读'
     *
     * @param id 消息ID
     * @return ResponseEntity
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        BusinessMessage data = businessMsgService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 直接将'未读'消息置为'已读'，不需要进入消息
     *
     * @param id
     * @return
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity update(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        BusinessMessage data = businessMsgService.update(new BusinessMessage.Builder().id(id).status("2").build());
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", data));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 用户删除消息，物理删除
     *
     * @param id 消息ID
     * @param request 获取USER_ID
     * @return ResponseEntity
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity delete(@PathVariable("id") String id, HttpServletRequest request) {
        LOGGER.info("{}", id);

        // request USER_ID为空
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.bodyStatus(4193));
        String userId = (String) request.getAttribute(Constant.USER_ID);

        if (!StringUtils.isEmpty(userId)) {
            BusinessMessage data = new BusinessMessage.Builder().id(id).userId(userId).build();
            BodyStatus body = businessMsgService.delete(data);
            responseEntity = ResponseEntity.ok(body);
        }

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }
}
