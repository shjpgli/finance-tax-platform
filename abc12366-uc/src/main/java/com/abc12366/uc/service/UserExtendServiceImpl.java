package com.abc12366.uc.service;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.uc.mapper.db1.UserExtendMapper;
import com.abc12366.uc.mapper.db2.UserExtendRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.UserExtend;
import com.abc12366.uc.model.bo.UserExtendBO;
import com.abc12366.uc.model.bo.UserExtendUpdateBO;
import com.abc12366.uc.util.UCConstant;
import com.abc12366.uc.util.UserUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;

/**
 * @author liuguiyao
 * @create 2017-05-05 10:08 AM
 * @since 1.0.0
 */
@Service
public class UserExtendServiceImpl implements UserExtendService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserExtendService.class);

    @Autowired
    private UserExtendMapper userExtendMapper;

    @Autowired
    private UserExtendRoMapper userExtendRoMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private UserBindService userBindService;

    @Autowired
    private TodoTaskService todoTaskService;

    @Override
    public UserExtendBO selectOne(String userId) {
        LOGGER.info("{}", userId);
        UserExtend userExtend = userExtendRoMapper.selectOne(userId);
        if (userExtend == null) {
            return null;
        }
        if(userExtend.getValidStatus().equals(UCConstant.USER_REALNAME_VALIDATED)){
            //首次实名认证任务埋点
            todoTaskService.doTask(userId, UCConstant.SYS_TASK_FIRST_REALNAME_VALIDATE_ID);
        }
        UserExtendBO userExtendBO = new UserExtendBO();
        BeanUtils.copyProperties(userExtend, userExtendBO);
        LOGGER.info("{}", userExtendBO);
        return userExtendBO;
    }

    @Transactional("db1TxManager")
    @Override
    public UserExtendBO insert(UserExtendBO userExtendBO, HttpServletRequest request) {
        if (userExtendBO == null) {
            LOGGER.warn("新增失败，参数：{}" + null);
            throw new ServiceException(4101);
        }
        LOGGER.info("{}", userExtendBO);
        if (userExtendBO.getUserId() != null && !userExtendBO.getUserId().equals("")) {
            UserExtend userExtend = userExtendRoMapper.selectOne(userExtendBO.getUserId());
            if (userExtend != null) {
                UserExtendBO userExtendOld = new UserExtendBO();
                BeanUtils.copyProperties(userExtend, userExtendOld);
                return userExtendOld;
            }
            userExtend = new UserExtend();
            BeanUtils.copyProperties(userExtendBO, userExtend);
            userExtend.setCreateTime(new Date());
            userExtend.setLastUpdate(new Date());
            if (userExtendBO.getValidStatus() == null || userExtendBO.getValidStatus().trim().equals("")) {
                userExtend.setValidStatus("0");
            }
            //调用电子税局实名认证查询接口查询用户实名认证情况，如果已实名，则财税专家直接将该用户置为已实名
            if(userBindService.isRealNameValidatedDzsj(userExtendBO.getIdcard(), userExtendBO.getRealName(), request)){
                userExtend.setValidStatus("2");
            }
            int result = userExtendMapper.insert(userExtend);
            if (result != 1) {
                LOGGER.warn("新增失败，参数：{}" + userExtend.toString());
                throw new ServiceException(4112);
            }

            UserExtendBO userExtendBO1 = new UserExtendBO();
            BeanUtils.copyProperties(userExtend, userExtendBO1);
            LOGGER.info("{}", userExtendBO1);
            return userExtendBO1;
        }
        return null;
    }

    @Override
    public UserExtendBO delete(String userId) {
        LOGGER.info("{}", userId);
        UserExtend userExtend = userExtendRoMapper.selectOne(userId);
        if (userExtend == null) {
            LOGGER.warn("删除失败，参数：{}" + userId);
            throw new ServiceException(4103);
        }
        int result = userExtendMapper.delete(userId);
        if (result != 1) {
            LOGGER.warn("删除失败，参数：{}" + userId);
            throw new ServiceException(4103);
        }
        UserExtendBO userExtendBO = new UserExtendBO();
        BeanUtils.copyProperties(userExtend, userExtendBO);
        LOGGER.info("{}", userExtendBO);
        return userExtendBO;
    }

    @Override
    public UserExtendBO update(UserExtendUpdateBO userExtendUpdateBO, HttpServletRequest request) throws IOException {
        LOGGER.info("{}", userExtendUpdateBO);
        if (userExtendUpdateBO == null) {
            LOGGER.warn("修改失败，参数：{}" + null);
            throw new ServiceException(4102);
        }
        //不允许修改非当前登录用户数据
        if(!userExtendUpdateBO.getUserId().trim().equals(UserUtil.getUserId(request).trim())){
            throw new ServiceException(4190);
        }
        if (!userExtendUpdateBO.getUserId().equals("")) {
            UserExtend userExtend = userExtendRoMapper.selectOne(userExtendUpdateBO.getUserId());
            if (userExtend == null) {
                UserExtendBO userExtendBO = new UserExtendBO();
                BeanUtils.copyProperties(userExtendUpdateBO, userExtendBO);
                UserExtendBO userExtendBOReturn = insert(userExtendBO, request);
                return userExtendBOReturn;
            }
            UserExtend userExtendSecond = new UserExtend();
            BeanUtils.copyProperties(userExtendUpdateBO, userExtendSecond);
            userExtendSecond.setLastUpdate(new Date());
            //调用电子税局实名认证查询接口查询用户实名认证情况，如果已实名，则财税专家直接将该用户置为已实名
            if(userBindService.isRealNameValidatedDzsj(userExtendUpdateBO.getIdcard(),userExtendUpdateBO.getRealName(), request)){
                userExtendSecond.setValidStatus("2");
            }
            int result = userExtendMapper.update(userExtendSecond);
            UserExtendBO userExtendBO = new UserExtendBO();

            if (result < 1) {
                LOGGER.warn("修改失败，参数：{}" + userExtendUpdateBO);
                throw new ServiceException(4102);
            }
            UserExtend userExtend2 = userExtendRoMapper.selectOne(userExtendSecond.getUserId());
            BeanUtils.copyProperties(userExtend2, userExtendBO);
            LOGGER.info("{}", userExtendBO);
            return userExtendBO;
        }
        return null;
    }


}
