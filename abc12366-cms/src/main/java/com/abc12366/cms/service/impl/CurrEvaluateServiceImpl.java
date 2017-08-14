package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.CurriculumEvaluateMapper;
import com.abc12366.cms.mapper.db2.CurriculumEvaluateRoMapper;
import com.abc12366.cms.model.curriculum.CurriculumEvaluate;
import com.abc12366.cms.model.curriculum.bo.CurriculumEvaluateBo;
import com.abc12366.cms.service.CurrEvaluateService;
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
public class CurrEvaluateServiceImpl implements CurrEvaluateService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrEvaluateServiceImpl.class);

    @Autowired
    private CurriculumEvaluateMapper evaluateMapper;

    @Autowired
    private CurriculumEvaluateRoMapper evaluateRoMapper;

    @Override
    public List<CurriculumEvaluateBo> selectList(Map<String,Object> map) {
        List<CurriculumEvaluateBo> evaluateBoList;
        try {
            //查询课程评价列表
            evaluateBoList = evaluateRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询课程评价列表信息异常：{}", e);
            throw new ServiceException(4310);
        }
        return evaluateBoList;
    }

    @Override
    public CurriculumEvaluateBo save(CurriculumEvaluateBo evaluateBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(evaluateBo);
            LOGGER.info("新增课程评价信息:{}", jsonStu.toString());
            evaluateBo.setEvaluateTime(new Date());
            //保存课程评价信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            CurriculumEvaluate evaluate = new CurriculumEvaluate();
            evaluateBo.setEvaluateId(uuid);
            BeanUtils.copyProperties(evaluateBo, evaluate);
            evaluateMapper.insert(evaluate);
        } catch (Exception e) {
            LOGGER.error("新增课程评价信息异常：{}", e);
            throw new ServiceException(4312);
        }

        return evaluateBo;
    }

    @Override
    public CurriculumEvaluateBo selectEvaluate(String evaluateId) {
        CurriculumEvaluateBo evaluateBo = new CurriculumEvaluateBo();
        try {
            LOGGER.info("查询单个课程评价信息:{}", evaluateId);
            //查询课程评价信息
            CurriculumEvaluate evaluate = evaluateRoMapper.selectByPrimaryKey(evaluateId);
            BeanUtils.copyProperties(evaluate, evaluateBo);
        } catch (Exception e) {
            LOGGER.error("查询单个课程评价信息异常：{}", e);
            throw new ServiceException(4311);
        }
        return evaluateBo;
    }

    @Override
    public CurriculumEvaluateBo update(CurriculumEvaluateBo evaluateBo) {
        //更新课程评价信息
        CurriculumEvaluate evaluate = new CurriculumEvaluate();
        try {
            JSONObject jsonStu = JSONObject.fromObject(evaluateBo);
            LOGGER.info("更新课程评价信息:{}", jsonStu.toString());
            BeanUtils.copyProperties(evaluateBo, evaluate);
            evaluateMapper.updateByPrimaryKeySelective(evaluate);
        } catch (Exception e) {
            LOGGER.error("更新课程评价信息异常：{}", e);
            throw new ServiceException(4313);
        }
        return evaluateBo;
    }

    @Override
    public String updateStatus(String evaluateId,String status) {
        //更新课程评价信息
        try {
//            LOGGER.info("更新课程状态信息:{}", EvaluateId+","+status);
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
//            EvaluateMapper.updateStatus(curriculum);
        } catch (Exception e) {
            LOGGER.error("更新更新评价信息异常：{}", e);
            throw new ServiceException(4313);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String evaluateId) {
        try {
            LOGGER.info("删除课程评价信息:{}", evaluateId);
            evaluateMapper.deleteByPrimaryKey(evaluateId);
        } catch (Exception e) {
            LOGGER.error("删除课程评价信息异常：{}", e);
            throw new ServiceException(4314);
        }
        return "";
    }

}
