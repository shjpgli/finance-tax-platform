package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.KnowledgeCategory;
import com.abc12366.bangbang.service.KnowledgeCategoryService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author liuqi
 * @Date 2017/8/2 20:58
 * 知识库 分类 接口
 */

@RestController
@RequestMapping(path = "/knowledgeCategory", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class KnowledgeCategoryController {

    private static final Logger LOGGER = LoggerFactory.getLogger(KnowledgeBaseController.class);

    @Autowired
    private KnowledgeCategoryService knowledgeCategoryService;

    /* 查询所有分类 */
    @GetMapping("/listAll")
    public ResponseEntity listAll(){
        List<KnowledgeCategory> all = knowledgeCategoryService.listAll();
        return ResponseEntity.ok(Utils.kv("dataList", all));
    }





}
