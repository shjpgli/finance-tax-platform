package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.QuestionFactionMemberMapper;
import com.abc12366.bangbang.mapper.db2.QuestionFactionMemberRoMapper;
import com.abc12366.bangbang.model.question.QuestionFactionMember;
import com.abc12366.bangbang.model.question.bo.QuestionFactionMemberBo;
import com.abc12366.bangbang.service.QueFactionMemberService;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/9/14.
 */
@Service
public class QueFactionMemberServiceImpl implements QueFactionMemberService {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueFactionMemberServiceImpl.class);

    @Autowired
    private QuestionFactionMemberMapper memberMapper;

    @Autowired
    private QuestionFactionMemberRoMapper memberRoMapper;

    @Override
    public List<QuestionFactionMemberBo> selectList(Map<String,Object> map) {
        List<QuestionFactionMemberBo> factionMemberBoList;
        try {
            //查询邦派成员列表
            factionMemberBoList = memberRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询邦派成员列表信息异常：{}", e);
            throw new ServiceException(6130);
        }
        return factionMemberBoList;
    }

    @Override
    public List<QuestionFactionMemberBo> selectListTj(String factionId) {
        List<QuestionFactionMemberBo> factionMemberBoList;
        try {
            //查询邦派成员列表(我管理的邦派)统计
            factionMemberBoList = memberRoMapper.selectListTj(factionId);

            for(QuestionFactionMemberBo factionMemberBo : factionMemberBoList){
                int honor = 2*factionMemberBo.getAnswerNum() + 1*factionMemberBo.getDiscussNum() + 7*factionMemberBo.getAdoptNum();
                factionMemberBo.setHonor(honor+"");
            }

        } catch (Exception e) {
            LOGGER.error("查询邦派成员列表信息异常：{}", e);
            throw new ServiceException(6130);
        }
        return factionMemberBoList;
    }

    @Transactional("db1TxManager")
    @Override
    public QuestionFactionMemberBo save(QuestionFactionMemberBo factionMemberBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(factionMemberBo);
            LOGGER.info("新增邦派成员信息:{}", jsonStu.toString());
            factionMemberBo.setCreateTime(new Date());
            factionMemberBo.setLastUpdate(new Date());
            //保存邦派成员信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            QuestionFactionMember factionMember = new QuestionFactionMember();
            factionMemberBo.setMemberId(uuid);
            BeanUtils.copyProperties(factionMemberBo, factionMember);
            memberMapper.insert(factionMember);
        } catch (Exception e) {
            LOGGER.error("新增邦派成员信息异常：{}", e);
            throw new ServiceException(6132);
        }

        return factionMemberBo;
    }

    @Override
    public QuestionFactionMemberBo selectFactionMember(String memberId) {
        QuestionFactionMemberBo factionMemberBo = new QuestionFactionMemberBo();
        try {
            LOGGER.info("查询单个邦派成员信息:{}", memberId);
            //查询单个邦派成员信息
            QuestionFactionMember factionMember = memberRoMapper.selectByPrimaryKey(memberId);
            BeanUtils.copyProperties(factionMember, factionMemberBo);
        } catch (Exception e) {
            LOGGER.error("查询单个邦派成员信息异常：{}", e);
            throw new ServiceException(6131);
        }
        return factionMemberBo;
    }


    @Transactional("db1TxManager")
    @Override
    public QuestionFactionMemberBo update(QuestionFactionMemberBo factionMemberBo) {
        //更新邦派成员信息
        QuestionFactionMember factionMember = new QuestionFactionMember();
        try {
            JSONObject jsonStu = JSONObject.fromObject(factionMemberBo);
            LOGGER.info("更新邦派成员信息:{}", jsonStu.toString());
            factionMemberBo.setLastUpdate(new Date());
            BeanUtils.copyProperties(factionMemberBo, factionMember);
            memberMapper.updateByPrimaryKeySelective(factionMember);
        } catch (Exception e) {
            LOGGER.error("更新邦派成员信息异常：{}", e);
            throw new ServiceException(6133);
        }
        return factionMemberBo;
    }

    @Override
    public String updateStatus(String memberId,String status) {
        //更新邦派成员信息
        try {

        } catch (Exception e) {
            LOGGER.error("更新邦派成员信息异常：{}", e);
            throw new ServiceException(6133);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String memberId) {
        try {
            LOGGER.info("删除邦派成员信息:{}", memberId);
            memberMapper.deleteByPrimaryKey(memberId);
        } catch (Exception e) {
            LOGGER.error("删除邦派成员异常：{}", e);
            throw new ServiceException(6134);
        }
        return "";
    }

}
