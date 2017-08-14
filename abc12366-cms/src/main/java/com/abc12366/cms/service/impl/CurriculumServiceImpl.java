package com.abc12366.cms.service.impl;

import com.abc12366.cms.mapper.db1.*;
import com.abc12366.cms.mapper.db2.*;
import com.abc12366.cms.model.Model;
import com.abc12366.cms.model.bo.ModelBo;
import com.abc12366.cms.model.bo.ModelListBo;
import com.abc12366.cms.model.curriculum.Curriculum;
import com.abc12366.cms.model.curriculum.CurriculumLabel;
import com.abc12366.cms.model.curriculum.CurriculumLecturerGx;
import com.abc12366.cms.model.curriculum.CurriculumMembergrade;
import com.abc12366.cms.model.curriculum.bo.*;
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

    @Autowired
    private CurriculumLabelMapper curriculumLabelMapper;

    @Autowired
    private CurriculumMembergradeMapper curriculumMembergradeMapper;

    @Autowired
    private CurriculumLecturerGxMapper curriculumLecturerGxMapper;

    @Autowired
    private CurriculumLabelRoMapper curriculumLabelRoMapper;

    @Autowired
    private CurriculumMembergradeRoMapper curriculumMembergradeRoMapper;

    @Autowired
    private CurriculumLecturerGxRoMapper curriculumLecturerGxRoMapper;

    @Autowired
    private CurriculumChapterMapper chapterMapper;

    @Autowired
    private CurriculumChapterRoMapper chapterRoMapper;

    @Autowired
    private CurriculumCoursewareMapper coursewareMapper;

    @Autowired
    private CurriculumCoursewareRoMapper coursewareRoMapper;

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
    public List<CurriculumListBo> selectRecommend() {
        List<CurriculumListBo> curriculumListBoList;
        try {
            //查询推荐课程
            curriculumListBoList = curriculumRoMapper.selectRecommend();
        } catch (Exception e) {
            LOGGER.error("查询课程列表信息异常：{}", e);
            throw new ServiceException(4320);
        }
        return curriculumListBoList;
    }

    @Override
    public CurriculumSituationBo selectSituation(String curriculumId) {
        CurriculumSituationBo curriculumSituationBo;
        try {
            //查询课程授课信息
            curriculumSituationBo = curriculumRoMapper.selectSituation(curriculumId);
        } catch (Exception e) {
            LOGGER.error("新增课程授课信息异常：{}", e);
            throw new ServiceException(4322);
        }
        return curriculumSituationBo;
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
            //保存课程信息
            String uuid = UUID.randomUUID().toString().replace("-", "");
            Curriculum curriculum = new Curriculum();
            curriculumBo.setCurriculumId(uuid);
            BeanUtils.copyProperties(curriculumBo, curriculum);
            curriculumMapper.insert(curriculum);

            List<CurriculumLabel> labelList = curriculumBo.getLabelList();

            for(CurriculumLabel label :labelList){
                curriculumLabelMapper.insert(label);
            }

            List<CurriculumMembergrade> membergradeList = curriculumBo.getMembergradeList();

            for(CurriculumMembergrade grade : membergradeList){
                curriculumMembergradeMapper.insert(grade);
            }

            List<CurriculumLecturerGx> lecturerGxList = curriculumBo.getLecturerGxList();

            for(CurriculumLecturerGx lecturerGx : lecturerGxList){
                curriculumLecturerGxMapper.insert(lecturerGx);
            }

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

            //标签
            List<CurriculumLabel> curriculumLabelList = curriculumLabelRoMapper.selectList(curriculumId);

            curriculumBo.setLabelList(curriculumLabelList);

            //会员等级
            List<CurriculumMembergrade> curriculumMembergradeList = curriculumMembergradeRoMapper.selectList(curriculumId);

            curriculumBo.setMembergradeList(curriculumMembergradeList);

            //讲师
            List<CurriculumLecturerGx> curriculumLecturerGxList =curriculumLecturerGxRoMapper.selectList(curriculumId);

            curriculumBo.setLecturerGxList(curriculumLecturerGxList);


            Map<String, Object> dataMap1 = new HashMap<>();
            dataMap1.put("curriculumId",curriculumId);
            //查询章节列表
            List<CurriculumChapterBo> chapterBoList = chapterRoMapper.selectList(dataMap1);

            for(CurriculumChapterBo chapterBo : chapterBoList){
                String chapterId =chapterBo.getChapterId();
                Map<String, Object> dataMap2 = new HashMap<>();
                dataMap2.put("chapterId",chapterId);
                //查询课件
                List<CurriculumCoursewareBo> coursewareBoList = coursewareRoMapper.selectList(dataMap2);
                chapterBo.setCoursewareList(coursewareBoList);
            }

            curriculumBo.setChapterBoList(chapterBoList);


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

            List<CurriculumLabel> labelList = curriculumBo.getLabelList();

            String curriculumId = curriculumBo.getCurriculumId();

            curriculumLabelMapper.deleteByPrimaryKey(curriculumId);
            for(CurriculumLabel label :labelList){
                curriculumLabelMapper.insert(label);
            }

            List<CurriculumMembergrade> membergradeList = curriculumBo.getMembergradeList();

            curriculumMembergradeMapper.deleteByPrimaryKey(curriculumId);
            for(CurriculumMembergrade grade : membergradeList){
                curriculumMembergradeMapper.insert(grade);
            }

            List<CurriculumLecturerGx> lecturerGxList = curriculumBo.getLecturerGxList();

            curriculumLecturerGxMapper.deleteByPrimaryKey(curriculumId);
            for(CurriculumLecturerGx lecturerGx : lecturerGxList){
                curriculumLecturerGxMapper.insert(lecturerGx);
            }



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
            curriculumLabelMapper.deleteByPrimaryKey(curriculumId);
            curriculumMembergradeMapper.deleteByPrimaryKey(curriculumId);
            curriculumLecturerGxMapper.deleteByPrimaryKey(curriculumId);
            coursewareMapper.deleteByCurriculumId(curriculumId);
            chapterMapper.deleteByCurriculumId(curriculumId);
            curriculumMapper.deleteByPrimaryKey(curriculumId);
        } catch (Exception e) {
            LOGGER.error("删除课程异常：{}", e);
            throw new ServiceException(4324);
        }
        return "";
    }

    @Transactional("db1TxManager")
    @Override
    public String deleteList(String[] curriculumIds) {
        for (int i = 0; i < curriculumIds.length; i++) {
            this.delete(curriculumIds[i]);
        }
        return "";
    }

}
