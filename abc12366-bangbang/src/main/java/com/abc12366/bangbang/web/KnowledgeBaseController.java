package com.abc12366.bangbang.web;

import com.abc12366.bangbang.model.KnowledgeBase;
import com.abc12366.bangbang.model.bo.KnowledgeBaseBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseHotParamBO;
import com.abc12366.bangbang.model.bo.KnowledgeBaseParamBO;
import com.abc12366.bangbang.service.KnowledgeBaseService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.RedisConstant;
import com.abc12366.gateway.util.Utils;
import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

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
    *
    * 财税网首页 最新问题、知识
    *
    */
    @GetMapping(path = "/nearestList")
    public ResponseEntity nearestList(
            @RequestParam(value = "KnowledgePageSize", defaultValue = "14") int KnowledgePageSize,
            @RequestParam(value = "KnowledgeType", defaultValue = "QA") String KnowledgeType){
        KnowledgeBaseHotParamBO param = new KnowledgeBaseHotParamBO(KnowledgePageSize, KnowledgeType, null);
        List<KnowledgeBase> list = knowledgeBaseService.selectNearestList(param);
        return ResponseEntity.ok(Utils.kv("dataList",list));
    }

    /*
    *
    * 首页 热点问题、热点知识，不分小类
    *
    */
    @GetMapping(path = "/hotUnClassifyList")
    public ResponseEntity hotUnClassifyList(
                                  @RequestParam(value = "KnowledgePageSize", defaultValue = "14") int KnowledgePageSize,
                                  @RequestParam(value = "KnowledgeType", defaultValue = "QA") String KnowledgeType,
                                  @RequestParam(value = "KnowledgeRecommend", defaultValue = "hot") String KnowledgeRecommend){
    	List<KnowledgeBase> list = null;
    	if(redisTemplate.hasKey("Bangb_HotUnClassifyList")){
    		list=JSONArray.parseArray(redisTemplate.opsForValue().get("Bangb_HotUnClassifyList"),KnowledgeBase.class);
    		LOGGER.info("从Redis获取数据:"+JSONArray.toJSONString(list));
    	}else{
    		KnowledgeBaseHotParamBO param = new KnowledgeBaseHotParamBO(KnowledgePageSize, KnowledgeType, KnowledgeRecommend);
            list = knowledgeBaseService.hotUnClassifyMap(param);
            redisTemplate.opsForValue().set("Bangb_HotUnClassifyList",JSONArray.toJSONString(list),RedisConstant.USER_INFO_TIME_ODFAY, TimeUnit.DAYS);
    	}   
    	
        return ResponseEntity.ok(Utils.kv("dataList",list));
    }
    
    
    /*
    *
    * 首页 热点问题、热点知识，不分小类
    *
    */
    @GetMapping(path = "/wx/hotUnClassifyList")
    public ResponseEntity wxhotUnClassifyList(
    		                      @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                  @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                  @RequestParam(value = "KnowledgeType", defaultValue = "QA") String KnowledgeType,
                                  @RequestParam(value = "KnowledgeRecommend", defaultValue = "hot") String KnowledgeRecommend){
    	PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        KnowledgeBaseHotParamBO param = new KnowledgeBaseHotParamBO(0, KnowledgeType, KnowledgeRecommend);
        List<KnowledgeBase> list = knowledgeBaseService.wxhotUnClassifyMap(param);
        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }
    

    /*
    *  知识库 分页查询
    *  支持 分类,类别 和 关键字查询
    */
    @GetMapping(path = "/uc/list")
    public ResponseEntity ucList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "categoryCode", required = false) String categoryCode,
                                     @RequestParam(value = "keywords", required = false) String keywords,
                                     @RequestParam(value = "type", required = false) String type) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        if(!StringUtils.isEmpty(keywords)){
            keywords = keywords.toUpperCase();
        }
        KnowledgeBaseParamBO param = new KnowledgeBaseParamBO(categoryCode, type, keywords, true, true,"");
        List<KnowledgeBase> list = knowledgeBaseService.selectUCList(param);

        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
    }


    /*
    *  知识库 根据标签查询
    *  支持 分类,类别 和 标签查询
    */
    @GetMapping(path = "/uc/listbytag")
    public ResponseEntity ucListBytag(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                 @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                 @RequestParam(value = "keywords", required = false) String keywords,
                                 @RequestParam(value = "type", required = false) String type) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        if(!StringUtils.isEmpty(keywords)){
            keywords = keywords.toUpperCase();
        }
        KnowledgeBaseParamBO param = new KnowledgeBaseParamBO("", type, keywords, true, true,"");
        List<KnowledgeBase> list = knowledgeBaseService.selectUCListByTag(param);

        return (list == null) ?
                ResponseEntity.ok(Utils.kv()) :
                ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
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
                                     @RequestParam(value = "type", required = false) String type,
                                     @RequestParam(value = "recommend", required = false) String recommend,
                                     @RequestParam(value = "isOpen", required = false) Boolean isOpen,
                                     @RequestParam(value = "order", required = false) String order,
                                     @RequestParam(value = "status", required = false) Boolean status) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        if(!StringUtils.isEmpty(keywords)){
            keywords = keywords.toUpperCase();
        }
        KnowledgeBaseParamBO param = new KnowledgeBaseParamBO(categoryCode, type, keywords, isOpen, status,order);
        param.setRecommend(recommend);
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


    /*
    *
    * 知识库采集来源列表
    *
    */
    @GetMapping(path = "/sourceList")
    public ResponseEntity sourceList(){
        return ResponseEntity.ok(Utils.kv("data", knowledgeBaseService.selectSourceList()));
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
        redisTemplate.delete("Bangb_HotUnClassifyList");
        return ResponseEntity.ok(Utils.kv("data", knowledgeBaseBO));
    }

    /*
    * 修改知识库 接口
    */
    @PutMapping(path = "/modify")
    public ResponseEntity modify(@RequestBody KnowledgeBaseBO knowledgeBaseBO) {
        knowledgeBaseService.modify(knowledgeBaseBO);
        redisTemplate.delete("Bangb_HotUnClassifyList");
        return ResponseEntity.ok(Utils.kv("data", knowledgeBaseBO));
    }

    /*
    * 修改知识库状态 接口
    */
    @PutMapping(path = "/modifyStatus")
    public ResponseEntity modifyStatus(@RequestBody Map<String, Object> map) {
        knowledgeBaseService.modifyStatus(map);
        redisTemplate.delete("Bangb_HotUnClassifyList");
        return ResponseEntity.ok(Utils.kv());
    }


    /*
    * 删除知识库 接口
    */
    @DeleteMapping(path = "/delete")
    public ResponseEntity delete(@RequestBody Map<String,List<String>> map) {
        List<String> ids = map.get("ids");
        knowledgeBaseService.delete(ids);
        redisTemplate.delete("Bangb_HotUnClassifyList");
        return ResponseEntity.ok(Utils.kv());
    }
}
