package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.CheatsMapper;
import com.abc12366.bangbang.mapper.db1.CheatsTagMapper;
import com.abc12366.bangbang.mapper.db2.*;
import com.abc12366.bangbang.model.question.Cheats;
import com.abc12366.bangbang.model.question.CheatsTag;
import com.abc12366.bangbang.model.question.bo.CheatsBo;
import com.abc12366.bangbang.model.question.bo.CheatsTagBo;
import com.abc12366.bangbang.service.CheatsService;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xieyanmao on 2017/9/14.
 */
@Service
public class CheatsServiceImpl implements CheatsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheatsServiceImpl.class);

    @Autowired
    private CheatsMapper cheatsMapper;

    @Autowired
    private CheatsRoMapper cheatsRoMapper;

    @Autowired
    private QuestionRoMapper questionRoMapper;

    @Autowired
    private CheatsTagMapper tagMapper;

    @Autowired
    private CheatsTagRoMapper tagRoMapper;

    @Autowired
    private SensitiveWordsRoMapper sensitiveWordsRoMapper;

    @Autowired
    private QuestionDisableIpRoMapper questionDisableIpRoMapper;

    @Autowired
    private QuestionDisableUserRoMapper questionDisableUserRoMapper;

    @Override
    public List<CheatsBo> selectList(Map<String,Object> map) {
        List<CheatsBo> cheatsBoList;
        try {
            //查询最新秘籍列表
            cheatsBoList = cheatsRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询秘籍列表信息异常：{}", e);
            throw new ServiceException(6180);
        }
        return cheatsBoList;
    }

    @Override
    public List<CheatsBo> selectListByBrowseNum(Map<String,Object> map) {
        List<CheatsBo> cheatsBoList;
        try {
            //查询热门秘籍列表
            cheatsBoList = cheatsRoMapper.selectListByBrowseNum(map);
        } catch (Exception e) {
            LOGGER.error("查询热门秘籍信息异常：{}", e);
            throw new ServiceException(6180);
        }
        return cheatsBoList;
    }

    @Override
    public List<CheatsBo> selectListRecommend(Map<String,Object> map) {
        List<CheatsBo> cheatsBoList;
        try {
            //查询推荐秘籍列表
            cheatsBoList = cheatsRoMapper.selectListRecommend(map);
        } catch (Exception e) {
            LOGGER.error("查询秘籍列表信息异常：{}", e);
            throw new ServiceException(6180);
        }
        return cheatsBoList;
    }

    @Override
    public List<CheatsBo> selectMyCheatsList(Map<String,Object> map) {
        List<CheatsBo> cheatsBoList;
        try {
            //查询我的文章列表
            cheatsBoList = cheatsRoMapper.selectMyCheatsList(map);
        } catch (Exception e) {
            LOGGER.error("查询我的文章列表异常：{}", e);
            throw new ServiceException(6180);
        }
        return cheatsBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public CheatsBo save(CheatsBo cheatsBo) {

        int ipcnt = questionDisableIpRoMapper.selectIpCnt(cheatsBo.getIp());

        if(ipcnt > 0){
            //该IP已被禁言
            throw new ServiceException(6372);
        }

        int usercnt = questionDisableUserRoMapper.selectUserCnt(cheatsBo.getUserId());

        if(usercnt > 0){
            //该用户已被禁言
            throw new ServiceException(6373);
        }

        try {
            JSONObject jsonStu = JSONObject.fromObject(cheatsBo);
            LOGGER.info("新增秘籍信息:{}", jsonStu.toString());

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("userId", cheatsBo.getUserId());
            dataMap.put("classifyId", cheatsBo.getClassifyCode());
            String factionId = questionRoMapper.selectfactionId(dataMap);
            if(factionId == null){
                factionId = "";
            }
            cheatsBo.setFactionId(factionId);

            cheatsBo.setCreateTime(new Date());
            cheatsBo.setLastUpdate(new Date());
            cheatsBo.setStatus("0");//0正常，1待审查，2拉黑
            cheatsBo.setBrowseNum(0);
            cheatsBo.setCollectNum(0);
            cheatsBo.setReportNum(0);
            cheatsBo.setCommentNum(0);
            cheatsBo.setLikeNum(0);
            cheatsBo.setTrampleNum(0);

            //敏感词校验
            String title = cheatsBo.getTitle();
            String detail = cheatsBo.getDetail();
            List<String> wordList = sensitiveWordsRoMapper.selectListWords();
            if(title != null && !"".equals(title)){
                for(String word : wordList){
                    boolean bl = title.contains(word);
                    if(bl){
                        cheatsBo.setStatus("1");
                        break;
                    }
                }
            }

            if("0".equals(cheatsBo.getStatus())){
                if(detail != null && !"".equals(detail)){
                    for(String word : wordList){
                        boolean bl = detail.contains(word);
                        if(bl){
                            cheatsBo.setStatus("1");
                            break;
                        }
                    }
                }
            }




            //保存问题信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Cheats cheats = new Cheats();
            cheatsBo.setId(uuid);
            BeanUtils.copyProperties(cheatsBo, cheats);


            List<CheatsTag> tagList = cheatsBo.getTagList();

            if(tagList != null){
                for(CheatsTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setCheatsId(uuid);
                    tagMapper.insert(tag);
                }
            }

            cheatsMapper.insert(cheats);
        } catch (Exception e) {
            LOGGER.error("新增秘籍信息异常：{}", e);
            throw new ServiceException(6182);
        }

        return cheatsBo;
    }

    @Override
    public CheatsBo selectCheats(String id) {
        CheatsBo cheatsBo;
        try {
            LOGGER.info("查询单个秘籍信息:{}", id);
            //查询问题信息
            cheatsBo = cheatsRoMapper.selectCheats(id);
            List<CheatsTag> tagList = tagRoMapper.selectList(id);
            cheatsBo.setTagList(tagList);
        } catch (Exception e) {
            LOGGER.error("查询单个秘籍信息异常：{}", e);
            throw new ServiceException(6181);
        }
        return cheatsBo;
    }

    @Override
    public List<CheatsTagBo> selectTagList() {
        List<CheatsTagBo> tagList;
        try {
            //查询热议标签列表
            tagList = tagRoMapper.selectTagList();
        } catch (Exception e) {
            LOGGER.error("查询热议标签列表信息异常：{}", e);
            throw new ServiceException(6185);
        }
        return tagList;
    }

    @Transactional("db1TxManager")
    @Override
    public CheatsBo update(CheatsBo cheatsBo) {
        int ipcnt = questionDisableIpRoMapper.selectIpCnt(cheatsBo.getIp());

        if(ipcnt > 0){
            //该IP已被禁言
            throw new ServiceException(6372);
        }

        int usercnt = questionDisableUserRoMapper.selectUserCnt(cheatsBo.getUserId());

        if(usercnt > 0){
            //该用户已被禁言
            throw new ServiceException(6373);
        }

        //更新秘籍信息
        Cheats cheats = new Cheats();
        try {
            JSONObject jsonStu = JSONObject.fromObject(cheatsBo);
            LOGGER.info("更新秘籍信息:{}", jsonStu.toString());

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("userId", cheatsBo.getUserId());
            dataMap.put("classifyId", cheatsBo.getClassifyCode());
            String factionId = questionRoMapper.selectfactionId(dataMap);
            if(factionId == null){
                factionId = "";
            }
            cheatsBo.setFactionId(factionId);
            cheatsBo.setLastUpdate(new Date());
            cheatsBo.setStatus("0");

            String title = cheatsBo.getTitle();
            String detail = cheatsBo.getDetail();
            List<String> wordList = sensitiveWordsRoMapper.selectListWords();
            if(title != null && !"".equals(title)){
                for(String word : wordList){
                    boolean bl = title.contains(word);
                    if(bl){
                        cheatsBo.setStatus("1");
                        break;
                    }
                }
            }

            if("0".equals(cheatsBo.getStatus())){
                if(detail != null && !"".equals(detail)){
                    for(String word : wordList){
                        boolean bl = detail.contains(word);
                        if(bl){
                            cheatsBo.setStatus("1");
                            break;
                        }
                    }
                }
            }


            BeanUtils.copyProperties(cheatsBo, cheats);


            List<CheatsTag> tagList = cheatsBo.getTagList();

            tagMapper.deleteByPrimaryKey(cheatsBo.getId());

            if(tagList != null){
                for(CheatsTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setCheatsId(cheatsBo.getId());
                    tagMapper.insert(tag);
                }
            }

            cheatsMapper.updateByPrimaryKeySelective(cheats);
        } catch (Exception e) {
            LOGGER.error("更新秘籍信息异常：{}", e);
            throw new ServiceException(6183);
        }
        return cheatsBo;
    }

    @Override
    public String updateStatus(String id,String status) {
        //更新秘籍信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新秘籍信息异常：{}", e);
            throw new ServiceException(6183);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String id) {
        try {
            LOGGER.info("删除秘籍信息:{}", id);
            tagMapper.deleteByPrimaryKey(id);
            cheatsMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("删除秘籍信息异常：{}", e);
            throw new ServiceException(6184);
        }
        return "";
    }

    @Override
    public String updateBrowseNum(String id) {
        cheatsMapper.updateBrowseNum(id);
        return "";
    }


}
