package com.abc12366.cms.web;

import com.abc12366.cms.model.bo.NoticeBO;
import com.abc12366.cms.service.NoticeService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * ֪ͨ������������
 *
 * @author yuanluo <ljun51@outlook.com>
 * @create 2017-07-26 4:02 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class NoticeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);
    @Autowired
    private NoticeService noticeService;

    @GetMapping("/notice")
    public ResponseEntity selectList(@RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{},{},{}", title, page, size);

        NoticeBO notice = new NoticeBO();
        notice.setTitle(title);
        List<NoticeBO> dataList = noticeService.selectList(notice, page, size);

        PageInfo<NoticeBO> pageInfo = new PageInfo<NoticeBO>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/notices")
    public ResponseEntity selectListForqt(@RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{},{},{}", title, page, size);

        NoticeBO notice = new NoticeBO();
        notice.setTitle(title);
        List<NoticeBO> dataList = noticeService.selectListForqt(notice, page, size);

        PageInfo<NoticeBO> pageInfo = new PageInfo<NoticeBO>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PostMapping("/notice")
    public ResponseEntity insert(@Valid @RequestBody NoticeBO notice) {
        LOGGER.info("{}", notice);

        NoticeBO v = noticeService.insert(notice);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/notice/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        NoticeBO notice = noticeService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", notice));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/notices/{id}")
    public ResponseEntity selectOneForqt(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        NoticeBO notice = noticeService.selectOneForqt(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", notice));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PutMapping("/notice/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @Valid @RequestBody NoticeBO notice) {
        LOGGER.info("{},{}", id, notice);

        notice.setId(id);
        NoticeBO v = noticeService.update(notice);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @DeleteMapping("/notice/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        noticeService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

}
