package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.jrxt.model.util.DateUtil;
import com.abc12366.uc.mapper.db1.ExperienceMapper;
import com.abc12366.uc.mapper.db2.ExperienceLevelRoMapper;
import com.abc12366.uc.mapper.db2.ExperienceLogRoMapper;
import com.abc12366.uc.mapper.db2.ExperienceRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.ExperienceRule;
import com.abc12366.uc.model.PrivilegeItem;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.*;
import com.abc12366.uc.service.ExperienceLogService;
import com.abc12366.uc.service.ExperienceRuleService;
import com.abc12366.uc.service.ExperienceService;
import com.abc12366.uc.service.PrivilegeItemService;
import com.abc12366.uc.util.DateUtils;
import com.abc12366.uc.util.UCConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:33
 */
@Service
public class ExperienceServiceImpl implements ExperienceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceServiceImpl.class);

    @Autowired
    private ExperienceRoMapper experienceRoMapper;

    @Autowired
    private ExperienceMapper experienceMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private ExperienceLogService experienceLogService;

    @Autowired
    private ExperienceLogRoMapper experienceLogRoMapper;

    @Autowired
    ExperienceLevelRoMapper experienceLevelRoMapper;

    @Autowired
    private ExperienceRuleService experienceRuleService;

    @Autowired
    private PrivilegeItemService privilegeItemService;

    @Override
    public MyExperienceBO getMyExperience(String userId) {
        LOGGER.info("{}", userId);
        return experienceRoMapper.getMyExperience(userId);
    }

    @Transactional("db1TxManager")
    @Override
    public List<ExpCodex> codex(String uexpruleId, List<ExpCodex> codexList) {
        LOGGER.info("{}:{}", uexpruleId, codexList);
        //先批量删除
        experienceMapper.deleteByRuleId(uexpruleId);

        //再批量新增
        List<ExpCodex> expCodexList = new ArrayList<>();
        for (int i = 0; i < codexList.size(); i++) {
            ExpCodex codex = new ExpCodex();
            BeanUtils.copyProperties(codexList.get(i), codex);
            codex.setId(Utils.uuid());
            experienceMapper.insert(codex);
            expCodexList.add(codex);
        }
        return expCodexList;
    }

    @Override
    public int deleteCodex(String id) {
        experienceMapper.delete(id);
        return 1;
    }

    @Override
    public List<ExpCodex> codexList() {
        return experienceRoMapper.codexList();
    }

    @Override
    public void calculate(ExpCalculateBO expCalculateBO) {
        ExperienceRuleBO experienceRule = experienceRuleService.selectValidOne(expCalculateBO.getRuleId());
        if (experienceRule == null) {
            return;
        }
        //查看获取经验值次数是否允许范围内
        Date startTime = new Date();
        Date endTime = new Date();


        String period = experienceRule.getPeriod().toUpperCase();

        if (!period.equals("D") && !period.equals("M") && !period.equals("Y") && !period.equals("A")) {
            return;
        }
        if (!period.trim().equals("") && (period.equals("D") || period.equals("M") || period.equals("Y"))) {
            switch (period) {
                case "D":
                    startTime = DateUtils.getFirstHourOfDay();
                    endTime = DateUtils.getFirstHourOfLastDay();
                    break;
                case "M":
                    startTime = DateUtils.getFirstDayOfMonth();
                    endTime = DateUtils.getFirstDayOfLastMonth();
                    break;
                case "Y":
                    startTime = DateUtils.getFirstMonthOfYear();
                    endTime = DateUtils.getFirstMonthOfLastYear();
                    break;
            }
            ExpComputeLogParam param = new ExpComputeLogParam();
            param.setUserId(expCalculateBO.getUserId());
            param.setTimeType(period);
            //param.setUexpCodexId(codex.getId());
            param.setStarTime(startTime);
            param.setEndTime(endTime);
            param.setRuleId(expCalculateBO.getRuleId());
            List<ExpComputeLog> computeLogList = experienceRoMapper.selectCalculateLog(param);
            if (computeLogList != null && computeLogList.size() >= experienceRule.getDegree()) {
                return;
            }
        }

        //经验值日志,同时修改用户经验值
        User user = userRoMapper.selectValidOne(expCalculateBO.getUserId());
        if (user == null) {
//            return;
            throw new ServiceException(4018);
        }

        ExperienceLogBO experienceLog = new ExperienceLogBO();
        experienceLog.setUserId(expCalculateBO.getUserId());
        experienceLog.setRuleId(expCalculateBO.getRuleId());
        if (experienceRule.getExp() < 0) {
            experienceLog.setIncome(0);
            experienceLog.setOutgo(-experienceRule.getExp());
        } else {
            experienceLog.setIncome(experienceRule.getExp());
            experienceLog.setOutgo(0);
        }
        experienceLogService.insert(experienceLog);

        //记录用户特定行为升级用户等级日志
        ExpComputeLog expComputeLog = new ExpComputeLog();
        expComputeLog.setId(Utils.uuid());
        expComputeLog.setUserId(expCalculateBO.getUserId());
        //expComputeLog.setUexpCodexId(codex.getId());
        expComputeLog.setTimeType(experienceRule.getPeriod().toUpperCase());
        expComputeLog.setCreateTime(new Date());
        expComputeLog.setRuleId(expCalculateBO.getRuleId());
        experienceMapper.insertComputeLog(expComputeLog);
    }

    @Override
    public void compute(ExpComputeBO expComputeBO) {
        //experienceRuleService.selectOne(UCConstant.)
        List<ExpCodex> expCodexes = experienceRoMapper.selectOne(expComputeBO);
        if (expCodexes == null || expCodexes.size() < 1) {
//            return;
            throw new ServiceException(4853);
        }
        ExpCodex codex = expCodexes.get(0);
        if (codex.getUexp() == null || codex.getUexp().toString().equals("")) {
//            return;
            throw new ServiceException(4854);
        }

        //查看获取经验值次数是否允许范围内
        Date startTime = new Date();
        Date endTime = new Date();


        String period = codex.getPeriod().toUpperCase();
        if (!period.trim().equals("") && (period.equals("D") || period.equals("M") || period.equals("Y"))) {
            switch (codex.getPeriod().toUpperCase()) {
                case "D":
                    startTime = DateUtils.getFirstHourOfDay();
                    endTime = DateUtils.getFirstHourOfLastDay();
                    break;
                case "M":
                    startTime = DateUtils.getFirstDayOfMonth();
                    endTime = DateUtils.getFirstDayOfLastMonth();
                    break;
                case "Y":
                    startTime = DateUtils.getFirstMonthOfYear();
                    endTime = DateUtils.getFirstMonthOfLastYear();
                    break;
            }
            ExpComputeLogParam param = new ExpComputeLogParam();
            param.setUserId(expComputeBO.getUserId());
            param.setTimeType(codex.getPeriod().toUpperCase());
            param.setUexpCodexId(codex.getId());
            param.setStarTime(startTime);
            param.setEndTime(endTime);
            List<ExpComputeLog> computeLogList = experienceRoMapper.selectExpComputeLog(param);
            if (computeLogList != null && computeLogList.size() >= codex.getDegree()) {
                return;
            }
        }

        //经验值日志,同时修改用户经验值
        User user = userRoMapper.selectOne(expComputeBO.getUserId());
        if (user == null) {
//            return;
            throw new ServiceException(4018);
        }

        ExperienceLogBO experienceLog = new ExperienceLogBO();
        experienceLog.setUserId(expComputeBO.getUserId());
        experienceLog.setRuleId(codex.getUexpruleId());
        if (codex.getUexp() < 0) {
            experienceLog.setIncome(0);
            experienceLog.setOutgo(-codex.getUexp());
        } else {
            //会员权限埋点（经验值加成）
            float factor = 1.0F;
            PrivilegeItem privilegeItem = privilegeItemService.selecOneByUser(expComputeBO.getUserId());
            if (privilegeItem != null && privilegeItem.getHyjyzjc() > 0) {
                factor = privilegeItem.getHyjyzjc();
            }
            experienceLog.setIncome((int) (codex.getUexp()*factor));
            experienceLog.setOutgo(0);
        }
        experienceLogService.insert(experienceLog);

        //记录用户特定行为升级用户等级日志
        ExpComputeLog expComputeLog = new ExpComputeLog();
        expComputeLog.setId(Utils.uuid());
        expComputeLog.setUserId(expComputeBO.getUserId());
        expComputeLog.setUexpCodexId(codex.getId());
        expComputeLog.setTimeType(codex.getPeriod().toUpperCase());
        expComputeLog.setCreateTime(new Date());
        experienceMapper.insertComputeLog(expComputeLog);

    }
}
