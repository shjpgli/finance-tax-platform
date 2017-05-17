package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.PointsLogMapper;
import com.abc12366.uc.mapper.db2.PointsLogRoMapper;
import com.abc12366.uc.model.PointsLog;
import com.abc12366.uc.model.bo.PointsLogBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-15 10:18 PM
 * @since 2.0.0
 */
@Service
public class PointsLogServiceImpl implements PointsLogService{
    private static final Logger LOGGER = LoggerFactory.getLogger(PointsLogServiceImpl.class);

    @Autowired
    private PointsLogMapper pointsLogMapper;

    @Autowired
    private PointsLogRoMapper pointsLogRoMapper;

    @Override
    public List<PointsLogBO> selectList(Map map) {
        List<PointsLogBO> pointsLogBOList = pointsLogRoMapper.selectList(map);
        return pointsLogBOList;
    }

    @Override
    public PointsLogBO insert(PointsLogBO pointsLogBO) {
        if(pointsLogBO==null){
            LOGGER.warn("新增失败，参数：{}", pointsLogBO.toString());
            throw new ServiceException(4101);
        }
        PointsLog pointsLog = new PointsLog();
        BeanUtils.copyProperties(pointsLogBO, pointsLog);
        pointsLog.setId(Utils.uuid());
        pointsLog.setCreateTime(new Date());

        int result = pointsLogMapper.insert(pointsLog);
        if(result<1){
            LOGGER.warn("新增失败，参数：{}", pointsLog.toString());
            throw new ServiceException(4101);
        }
        PointsLogBO pointsLogBOReturn = new PointsLogBO();
        BeanUtils.copyProperties(pointsLog, pointsLogBOReturn);
        return pointsLogBOReturn;
    }
}
