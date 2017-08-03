package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
import com.abc12366.bangbang.service.KnowledgeBaseService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/2 20:03
 */
@RestController
@RequestMapping(path = "/knowledgeBase", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class KnowledgeBaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseController.class);

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;


    /*
    *  知识库 分页查询
    *  支持分类 和 关键字查询
    */
    @GetMapping(path = "/list")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "categoryId", required = false) String categoryId,
                                     @RequestParam(value = "keywords", required = false) String keywords) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);

        KnowledgeBaseParamBO param = new KnowledgeBaseParamBO(categoryId, keywords);
        List<KnowledgeBase> list = knowledgeBaseService.selectList(param);

        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }







    //test
    @PostMapping(path = "/add")
    public ResponseEntity add(){

        byte[] b = {new Byte("1")};
        KnowledgeBase record = new KnowledgeBase();
        record.setId(Utils.uuid());
        record.setCategoryId("1");
        record.setContent(b);
        record.setCreateUser("1");
        record.setIsOpen(Boolean.TRUE);
        record.setCreateUser("1");
        record.setRecommend("hot");
        record.setShareNum(Long.MAX_VALUE);
        record.setSource("baidu");
        record.setSubject("head");
        record.setStatus(Boolean.TRUE);
        record.setType("QA");
        record.setPv(new Long(0));
        record.setUsefulVotes(new Long(0));
        record.setUselessVotes(new Long(0));
        record.setShareNum(new Long(0));
        knowledgeBaseService.add(record);
        return ResponseEntity.ok(Utils.kv());
    }



}
