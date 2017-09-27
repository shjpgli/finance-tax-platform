package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.CurriculumApplyMapper;
import com.abc12366.bangbang.mapper.db2.CurriculumApplyRoMapper;
import com.abc12366.bangbang.mapper.db2.CurriculumRoMapper;
import com.abc12366.bangbang.model.Message;
import com.abc12366.bangbang.model.curriculum.Curriculum;
import com.abc12366.bangbang.model.curriculum.CurriculumApply;
import com.abc12366.bangbang.model.curriculum.bo.CurriculumApplyBo;
import com.abc12366.bangbang.service.CurrApplyService;
import com.abc12366.bangbang.util.MessageConstant;
import com.abc12366.bangbang.util.MessageSendUtil;
import com.abc12366.gateway.exception.ServiceException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private MessageSendUtil messageSendUtil;

    @Autowired
    private CurriculumRoMapper curriculumRoMapper;

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
    public CurriculumApplyBo save(CurriculumApplyBo applyBo,HttpServletRequest request) {

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("curriculumId",applyBo.getCurriculumId());
        dataMap.put("userId",applyBo.getUserId());
        int cnt = selectApplyCnt(dataMap);
        if(cnt >0){
            //不能重复报名
            throw new ServiceException(4375);
        }
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

            Curriculum curriculum = curriculumRoMapper.selectByPrimaryKey(applyBo.getCurriculumId());

            String curriculumTitle = "";
            String time = "";
            String site = "";
            if(curriculum !=null){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:ss");
                curriculumTitle = curriculum.getTitle();
                if("live".equals(curriculum.getTeachingMethod())){
                    time = sdf.format(curriculum.getSignTimeBegin());
                    Message message = new Message();
                    message.setBusinessId(applyBo.getApplyId());
                    message.setType(MessageConstant.KCBM);
//            message.setContent("<a href=\""+MessageConstant.ABCUC_URL+"/orderback/exchange/"+oe.getId()+"/"+order.getOrderNo()+"\">"+MessageConstant.EXCHANGE_CHECK_ADOPT+"</a>");
                    message.setContent("您已报名成功"+curriculumTitle+"课程培训，请于"+time+"时间登录系统准时参加，感谢您的参与！");
                    message.setUserId(applyBo.getUserId());
                    messageSendUtil.sendMessage(message, request);
                }else if("face".equals(curriculum.getTeachingMethod())){
                    time = sdf.format(curriculum.getSignTimeBegin());
                    site = curriculum.getTrainSite();
                    Message message = new Message();
                    message.setBusinessId(applyBo.getApplyId());
                    message.setType(MessageConstant.KCBM);
//            message.setContent("<a href=\""+MessageConstant.ABCUC_URL+"/orderback/exchange/"+oe.getId()+"/"+order.getOrderNo()+"\">"+MessageConstant.EXCHANGE_CHECK_ADOPT+"</a>");
                    message.setContent("您已报名成功"+curriculumTitle+"课程培训，请于"+time+"时间"+site+"地点系统准时参加，感谢您的参与！");
                    message.setUserId(applyBo.getUserId());
                    messageSendUtil.sendMessage(message, request);
                }
            }



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
    public CurriculumApplyBo update(CurriculumApplyBo applyBo,HttpServletRequest request) {
        //更新课程报名签到信息
        CurriculumApply apply = new CurriculumApply();
        try {
            JSONObject jsonStu = JSONObject.fromObject(applyBo);
            LOGGER.info("更新课程报名签到信息:{}", jsonStu.toString());
            applyBo.setSignTime(new Date());
            BeanUtils.copyProperties(applyBo, apply);
            applyMapper.updateByPrimaryKeySelective(apply);

            Curriculum curriculum = curriculumRoMapper.selectByPrimaryKey(applyBo.getCurriculumId());

            String curriculumTitle = "";
            if(curriculum !=null){
                curriculumTitle = curriculum.getTitle();
            }
            Message message = new Message();
            message.setBusinessId(applyBo.getApplyId());
            message.setType(MessageConstant.KCQD);
//            message.setContent("<a href=\""+MessageConstant.ABCUC_URL+"/orderback/exchange/"+oe.getId()+"/"+order.getOrderNo()+"\">"+MessageConstant.EXCHANGE_CHECK_ADOPT+"</a>");
            message.setContent("您已成功签到"+curriculumTitle+"课程培训，赶紧参加培训吧！");
            message.setUserId(applyBo.getUserId());
            messageSendUtil.sendMessage(message, request);


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
