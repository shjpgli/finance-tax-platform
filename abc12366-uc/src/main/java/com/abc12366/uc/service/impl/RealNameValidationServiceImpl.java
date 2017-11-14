package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.uc.mapper.db1.UserExtendMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendListBO;
import com.abc12366.uc.model.bo.UserExtendUpdateBO;
import com.abc12366.uc.service.RealNameValidationService;
import com.abc12366.uc.service.TodoTaskService;
import com.abc12366.gateway.util.UCConstant;
import com.abc12366.uc.service.UserFeedbackMsgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com>
 * Date: 2017-07-04
 * Time: 11:07
 */
@Service
public class RealNameValidationServiceImpl implements RealNameValidationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RealNameValidationServiceImpl.class);

    @Autowired
    private UserExtendRoMapper userExtendRoMapper;

    @Autowired
    private UserExtendMapper userExtendMapper;

    @Autowired
    private TodoTaskService todoTaskService;

    @Autowired
    private UserFeedbackMsgService userFeedbackMsgService;

    @Override
    public List<UserExtendListBO> selectList(Map map) {
        LOGGER.info("{}", map);
        return userExtendRoMapper.selectList(map);
    }

    @Override
    public UserExtendBO validate(String userId, String validStatus, UserExtendUpdateBO userExtendUpdateBO) throws
            ParseException {
        LOGGER.info("{}:{}:{}", userId, validStatus, userExtendUpdateBO);
        UserExtend userExtend = userExtendRoMapper.selectOne(userId);
        if (userExtend == null) {
            throw new ServiceException(4701);
        }
        UserExtend userExtendUpdate = new UserExtend();
        Date startTime = new Date();

        if (userExtendUpdateBO != null) {
            BeanUtils.copyProperties(userExtendUpdateBO, userExtendUpdate);
        }
        userExtendUpdate.setUserId(userId);
        userExtendUpdate.setLastUpdate(startTime);
        userExtendUpdate.setStartTime(startTime);
        userExtendUpdate.setEndTime(getSpecifiedDate("2099-12-30 23:59:59"));
        userExtendUpdate.setValidStatus(validStatus);
        if(validStatus.equals(UCConstant.USER_REALNAME_VALIDATED)){
            userExtendUpdate.setValidTime(new Date());
        }
        int result = userExtendMapper.update(userExtendUpdate);
        if (result < 1) {
            throw new ServiceException();
        }

        //首次实名认证任务埋点
        if(validStatus.equals(UCConstant.USER_REALNAME_VALIDATED)){
            todoTaskService.doTask(userId, UCConstant.SYS_TASK_FIRST_REALNAME_VALIDATE_CODE);
        }

        UserExtendBO userExtendBO = new UserExtendBO();
        UserExtend userExtend1 = userExtendRoMapper.selectOne(userExtendUpdate.getUserId());
        BeanUtils.copyProperties(userExtend1, userExtendBO);

        userFeedbackMsgService.realNameValidate(validStatus);
        return userExtendBO;
    }

    @Override
    public Integer selectTodoListCount() {
        return userExtendRoMapper.selectTodoListCount();
    }

    public Date getSpecifiedDate(String date) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.parse(date);
    }
}
