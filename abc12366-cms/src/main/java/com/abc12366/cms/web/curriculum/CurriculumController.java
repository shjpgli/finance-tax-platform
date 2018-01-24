package com.abc12366.cms.web.curriculum;

import com.abc12366.cms.model.bo.IdsBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumListBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumSituationBo;
import com.abc12366.cms.service.CurriculumService;
import com.abc12366.gateway.util.Constant;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 学堂课程管理模块
 *
 * @author xieyanmao
 * @create 2017-08-10
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/curriculum", headers = Constant.VERSION_HEAD + "=1")
public class CurriculumController {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurriculumController.class);

    @Autowired
    private CurriculumService curriculumService;
    
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 课程列表查询
     */
    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size,
                                     @RequestParam(value = "title", required = false) String title,
                                     @RequestParam(value = "status", required = false) String status,
                                     @RequestParam(value = "label", required = false) String label,
                                     @RequestParam(value = "classify", required = false) String classify) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("status", status);//状态
        dataMap.put("title", title);//标题
        dataMap.put("label", label);//标签
        dataMap.put("classify", classify);//课程分类
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<CurriculumListBo> dataList = curriculumService.selectList(dataMap);
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }

    /**
     * 推荐课程查询
     */
    @GetMapping(path = "/selectRecommend")
    public ResponseEntity selectRecommend(@RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        Map<String, Object> dataMap = new HashMap<>();
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        List<CurriculumListBo> dataList = curriculumService.selectRecommend();
        return ResponseEntity.ok(Utils.kv("dataList", (Page) dataList, "total", ((Page) dataList).getTotal()));

    }
    
   /* *//**
     * 推荐课程查询
     *//*
    @GetMapping(path = "/selectRecommendForqt")
    public ResponseEntity selectRecommendForqt() {
    	List<CurriculumListBo> list = null;
        if(redisTemplate.hasKey("Bangb_RecommendForqt")){
        	list=JSONArray.parseArray(redisTemplate.opsForValue().get("Bangb_RecommendForqt"),CurriculumListBo.class);
    		LOGGER.info("从Redis获取数据:"+JSONArray.toJSONString(list));
    		return ResponseEntity.ok(Utils.kv("dataList", list, "total", list.size()));
        }else{
        	PageHelper.startPage(1, 10, true).pageSizeZero(true).reasonable(true);
        	list= curriculumService.selectRecommend();
        	redisTemplate.opsForValue().set("Bangb_RecommendForqt",JSONArray.toJSONString(list),RedisConstant.DAY_1, TimeUnit.DAYS);
        	return ResponseEntity.ok(Utils.kv("dataList", (Page) list, "total", ((Page) list).getTotal()));
        }
    }*/

    /**
     * 查询单个课程授课信息
     */
    @GetMapping(path = "/selectSituation/{curriculumId}")
    public ResponseEntity selectSituation(@PathVariable String curriculumId) {
        //查询课程信息
        CurriculumSituationBo curriculumSituationBo = curriculumService.selectSituation(curriculumId);
        return ResponseEntity.ok(Utils.kv("data", curriculumSituationBo));
    }

    /**
     * 课程新增
     */
    @PostMapping
    public ResponseEntity save(@Valid @RequestBody CurriculumBo curriculumBo) {
        //新增课程信息
        curriculumBo = curriculumService.save(curriculumBo);
        redisTemplate.delete("Bangb_RecommendForqt");
        return ResponseEntity.ok(Utils.kv("data", curriculumBo));
    }

    /**
     * 查询单个课程信息
     */
    @GetMapping(path = "/{curriculumId}")
    public ResponseEntity selectOne(@PathVariable String curriculumId) {
        //查询课程信息
        CurriculumBo curriculumBo = curriculumService.selectCurriculum(curriculumId);
        return ResponseEntity.ok(Utils.kv("data", curriculumBo));
    }

    /**
     * 更新课程信息
     */
    @PutMapping(path = "/{curriculumId}")
    public ResponseEntity update(@PathVariable String curriculumId,
                                 @Valid @RequestBody CurriculumBo curriculumBo) {
        //更新课程信息
        curriculumBo = curriculumService.update(curriculumBo);
        redisTemplate.delete("Bangb_RecommendForqt");
        return ResponseEntity.ok(Utils.kv("data", curriculumBo));
    }

    /**
     * 更新课程状态
     *
     * @param status
     * @param curriculumId
     * @return
     */
    @PutMapping(path = "/updateStatus/{curriculumId}")
    public ResponseEntity updateStatus(@Valid @RequestBody String status, @PathVariable("curriculumId") String curriculumId) {
        curriculumService.updateStatus(curriculumId, status);
        redisTemplate.delete("Bangb_RecommendForqt");
        return ResponseEntity.ok(Utils.kv("data", curriculumId));
    }

    /**
     * 删除课程信息
     */
    @DeleteMapping(path = "/{curriculumId}")
    public ResponseEntity delete(@PathVariable String curriculumId) {
        //删除课程信息
        String rtn = curriculumService.delete(curriculumId);
        redisTemplate.delete("Bangb_RecommendForqt");
        return ResponseEntity.ok(Utils.kv("data", rtn));
    }

    /**
     * 批量删除课程信息
     */
    @PostMapping(path = "/deletelist")
    public ResponseEntity deleteList(@RequestBody IdsBo idsBo) {
        LOGGER.info("{}", idsBo);
        //批量删除课程信息
        String rtn = curriculumService.deleteList(idsBo.getIds());
        LOGGER.info("{}", rtn);
        redisTemplate.delete("Bangb_RecommendForqt");
        return ResponseEntity.ok(Utils.kv("data", idsBo));
    }

}
