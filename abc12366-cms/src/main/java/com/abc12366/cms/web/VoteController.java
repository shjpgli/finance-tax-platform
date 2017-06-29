package com.abc12366.cms.web;

import com.abc12366.cms.model.Vote;
import com.abc12366.cms.model.VoteHistory;
import com.abc12366.cms.model.VoteResult;
import com.abc12366.cms.model.bo.VoteStatAreaBO;
import com.abc12366.cms.model.bo.VoteStatBrowserBO;
import com.abc12366.cms.service.VoteService;
import com.abc12366.common.util.Constant;
import com.abc12366.common.util.Utils;
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
import java.util.Map;

/**
 * 投票管理控制器
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-07 4:02 PM
 * @since 1.0.0
 */
@RestController
@RequestMapping(path = "/vote", headers = Constant.VERSION_HEAD + "=" + Constant.VERSION_1)
public class VoteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(VoteController.class);
    @Autowired
    private VoteService voteService;

    @GetMapping
    public ResponseEntity selectList(@RequestParam(value = "name", required = false) String name,
                                     @RequestParam(value = "page", defaultValue = Constant.pageNum) int page,
                                     @RequestParam(value = "size", defaultValue = Constant.pageSize) int size) {
        LOGGER.info("{},{},{}", name, page, size);

        Vote vote = new Vote.Builder().name(name).build();
        List<Vote> dataList = voteService.selectList(vote, page, size);

        PageInfo<Vote> pageInfo = new PageInfo<>(dataList);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", pageInfo.getList(),
                "total", pageInfo.getTotal()));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PostMapping
    public ResponseEntity insert(@Valid @RequestBody Vote vote) {
        LOGGER.info("{}", vote);

        Vote v = voteService.insert(vote);

        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @GetMapping("/{id}")
    public ResponseEntity selectOne(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        Vote vote = voteService.selectOne(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", vote));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable("id") String id, @Valid @RequestBody Vote vote) {
        LOGGER.info("{},{}", id, vote);

        vote.setId(id);
        Vote v = voteService.update(vote);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", v));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") String id) {
        LOGGER.info("{}", id);

        voteService.delete(id);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv());

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 单个题目投票
     *
     * @param voteId 投票ID
     * @param subjectId 题目ID
     * @param itemId 选项ID
     * @param request HttpServletRequest
     * @return VoteResult
     */
    @PostMapping("/result/{voteId}/{subjectId}/{itemId}")
    public ResponseEntity vote(@PathVariable("voteId") String voteId,
                               @PathVariable("subjectId") String subjectId,
                               @PathVariable("itemId") String itemId,
                               HttpServletRequest request) {

        LOGGER.info("{},{},{}", voteId, subjectId, itemId);

        VoteResult result = new VoteResult();
        result.setVoteId(voteId);
        result.setSubjectId(subjectId);
        result.setItemId(itemId);

        VoteResult voteResult = voteService.vote(result, request);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", voteResult));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 多个题目投票
     *
     * @param voteId 投票ID
     * @param resultList 投票结果列表
     * @param request HttpServletRequest
     * @return VoteResult集合
     */
    @PostMapping("/result/{voteId}")
    public ResponseEntity vote(@PathVariable("voteId") String voteId,
                               @Valid @RequestBody List<VoteResult> resultList,
                               HttpServletRequest request) {
        LOGGER.info("{},{}", voteId, resultList);
        List<VoteResult> dataList = voteService.vote(voteId, resultList, request);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("dataList", dataList));

        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 投票浏览记录
     *
     * @param voteId 投票ID
     * @param request HttpServletRequest
     * @return VoteHistory
     */
    @PostMapping("/views/{voteId}")
    public ResponseEntity history(@PathVariable("voteId") String voteId, HttpServletRequest request) {
        LOGGER.info("{}", voteId);
        VoteHistory vh = voteService.insertHistory(voteId, request);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", vh));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 统计访问量
     *
     * @param voteId 投票ID
     * @return Map
     */
    @GetMapping("/stat/views/{voteId}")
    public ResponseEntity statViews(@PathVariable("voteId") String voteId) {
        LOGGER.info("{}", voteId);
        Map<String, Integer> map = voteService.statViews(voteId);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", map));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 统计用户代理
     *
     * @param voteId 投票ID
     * @return VoteStatBrowserBO
     */
    @GetMapping("/stat/browser/{voteId}")
    public ResponseEntity statBrowser(@PathVariable("voteId") String voteId) {
        LOGGER.info("{}", voteId);
        List<VoteStatBrowserBO> list = voteService.statBrowser(voteId);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", list));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }

    /**
     * 统计IP归属区域
     *
     * @param voteId 投票ID
     * @return VoteStatAreaBO
     */
    @GetMapping("/stat/area/{voteId}")
    public ResponseEntity statIpArea(@PathVariable("voteId") String voteId) {
        LOGGER.info("{}", voteId);
        List<VoteStatAreaBO> list = voteService.statIpArea(voteId);
        ResponseEntity responseEntity = ResponseEntity.ok(Utils.kv("data", list));
        LOGGER.info("{}", responseEntity);
        return responseEntity;
    }
}
