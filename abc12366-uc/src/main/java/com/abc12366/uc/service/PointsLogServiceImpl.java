package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.PointsLogMapper;
import com.abc12366.uc.mapper.db1.UserMapper;
import com.abc12366.uc.mapper.db2.PointsLogRoMapper;
import com.abc12366.uc.mapper.db2.UserRoMapper;
import com.abc12366.uc.model.PointsLog;
import com.abc12366.uc.model.User;
import com.abc12366.uc.model.bo.PointsLogBO;
import com.abc12366.uc.model.bo.PointsLogUcBO;
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
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-07-25
 * Time: 16:22
 */
@Service
public class PointsLogServiceImpl implements PointsLogService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PointsLogServiceImpl.class);

    @Autowired
    private PointsLogMapper pointsLogMapper;

    @Autowired
    private PointsLogRoMapper pointsLogRoMapper;

    @Autowired
    private UserRoMapper userRoMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<PointsLogBO> selectList(Map map) {
        return pointsLogRoMapper.selectList(map);
    }

    @Transactional("db1TxManager")
    @Override
    public PointsLogBO insert(PointsLogBO pointsLogBO) {
        if(pointsLogBO==null){
            LOGGER.warn("新增失败，参数：{}" + null);
            throw new ServiceException(4101);
        }
        User user = userRoMapper.selectOne(pointsLogBO.getUserId());
        if (user == null) {
            LOGGER.warn("新增失败,userId为不存在用户的id,参数为：userId=" + pointsLogBO.getUserId());
            throw new ServiceException(4101);
        }
        //可用积分=上一次的可用积分+|-本次收入|支出
        int usablePoints = user.getPoints() + pointsLogBO.getIncome()-pointsLogBO.getOutgo();
        //uc_user的points字段和uc_point_log的usablePoints字段都要更新
        user.setPoints(usablePoints);
        int userUpdateResult = userMapper.update(user);
        if (userUpdateResult != 1) {
            LOGGER.warn("新增失败,更新用户表积分失败,参数为：userId=" + pointsLogBO.getUserId());
            throw new ServiceException(4101);
        }

        PointsLog pointsLog = new PointsLog();
        BeanUtils.copyProperties(pointsLogBO, pointsLog);
        pointsLog.setId(Utils.uuid());
        pointsLog.setCreateTime(new Date());
        pointsLog.setUsablePoints(usablePoints);
        int result = pointsLogMapper.insert(pointsLog);
        if(result<1){
            LOGGER.warn("新增失败，参数：{}", pointsLog.toString());
            throw new ServiceException(4101);
        }

        PointsLogBO pointsLogBOReturn = new PointsLogBO();
        BeanUtils.copyProperties(pointsLog, pointsLogBOReturn);
        return pointsLogBOReturn;
    }

    @Override
    public List<PointsLogUcBO> selectListByUser(Map<String, Object> map) {
        return pointsLogRoMapper.selectListByUser(map);
    }
}
