package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.common.UcUserCommon;
import com.abc12366.bangbang.mapper.db1.*;
import com.abc12366.bangbang.mapper.db2.*;
import com.abc12366.bangbang.model.curriculum.Curriculum;
import com.abc12366.bangbang.model.curriculum.CurriculumLabel;
import com.abc12366.bangbang.model.curriculum.CurriculumLecturerGx;
import com.abc12366.bangbang.model.curriculum.CurriculumMembergrade;
import com.abc12366.bangbang.model.curriculum.bo.*;
import com.abc12366.bangbang.service.CurriculumService;
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
    private CurriculumLecturerRoMapper lecturerRoMapper;

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
    public List<CurriculumListsyBo> selectListNew(Map<String,Object> map) {
        List<CurriculumListsyBo> curriculumListBoList;
        try {
            //查询最新课程列表
            curriculumListBoList = curriculumRoMapper.selectListNew(map);
        } catch (Exception e) {
            LOGGER.error("查询课程列表信息异常：{}", e);
            throw new ServiceException(4320);
        }
        return curriculumListBoList;
    }

    @Override
    public List<CurriculumListsyBo> selectListWatch(Map<String,Object> map) {
        List<CurriculumListsyBo> curriculumListBoList;
        try {
            //查询最热课程列表
            curriculumListBoList = curriculumRoMapper.selectListWatch(map);
        } catch (Exception e) {
            LOGGER.error("查询课程列表信息异常：{}", e);
            throw new ServiceException(4320);
        }
        return curriculumListBoList;
    }

    @Override
    public List<CurriculumListsyBo> selectRecommend(Map<String,Object> map) {
        List<CurriculumListsyBo> curriculumListBoList;
        try {
            //查询推荐课程
            curriculumListBoList = curriculumRoMapper.selectRecommend(map);
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
            LOGGER.error("查询课程授课信息异常：{}", e);
            throw new ServiceException(4321);
        }
        return curriculumSituationBo;
    }

    @Override
    public List<CurriculumLabelBo> selectLabelList() {
        List<CurriculumLabelBo> CurriculumLabelBoList;
        try {
            //查询课程标签列表
            CurriculumLabelBoList = curriculumLabelRoMapper.selectLabelList();
        } catch (Exception e) {
            LOGGER.error("查询课程标签信息异常：{}", e);
            throw new ServiceException(5000);
        }
        return CurriculumLabelBoList;
    }

    @Override
    public int selectCurrCntByGoodsId(String goodsId) {
        int cnt=0;
        try {
            //查询课程
            cnt = curriculumRoMapper.selectCurrCntByGoodsId(goodsId);
        } catch (Exception e) {
            LOGGER.error("查询课程信息异常：{}", e);
            throw new ServiceException(4321);
        }
        return cnt;
    }

    @Override
    public CurriculumBo save(CurriculumBo curriculumBo) {
        String goodsId = curriculumBo.getGoodsId();
        if(goodsId == null){
            goodsId = "";
        }
        if(1 == curriculumBo.getIsFree()){
            curriculumBo.setGoodsId("");
        }else{
            int cnt = curriculumRoMapper.selectCurrCntByGoodsId(goodsId);
            if(cnt>0){
                //该商品已被课程使用，请重新选择商品
                throw new ServiceException(4326);
            }
        }

        Map<String, Object> dataMap1 = new HashMap<>();
        dataMap1.put("title", curriculumBo.getTitle());
        int cnt1 = curriculumRoMapper.selectCurriculumCnt(dataMap1);
        if(cnt1 > 0){
            throw new ServiceException(4329);
        }

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

            String userId = UcUserCommon.getAdminId();

            Curriculum curriculum = new Curriculum();
            curriculumBo.setCurriculumId(uuid);
            curriculumBo.setCreaterId(userId);
            BeanUtils.copyProperties(curriculumBo, curriculum);
            curriculumMapper.insert(curriculum);

            List<CurriculumLabel> labelList = curriculumBo.getLabelList();

            if(labelList != null){
                for(CurriculumLabel label :labelList){
                    label.setCurriculumId(uuid);
                    curriculumLabelMapper.insert(label);
                }
            }


            List<CurriculumMembergrade> membergradeList = curriculumBo.getMembergradeList();

            if(membergradeList != null){
                for(CurriculumMembergrade grade : membergradeList){
                    grade.setCurriculumId(uuid);
                    curriculumMembergradeMapper.insert(grade);
                }
            }


            List<CurriculumLecturerGx> lecturerGxList = curriculumBo.getLecturerGxList();

            if(lecturerGxList != null){
                for(CurriculumLecturerGx lecturerGx : lecturerGxList){
                    lecturerGx.setCurriculumId(uuid);
                    curriculumLecturerGxMapper.insert(lecturerGx);
                }
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
            //查询课程信息
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
    public CurriculumsyBo selectCurriculumsy(String curriculumId) {
        CurriculumsyBo curriculumBo;
        try {
            LOGGER.info("查询单个课程信息:{}", curriculumId);
            //查询课程信息
            curriculumBo = curriculumRoMapper.selectCurriculum(curriculumId);

            if(curriculumBo == null){
                return curriculumBo;
            }

            //标签
            List<CurriculumLabel> curriculumLabelList = curriculumLabelRoMapper.selectList(curriculumId);

            curriculumBo.setLabelList(curriculumLabelList);

            //会员等级
            List<CurriculumMembergrade> curriculumMembergradeList = curriculumMembergradeRoMapper.selectList(curriculumId);

            curriculumBo.setMembergradeList(curriculumMembergradeList);

            //讲师
            List<CurriculumLecturerBo> lecturerBoList =lecturerRoMapper.selectListByCurr(curriculumId);

            curriculumBo.setLecturerList(lecturerBoList);


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

            List<CurriculumListsyBo> curriculumListBoList;
            //查询相关课程列表
            curriculumListBoList = curriculumRoMapper.selectListxgNew(curriculumId);

            curriculumBo.setCurriculumListBoList(curriculumListBoList);


        } catch (Exception e) {
            LOGGER.error("查询单个课程信息异常：{}", e);
            throw new ServiceException(4321);
        }
        return curriculumBo;
    }

    @Override
    public CurriculumEvaluateTjBo selectEvaluateTj(String curriculumId) {
        CurriculumEvaluateTjBo evaluateTjBo;
        try {
            LOGGER.info("查询单个课程评价统计信息:{}", curriculumId);
            //查询课程评价统计信息
            evaluateTjBo = curriculumRoMapper.selectEvaluateTj(curriculumId);

        } catch (Exception e) {
            LOGGER.error("查询单个课程评价统计信息异常：{}", e);
            throw new ServiceException(4321);
        }
        return evaluateTjBo;
    }

    @Override
    public CurriculumBo update(CurriculumBo curriculumBo) {
        Map<String, Object> dataMap1 = new HashMap<>();
        dataMap1.put("curriculumId", curriculumBo.getCurriculumId());
        dataMap1.put("title", curriculumBo.getTitle());
        int cnt1 = curriculumRoMapper.selectCurriculumCnt(dataMap1);
        if(cnt1 > 0){
            throw new ServiceException(4329);
        }
        String curriculumId = curriculumBo.getCurriculumId();
        String goodsId = curriculumBo.getGoodsId();
        if(goodsId == null){
            goodsId = "";
        }


        //1为发布
        if(curriculumBo.getStatus() == 1){
            curriculumBo.setIssueTime(new Date());
            int cnt = curriculumRoMapper.selectCoursewareCnt(curriculumId);
            if(cnt == 0){
                //该课程下没有添加课件，不能发布
                throw new ServiceException(4328);
            }
        }else{
            curriculumBo.setIssueTime(null);
        }

        //查询课程信息
        Curriculum curriculum1 = curriculumRoMapper.selectByPrimaryKey(curriculumId);


        if(curriculumBo.getStatus() == 0){
            if(1 == curriculumBo.getIsFree()){
                curriculumBo.setGoodsId("");
            }else{
                if(!goodsId.equals(curriculum1.getGoodsId())){
                    int cnt = curriculumRoMapper.selectCurrCntByGoodsId(goodsId);
                    if(cnt>0){
                        //该商品已被课程使用，请重新选择商品
                        throw new ServiceException(4326);
                    }
                }
            }
        }else{
            if(!"".equals(goodsId)){
                if(!"".equals(curriculum1.getGoodsId()) && !goodsId.equals(curriculum1.getGoodsId())){
                    //商品不能修改
                    throw new ServiceException(4327);
                }
            }else{
                curriculumBo.setGoodsId(curriculum1.getGoodsId());
            }

        }

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

            curriculumLabelMapper.deleteByPrimaryKey(curriculumId);
            if(labelList != null){
                for(CurriculumLabel label :labelList){
                    label.setCurriculumId(curriculumId);
                    curriculumLabelMapper.insert(label);
                }
            }


            List<CurriculumMembergrade> membergradeList = curriculumBo.getMembergradeList();

            curriculumMembergradeMapper.deleteByPrimaryKey(curriculumId);
            if(membergradeList != null){
                for(CurriculumMembergrade grade : membergradeList){
                    grade.setCurriculumId(curriculumId);
                    curriculumMembergradeMapper.insert(grade);
                }
            }


            List<CurriculumLecturerGx> lecturerGxList = curriculumBo.getLecturerGxList();

            curriculumLecturerGxMapper.deleteByPrimaryKey(curriculumId);
            if(lecturerGxList != null){
                for(CurriculumLecturerGx lecturerGx : lecturerGxList){
                    lecturerGx.setCurriculumId(curriculumId);
                    curriculumLecturerGxMapper.insert(lecturerGx);
                }
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

            LOGGER.info("更新课程状态信息:{}", curriculumId+","+status);
            Curriculum curriculum = new Curriculum();
            curriculum.setCurriculumId(curriculumId);
            curriculum.setStatus(Integer.parseInt(status));
            curriculum.setUpdateTime(new Date());
            //1为发布
            if("1".equals(status)){
                curriculum.setIssueTime(new Date());
                int cnt = curriculumRoMapper.selectCoursewareCnt(curriculumId);
                if(cnt == 0){
                    //该课程下没有添加课件，不能发布
                    throw new ServiceException(4328);
                }
            }else{
                curriculum.setIssueTime(null);
            }
        try {
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

    @Override
    public List<CurrMyStudyBo> selectStudyHistory(Map<String,Object> map) {
        List<CurrMyStudyBo> currMyStudyBoList;
        try {
            //查询课程学习历史
            currMyStudyBoList = curriculumRoMapper.selectStudyHistory(map);
        } catch (Exception e) {
            LOGGER.error("查询课程列表信息异常：{}", e);
            throw new ServiceException(4320);
        }
        return currMyStudyBoList;
    }

    @Override
    public List<CurriculumListsyBo> selectListCollect(Map<String,Object> map) {
        List<CurriculumListsyBo> curriculumListBoList;
        try {
            //查询收藏课程列表
            curriculumListBoList = curriculumRoMapper.selectListCollect(map);
        } catch (Exception e) {
            LOGGER.error("查询课程列表信息异常：{}", e);
            throw new ServiceException(4320);
        }
        return curriculumListBoList;
    }

    @Override
    public CurrMyStudyNumBo selectStudyNum(Map<String,Object> map) {
        CurrMyStudyNumBo currMyStudyNumBo = new CurrMyStudyNumBo();
        try {
            //查询本月学习课程数
            int cnt1 = curriculumRoMapper.selectStudyNumByMonth(map);
            currMyStudyNumBo.setStudyNumMonth(cnt1);
            //查询本年学习课程数
            int cnt2 = curriculumRoMapper.selectStudyNumByYear(map);
            currMyStudyNumBo.setStudyNumYear(cnt2);
            //查询学习勤奋榜
            String cnt3 = curriculumRoMapper.selectStudyQfb(map);
            currMyStudyNumBo.setStudyQfb(cnt3);
        } catch (Exception e) {
            LOGGER.error("查询课程信息异常：{}", e);
            throw new ServiceException(4321);
        }
        return currMyStudyNumBo;
    }

    @Override
    public String updateBrowsesDay(String curriculumId) {
        try {
            LOGGER.info("课程浏览信息:{}", curriculumId);
            curriculumMapper.updateBrowsesDay(curriculumId);
        } catch (Exception e) {
            LOGGER.error("更新课程浏览量异常：{}", e);
            throw new ServiceException(4324);
        }
        return "";
    }

}
