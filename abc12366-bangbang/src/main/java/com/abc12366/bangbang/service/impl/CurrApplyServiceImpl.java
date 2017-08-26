package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.CurriculumApplyMapper;
import com.abc12366.bangbang.mapper.db2.CurriculumApplyRoMapper;
import com.abc12366.bangbang.model.curriculum.CurriculumApply;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumApplyBo;
import com.abc12366.bangbang.service.CurrApplyService;
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
 * Created by xieyanmao on 2017/8/11.
 */
@Service
public class CurrApplyServiceImpl implements CurrApplyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrApplyServiceImpl.class);

    @Autowired
    private CurriculumApplyMapper applyMapper;

    @Autowired
    private CurriculumApplyRoMapper applyRoMapper;

    @Override
    public List<CurriculumApplyBo> selectList(Map<String,Object> map) {
        List<CurriculumApplyBo> applyBoList;
        try {
            //查询课程报名签到列表
            applyBoList = applyRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询课程报名签到列表信息异常：{}", e);
            throw new ServiceException(4370);
        }
        return applyBoList;
    }

    @Override
    public int selectApplyCnt(Map<String,Object> map) {
        int cnt = 0;
        try {
            //查询课程报名人数
            cnt = applyRoMapper.selectApplyCnt(map);
        } catch (Exception e) {
            LOGGER.error("查询课程报名人数信息异常：{}", e);
            throw new ServiceException(4370);
        }
        return cnt;
    }

    @Override
    public CurriculumApplyBo save(CurriculumApplyBo applyBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(applyBo);
            LOGGER.info("新增课程报名签到信息:{}", jsonStu.toString());
            applyBo.setApplyTime(new Date());
            //保存课程报名签到信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            CurriculumApply apply = new CurriculumApply();
            applyBo.setApplyId(uuid);
            BeanUtils.copyProperties(applyBo, apply);
            applyMapper.insert(apply);
        } catch (Exception e) {
            LOGGER.error("新增课程报名签到信息异常：{}", e);
            throw new ServiceException(4372);
        }

        return applyBo;
    }

    @Override
    public CurriculumApplyBo selectApply(String applyId) {
        CurriculumApplyBo applyBo = new CurriculumApplyBo();
        try {
            LOGGER.info("查询单个课程报名签到信息:{}", applyId);
            //查询课程报名签到信息
            CurriculumApply apply = applyRoMapper.selectByPrimaryKey(applyId);
            BeanUtils.copyProperties(apply, applyBo);
        } catch (Exception e) {
            LOGGER.error("查询单个课程报名签到信息异常：{}", e);
            throw new ServiceException(4371);
        }
        return applyBo;
    }

    @Override
    public CurriculumApplyBo selectCurrApply(Map<String,Object> map) {
        CurriculumApplyBo applyBo;
        try {
            //查询课程报名签到列表
            applyBo = applyRoMapper.selectCurrApply(map);
        } catch (Exception e) {
            LOGGER.error("查询课程报名签到信息异常：{}", e);
            throw new ServiceException(4371);
        }
        return applyBo;
    }

    @Override
    public CurriculumApplyBo update(CurriculumApplyBo applyBo) {
        //更新课程报名签到信息
        CurriculumApply apply = new CurriculumApply();
        try {
            JSONObject jsonStu = JSONObject.fromObject(applyBo);
            LOGGER.info("更新课程报名签到信息:{}", jsonStu.toString());
            BeanUtils.copyProperties(applyBo, apply);
            applyMapper.updateByPrimaryKeySelective(apply);
        } catch (Exception e) {
            LOGGER.error("更新课程报名签到信息异常：{}", e);
            throw new ServiceException(4373);
        }
        return applyBo;
    }

    @Override
    public String updateStatus(String applyId,String status) {
        //更新课程报名签到信息
        try {
//            LOGGER.info("更新课程状态信息:{}", ApplyId+","+status);
//            Curriculum curriculum = new Curriculum();
//            curriculum.setCurriculumId(curriculumId);
//            curriculum.setStatus(Integer.parseInt(status));
//            curriculum.setUpdateTime(new Date());
//            //1为发布
//            if("1".equals(status)){
//                curriculum.setIssueTime(new Date());
//            }else{
//                curriculum.setIssueTime(null);
//            }
//            ApplyMapper.updateStatus(curriculum);
        } catch (Exception e) {
            LOGGER.error("更新课程报名签到信息异常：{}", e);
            throw new ServiceException(4373);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String applyId) {
        try {
            LOGGER.info("删除课程报名签到信息:{}", applyId);
            applyMapper.deleteByPrimaryKey(applyId);
        } catch (Exception e) {
            LOGGER.error("删除课程报名签到信息异常：{}", e);
            throw new ServiceException(4374);
        }
        return "";
    }

}
