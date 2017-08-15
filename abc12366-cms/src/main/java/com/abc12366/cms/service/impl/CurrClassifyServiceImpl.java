package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.CurriculumClassifyMapper;
import com.abc12366.cms.mapper.db2.CurriculumClassifyRoMapper;
import com.abc12366.cms.model.curriculum.CurriculumClassify;
import com.abc12366.cms.model.curriculum.bo.CurriculumClassifyBo;
import com.abc12366.cms.service.CurrClassifyService;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by xieyanmao on 2017/8/11.
 */
@Service
public class CurrClassifyServiceImpl implements CurrClassifyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrClassifyServiceImpl.class);

    @Autowired
    private CurriculumClassifyMapper classifyMapper;

    @Autowired
    private CurriculumClassifyRoMapper classifyRoMapper;

    @Override
    public List<CurriculumClassifyBo> selectList(Map<String,Object> map) {
        List<CurriculumClassifyBo> classifyBoList;
        try {
            //查询课程分类列表
            classifyBoList = classifyRoMapper.selectList(map);

        } catch (Exception e) {
            LOGGER.error("查询课程分类信息异常：{}", e);
            throw new ServiceException(4300);
        }
        return classifyBoList;
    }

    @Override
    public CurriculumClassifyBo save(CurriculumClassifyBo classifyBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(classifyBo);
            LOGGER.info("新增课程分类信息:{}", jsonStu.toString());
            //保存课程分类信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            CurriculumClassify classify = new CurriculumClassify();
            classifyBo.setClassifyId(uuid);
            BeanUtils.copyProperties(classifyBo, classify);
            classifyMapper.insert(classify);
        } catch (Exception e) {
            LOGGER.error("新增课程分类信息异常：{}", e);
            throw new ServiceException(4302);
        }

        return classifyBo;
    }

    @Override
    public CurriculumClassifyBo selectClassify(String classifyId) {
        CurriculumClassifyBo classifyBo = new CurriculumClassifyBo();
        try {
            LOGGER.info("查询单个课程分类信息:{}", classifyId);
            //查询课程分类信息
            CurriculumClassify classify = classifyRoMapper.selectByPrimaryKey(classifyId);
            BeanUtils.copyProperties(classify, classifyBo);
        } catch (Exception e) {
            LOGGER.error("查询单个课程分类信息异常：{}", e);
            throw new ServiceException(4301);
        }
        return classifyBo;
    }

    @Override
    public CurriculumClassifyBo update(CurriculumClassifyBo classifyBo) {
        //更新课程分类信息
        CurriculumClassify classify = new CurriculumClassify();
        try {
            JSONObject jsonStu = JSONObject.fromObject(classifyBo);
            LOGGER.info("更新课程分类信息:{}", jsonStu.toString());
            BeanUtils.copyProperties(classifyBo, classify);
            classifyMapper.updateByPrimaryKeySelective(classify);
        } catch (Exception e) {
            LOGGER.error("更新课程分类信息异常：{}", e);
            throw new ServiceException(4303);
        }
        return classifyBo;
    }

    @Override
    public String updateStatus(String classifyId,String status) {
        //更新课程分类信息
        try {
//            LOGGER.info("更新课程状态信息:{}", ClassifyId+","+status);
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
//            ClassifyMapper.updateStatus(curriculum);
        } catch (Exception e) {
            LOGGER.error("更新课程分类信息异常：{}", e);
            throw new ServiceException(4303);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String classifyId) {
        try {
            LOGGER.info("删除课程分类信息:{}", classifyId);
            classifyMapper.deleteByPrimaryKey(classifyId);
        } catch (Exception e) {
            LOGGER.error("删除课程分类异常：{}", e);
            throw new ServiceException(4304);
        }
        return "";
    }

}
