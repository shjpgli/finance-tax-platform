package com.abc12366.cszj.web;

import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.abc12366.cszj.model.bo.NoticeBO;
import com.abc12366.cszj.service.NoticeService;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 通知公告管理控制类
 *
 * @author yuanluo <ljun51@outlook.com>
 * @create 2017-07-26 4:02 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/notice", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class NoticeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(NoticeController.class);
    @Autowired
    private NoticeService noticeService;

    @GetMapping
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

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody NoticeBO notice) {
        LOGGER.info("{}", notice);

        NoticeBO v = noticeService.insert(notice);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        NoticeBO notice = noticeService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", notice));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @Valid @RequestBody NoticeBO notice) {
        LOGGER.info("{},{}", id, notice);

        notice.setId(id);
        NoticeBO v = noticeService.update(notice);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        noticeService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

}
