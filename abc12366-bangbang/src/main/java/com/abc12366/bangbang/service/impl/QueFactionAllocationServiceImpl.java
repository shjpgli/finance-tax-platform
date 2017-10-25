package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionFactionAllocationMapper;
import com.abc12366.bangbang.mapper.db2.QuestionFactionAllocationRoMapper;
import com.abc12366.bangbang.model.question.QuestionFactionAllocation;
import com.abc12366.bangbang.model.question.bo.QuestionFactionAllocationBo;
import com.abc12366.bangbang.service.QueFactionAllocationService;
import com.abc12366.bangbang.service.QueFactionMemberService;
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
public class QueFactionAllocationServiceImpl implements QueFactionAllocationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueFactionAllocationServiceImpl.class);

    @Autowired
    private QuestionFactionAllocationMapper allocationMapper;

    @Autowired
    private QuestionFactionAllocationRoMapper allocationRoMapper;

    @Override
    public List<QuestionFactionAllocationBo> selectList(Map<String,Object> map) {
        List<QuestionFactionAllocationBo> factionMemberBoList;
        try {
            //查询邦派奖励分配列表
            factionMemberBoList = allocationRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询邦派奖励分配列表信息异常：{}", e);
            throw new ServiceException(6140);
        }
        return factionMemberBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionFactionAllocationBo save(QuestionFactionAllocationBo factionAllocationBo) {
        JSONObject jsonStu = JSONObject.fromObject(factionAllocationBo);
        LOGGER.info("新增邦派奖励分配信息:{}", jsonStu.toString());
        Map<String, Object> dataMap = new HashMap<>();
//        dataMap.put("factionId", factionAllocationBo.getFactionId());//
//        int cnt = allocationRoMapper.selectMemberCnt(dataMap);
//        if(cnt > 0){
//            //已申请加入邦派，请勿重复申请
//            throw new ServiceException(6145);
//        }
        try {
            factionAllocationBo.setCreateTime(new Date());
            factionAllocationBo.setUpdateTime(new Date());
            //保存邦派成员信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            QuestionFactionAllocation factionAllocation = new QuestionFactionAllocation();
            factionAllocationBo.setId(uuid);
            factionAllocationBo.setState(1);
            BeanUtils.copyProperties(factionAllocationBo, factionAllocation);
            allocationMapper.insert(factionAllocation);
        } catch (Exception e) {
            LOGGER.error("新增邦派奖励分配信息异常：{}", e);
            throw new ServiceException(6142);
        }

        return factionAllocationBo;
    }

    @Override
    public QuestionFactionAllocationBo selectFactionAllocation(String id) {
        QuestionFactionAllocationBo factionAllocationBo = new QuestionFactionAllocationBo();
        try {
            LOGGER.info("查询单个邦派奖励分配信息:{}", id);
            //查询单个邦派奖励分配信息
            QuestionFactionAllocation factionAllocation = allocationRoMapper.selectByPrimaryKey(id);
            BeanUtils.copyProperties(factionAllocation, factionAllocationBo);
        } catch (Exception e) {
            LOGGER.error("查询单个邦派奖励分配信息异常：{}", e);
            throw new ServiceException(6141);
        }
        return factionAllocationBo;
    }


    @Transactional("db1TxManager")
    @Override
    public QuestionFactionAllocationBo update(QuestionFactionAllocationBo factionAllocationBo) {
        //更新邦派奖励分配信息
        QuestionFactionAllocation factionAllocation = new QuestionFactionAllocation();
        try {
            JSONObject jsonStu = JSONObject.fromObject(factionAllocationBo);
            LOGGER.info("更新邦派奖励分配信息:{}", jsonStu.toString());
            factionAllocationBo.setUpdateTime(new Date());
            BeanUtils.copyProperties(factionAllocationBo, factionAllocation);
            allocationMapper.updateByPrimaryKeySelective(factionAllocation);
        } catch (Exception e) {
            LOGGER.error("更新邦派奖励分配信息异常：{}", e);
            throw new ServiceException(6143);
        }
        return factionAllocationBo;
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String id) {
        try {
            LOGGER.info("删除邦派奖励分配信息:{}", id);
            allocationMapper.deleteByPrimaryKey(id);
        } catch (Exception e) {
            LOGGER.error("删除邦派奖励分配异常：{}", e);
            throw new ServiceException(6144);
        }
        return "";
    }

}
