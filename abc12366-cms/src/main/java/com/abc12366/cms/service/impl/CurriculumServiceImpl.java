package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.CurriculumMapper;
import com.abc12366.cms.mapper.db1.ModelItemMapper;
import com.abc12366.cms.mapper.db1.ModelMapper;
import com.abc12366.cms.mapper.db2.CurriculumRoMapper;
import com.abc12366.cms.mapper.db2.ModelRoMapper;
import com.abc12366.cms.model.Model;
import com.abc12366.cms.model.bo.ModelBo;
import com.abc12366.cms.model.bo.ModelListBo;
import com.abc12366.cms.model.curriculum.Curriculum;
import com.abc12366.cms.model.curriculum.bo.CurriculumBo;
import com.abc12366.cms.model.curriculum.bo.CurriculumListBo;
import com.abc12366.cms.service.CurriculumService;
import com.abc12366.cms.service.ModelService;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by xieyanmao on 2017/8/10.
 */
@Service
public class CurriculumServiceImpl implements CurriculumService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CurriculumServiceImpl.class);

    @Autowired
    private CurriculumMapper curriculumMapper;

    @Autowired
    private CurriculumRoMapper curriculumRoMapper;

    @Override
    public List<CurriculumListBo> selectList(Map<String,Object> map) {
        List<CurriculumListBo> curriculumListBoList;
        try {
            //查询课程列表
            curriculumListBoList = curriculumRoMapper.selectList(map);
        } catch (Exception e) {
            LOGGER.error("查询课程列表信息异常：{}", e);
            throw new ServiceException(4320);
        }
        return curriculumListBoList;
    }

    @Override
    public CurriculumBo save(CurriculumBo curriculumBo) {
        try {
            JSONObject jsonStu = JSONObject.fromObject(curriculumBo);
            LOGGER.info("新增课程信息:{}", jsonStu.toString());
            if(1==curriculumBo.getStatus()){
                curriculumBo.setIssueTime(new Date());
            }
            curriculumBo.setCreaterTime(new Date());
            curriculumBo.setUpdateTime(new Date());
            //保存模型信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Curriculum curriculum = new Curriculum();
            curriculumBo.setCurriculumId(uuid);
            BeanUtils.copyProperties(curriculumBo, curriculum);
            curriculumMapper.insert(curriculum);
        } catch (Exception e) {
            LOGGER.error("新增课程信息异常：{}", e);
            throw new ServiceException(4322);
        }

        return curriculumBo;
    }

    @Override
    public CurriculumBo selectCurriculum(String curriculumId) {
        CurriculumBo curriculumBo = new CurriculumBo();
        try {
            LOGGER.info("查询单个课程信息:{}", curriculumId);
            //查询模型信息
            Curriculum curriculum = curriculumRoMapper.selectByPrimaryKey(curriculumId);
            BeanUtils.copyProperties(curriculum, curriculumBo);
        } catch (Exception e) {
            LOGGER.error("查询单个课程信息异常：{}", e);
            throw new ServiceException(4321);
        }
        return curriculumBo;
    }

    @Override
    public CurriculumBo update(CurriculumBo curriculumBo) {
        //更新模型信息
        Curriculum curriculum = new Curriculum();
        try {
            JSONObject jsonStu = JSONObject.fromObject(curriculumBo);
            LOGGER.info("更新课程信息:{}", jsonStu.toString());
            if(1==curriculumBo.getStatus()){
                curriculumBo.setIssueTime(new Date());
            }
            curriculumBo.setUpdateTime(new Date());
            BeanUtils.copyProperties(curriculumBo, curriculum);
            curriculumMapper.updateByPrimaryKeySelective(curriculum);
        } catch (Exception e) {
            LOGGER.error("更新课程信息异常：{}", e);
            throw new ServiceException(4323);
        }
        return curriculumBo;
    }

    @Override
    public String updateStatus(String curriculumId,String status) {
        //更新模型信息
        try {
            LOGGER.info("更新课程状态信息:{}", curriculumId+","+status);
            Curriculum curriculum = new Curriculum();
            curriculum.setCurriculumId(curriculumId);
            curriculum.setStatus(Integer.parseInt(status));
            curriculum.setUpdateTime(new Date());
            //1为发布
            if("1".equals(status)){
                curriculum.setIssueTime(new Date());
            }else{
                curriculum.setIssueTime(null);
            }
            curriculumMapper.updateStatus(curriculum);
        } catch (Exception e) {
            LOGGER.error("更新课程信息异常：{}", e);
            throw new ServiceException(4323);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String delete(String curriculumId) {
        try {
            LOGGER.info("删除课程信息:{}", curriculumId);
            curriculumMapper.deleteByPrimaryKey(curriculumId);
        } catch (Exception e) {
            LOGGER.error("删除课程异常：{}", e);
            throw new ServiceException(4324);
        }
        return "";
    }

}
