package com.abc12366.uc.service;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.mapper.db1.ExperienceLogMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.ExperienceLogRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.ExperienceLog;
import com.abc12366.uc.model.PrivilegeItem;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.ExpLogUcBO;
import com.abc12366.uc.model.bo.ExperienceLogBO;
import com.abc12366.uc.model.bo.ExperienceLogQueryBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 15:07
 */
@Service
public class ExperienceLogServiceImpl implements ExperienceLogService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExperienceLogServiceImpl.class);

    @Autowired
    private ExperienceLogRoMapper experienceLogRoMapper;

    @Autowired
    private ExperienceLogMapper experienceLogMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PrivilegeItemService privilegeItemService;

    @Transactional("db1TxManager")
    @Override
    public ExperienceLogQueryBO insert(ExperienceLogBO experienceLogBO) {
        if (experienceLogBO == null) {
            LOGGER.warn("新增失败,入参为空,参数为：" + null);
            throw new ServiceException(4101);
        }

        User user = userRoMapper.selectOne(experienceLogBO.getUserId());
        if (user == null) {
            LOGGER.warn("新增失败,userId为不存在用户的id,参数为：userId=" + experienceLogBO.getUserId());
            throw new ServiceException(4101);
        }
        //可用经验值=上一次的可用经验值+|-本次收入|支出
        int usableExp = user.getExp() + experienceLogBO.getIncome() - experienceLogBO.getOutgo();

        //会员权限埋点（经验值加成）
        if (experienceLogBO.getIncome() > 0 && experienceLogBO.getIncome() > experienceLogBO.getOutgo()) {
            PrivilegeItem privilegeItem = privilegeItemService.selecOneByUser(user.getId());
            if (privilegeItem != null && privilegeItem.getHyjyzjc() > 0) {
                usableExp = (int) (usableExp * privilegeItem.getHyjyzjc());
            }

            experienceLogBO.setIncome((int) (experienceLogBO.getIncome() * privilegeItem.getHyjyzjc()));
        }

        //uc_user的exp字段和uc_uexp_log的usableExp字段都要更新
        user.setExp(usableExp);
        user.setLastUpdate(new Date());
        int userUpdateResult = userMapper.update(user);
        if (userUpdateResult != 1) {
            LOGGER.warn("新增失败,更新用户表经验值失败,参数为：userId=" + experienceLogBO.getUserId());
            throw new ServiceException(4101);
        }

        ExperienceLog experienceLog = new ExperienceLog();
        BeanUtils.copyProperties(experienceLogBO, experienceLog);
        experienceLog.setId(Utils.uuid());
        experienceLog.setCreateTime(new Date());
        experienceLog.setUsableExp(usableExp);
        int result = experienceLogMapper.insert(experienceLog);
        if (result != 1) {
            LOGGER.warn("新增失败,参数为：" + experienceLog);
            throw new ServiceException(4101);
        }

        ExperienceLogQueryBO experienceLogReturn = new ExperienceLogQueryBO();
        BeanUtils.copyProperties(experienceLog, experienceLogReturn);
        return experienceLogReturn;
    }

    @Override
    public List<ExperienceLogQueryBO> selectList(Map map) {
        return experienceLogRoMapper.selectList(map);
    }

    @Override
    public List<ExpLogUcBO> selectListByUser(Map<String, Object> map) {
        return experienceLogRoMapper.selectListByUser(map);
    }
}
