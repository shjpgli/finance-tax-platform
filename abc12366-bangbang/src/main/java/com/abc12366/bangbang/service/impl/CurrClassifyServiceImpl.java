package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.CurriculumClassifyMapper;
import com.abc12366.bangbang.mapper.db1.CurriculumClassifyTagMapper;
import com.abc12366.bangbang.mapper.db1.QuestionClassifyTagMapper;
import com.abc12366.bangbang.mapper.db2.CurriculumClassifyRoMapper;
import com.abc12366.bangbang.mapper.db2.CurriculumClassifyTagRoMapper;
import com.abc12366.bangbang.mapper.db2.CurriculumRoMapper;
import com.abc12366.bangbang.mapper.db2.QuestionClassifyTagRoMapper;
import com.abc12366.bangbang.model.curriculum.CurriculumClassify;
import com.abc12366.bangbang.model.curriculum.CurriculumClassifyTag;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumClassifyBo;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumClassifyTagBo;
import com.abc12366.bangbang.service.CurrClassifyService;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Random;
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

    @Autowired
    private CurriculumRoMapper curriculumRoMapper;

    @Autowired
    private CurriculumClassifyTagMapper tagMapper;

    @Autowired
    private CurriculumClassifyTagRoMapper tagRoMapper;


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

            JSONObject jsonStu = JSONObject.fromObject(classifyBo);
            LOGGER.info("新增课程分类信息:{}", jsonStu.toString());
            //保存课程分类信息
//            String uuid = UUID.randomUUID().toString().replace("-", "");

            String uuid = "";
            String code = "";

            String parentId = classifyBo.getParentId();

            for(int i=0;i<20;i++){
                code = this.genCodes(6);
                uuid = parentId + code;
                int cnt = classifyRoMapper.selectClassifyCnt(uuid);
                if(cnt ==0){
                    break;
                }
            }

            CurriculumClassify classify = new CurriculumClassify();
            classifyBo.setClassifyId(uuid);


            int cnt1 = classifyRoMapper.selectClassifyNameCnt(classifyBo);
            if(cnt1 >0){
                //分类名称不能重复
                throw new ServiceException(4306);
            }

        try {
            BeanUtils.copyProperties(classifyBo, classify);
            List<CurriculumClassifyTag> tagList = classifyBo.getTagList();

            if(tagList != null){
                for(CurriculumClassifyTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setClassifyId(uuid);
                    tagMapper.insert(tag);
                }
            }
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
            List<CurriculumClassifyTag> tagList = tagRoMapper.selectList(classifyId);
            BeanUtils.copyProperties(classify, classifyBo);
            classifyBo.setTagList(tagList);
        } catch (Exception e) {
            LOGGER.error("查询单个课程分类信息异常：{}", e);
            throw new ServiceException(4301);
        }
        return classifyBo;
    }

    @Override
    public List<CurriculumClassifyTagBo> selectClassifyTagList(String classifyId) {
        List<CurriculumClassifyTagBo> classifyTagBoList;
        try {
            LOGGER.info("查询单个课程分类标签信息:{}", classifyId);
            //查询课程分类标签信息
            classifyTagBoList = tagRoMapper.selectClassifyTagList(classifyId);
        } catch (Exception e) {
            LOGGER.error("查询单个课程分类标签信息异常：{}", e);
            throw new ServiceException(4301);
        }
        return classifyTagBoList;
    }

    @Override
    public CurriculumClassifyBo update(CurriculumClassifyBo classifyBo) {
        //更新课程分类信息
        CurriculumClassify classify = new CurriculumClassify();
        int cnt1 = classifyRoMapper.selectClassifyNameCnt(classifyBo);
        if(cnt1 >0){
            //分类名称不能重复
            throw new ServiceException(4306);
        }
        try {
            JSONObject jsonStu = JSONObject.fromObject(classifyBo);
            LOGGER.info("更新课程分类信息:{}", jsonStu.toString());
            BeanUtils.copyProperties(classifyBo, classify);
            tagMapper.deleteByPrimaryKey(classifyBo.getClassifyId());
            List<CurriculumClassifyTag> tagList = classifyBo.getTagList();

            if(tagList != null){
                for(CurriculumClassifyTag tag :tagList){
                    tag.setId(UUID.randomUUID().toString().replace("-", ""));
                    tag.setClassifyId(classifyBo.getClassifyId());
                    tagMapper.insert(tag);
                }
            }
            classifyMapper.insert(classify);
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
        LOGGER.info("删除课程分类信息:{}", classifyId);
        int cnt = curriculumRoMapper.selectCurrCntByClassify(classifyId);
        if(cnt > 0){
            throw new ServiceException(4305);
        }
        try {
            tagMapper.deleteByPrimaryKey(classifyId);
            classifyMapper.deleteByPrimaryKey(classifyId);
        } catch (Exception e) {
            LOGGER.error("删除课程分类异常：{}", e);
            throw new ServiceException(4304);
        }
        return "";
    }

    public String genCodes(int length){

        String val = "";
        Random random = new Random();
        for(int i = 0; i < length; i++)
        {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; //取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
            }
            else if("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        val=val.toLowerCase();

        return val;
    }

}
