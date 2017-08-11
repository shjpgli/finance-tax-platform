package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.bo.KnowledgeBaseBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseHotParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
import com.abc12366.bangbang.service.KnowledgeBaseService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @Author liuqi
 * @Date 2017/8/2 20:03
 */
@RestController
@RequestMapping(path = "/knowledgeBase", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class KnowledgeBaseController {

    @Autowired
    private KnowledgeBaseService knowledgeBaseService;

    /*
    *
    * 帮助中心 热点问题知识
    *
    */
    @GetMapping(path = "/hotList")
    public ResponseEntity hotList(@RequestParam(value = "categoryNum", defaultValue = "5") int categoryNum,
                                  @RequestParam(value = "KnowledgePageSize", defaultValue = "14") int KnowledgePageSize,
                                  @RequestParam(value = "KnowledgeType", defaultValue = "QA") String KnowledgeType,
                                  @RequestParam(value = "KnowledgeRecommend", defaultValue = "hot") String KnowledgeRecommend){
        KnowledgeBaseHotParamBO param = new KnowledgeBaseHotParamBO(categoryNum, KnowledgePageSize, KnowledgeType, KnowledgeRecommend);
        Map<String, List<KnowledgeBase>> map = knowledgeBaseService.hotMap(param);
        return ResponseEntity.ok(Utils.kv("data",map));
    }

    /*
    *  知识库 分页查询
    *  支持 分类,类别 和 关键字查询
    */
    @GetMapping(path = "/list")
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                     @RequestParam(value = "keywords", required = false) String keywords,
                                     @RequestParam(value = "type", required = false) String type) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);

        KnowledgeBaseParamBO param = new KnowledgeBaseParamBO(categoryCode, type, keywords);
        List<KnowledgeBase> list = knowledgeBaseService.selectList(param);

        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }

    /*
    *  详情页感兴趣的知识查询
    */
    @GetMapping(path = "/interestedList/{id}")
    public ResponseEntity interestedList(@PathVariable String id, @RequestParam(value = "num", defaultValue = "5") int num) {
        List<KnowledgeBase> list = knowledgeBaseService.interestedList(id, num);
        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", list));
    }

    /*
    *  详情页关联问题的知识查询
    */
    @GetMapping(path = "/relatedList/{id}")
    public ResponseEntity relatedList(@PathVariable String id, @RequestParam(value = "num", defaultValue = "5") int num) {
        List<KnowledgeBase> list = knowledgeBaseService.relatedList(id, num);
        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", list));
    }

    /**
     * 单个知识库查询 接口
     */
    @GetMapping(path = "/view/{id}")
    public ResponseEntity view(@PathVariable String id){
        KnowledgeBase knowledgeBase = knowledgeBaseService.selectOne(id);
        return ResponseEntity.ok(Utils.kv("data", knowledgeBase));
    }

    /**
     * 新增PV 接口
     */
    @PutMapping(path = "/pv/{id}")
    public ResponseEntity pv(@PathVariable String id) {
        knowledgeBaseService.addPV(id);
        return ResponseEntity.ok(Utils.kv());
    }

    /**
     * 添加知识库 接口
     */
    @PostMapping(path = "/add")
    public ResponseEntity add(@RequestBody KnowledgeBaseBO knowledgeBaseBO) {
        knowledgeBaseService.add(knowledgeBaseBO);
        return ResponseEntity.ok(Utils.kv("data", knowledgeBaseBO));
    }

    /*
    * 修改知识库 接口
    */
    @PutMapping(path = "/modify")
    public ResponseEntity modify(@RequestBody KnowledgeBaseBO knowledgeBaseBO) {
        knowledgeBaseService.modify(knowledgeBaseBO);
        return ResponseEntity.ok(Utils.kv("data", knowledgeBaseBO));
    }

    /*
    * 修改知识库状态 接口
    */
    @PutMapping(path = "/modifyStatus")
    public ResponseEntity modifyStatus(@RequestBody Map<String, Object> map) {
        knowledgeBaseService.modifyStatus(map);
        return ResponseEntity.ok(Utils.kv());
    }


    /*
    * 删除知识库 接口
    */
    @DeleteMapping(path = "/delete")
    public ResponseEntity delete(@RequestBody Map<String,List<String>> map) {
        List<String> ids = map.get("ids");
        knowledgeBaseService.delete(ids);
        return ResponseEntity.ok(Utils.kv());
    }
}
