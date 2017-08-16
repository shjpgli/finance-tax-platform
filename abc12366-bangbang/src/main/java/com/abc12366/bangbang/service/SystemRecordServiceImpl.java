package com.abc12366.bangbang.service;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.bangbang.mapper.db1.SystemRecordMapper;
import com.abc12366.bangbang.mapper.db2.SystemRecordRoMapper;
import com.abc12366.bangbang.model.SystemRecord;
import com.abc12366.bangbang.model.bo.SystemRecordBO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Admin: lingsuzhi <554600654@qq.com.com> Date: 2017-08-16
 */
@Service
public class SystemRecordServiceImpl implements SystemRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemRecordServiceImpl.class);

    @Resource
    private SystemRecordRoMapper SystemRecordRoMapper;

    @Resource
    private SystemRecordMapper SystemRecordMapper;

    @Override
    public List<SystemRecordBO> selectList(Map map) {
        return SystemRecordRoMapper.selectList(map);
    }

    @Override
    public SystemRecordBO selectOne(String id) {
        return SystemRecordRoMapper.selectOne(id);
    }

    //异步新增
    @Async
    @Override
    public SystemRecordBO insert(SystemRecordBO SystemRecordInsertBO) {
        if (SystemRecordInsertBO == null) {
            LOGGER.warn("新增失败，参数：" + null);
            throw new ServiceException(4101);
        }


        SystemRecord SystemRecord = new SystemRecord();
        BeanUtils.copyProperties(SystemRecordInsertBO, SystemRecord);
        Date date = new Date();
        SystemRecord.setId(Utils.uuid());
        SystemRecord.setCreateTime(date);
 
        int result = SystemRecordMapper.insert(SystemRecord);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + SystemRecord);
            throw new ServiceException(4101);
        }
        SystemRecordBO SystemRecordBOReturn = new SystemRecordBO();
        BeanUtils.copyProperties(SystemRecord, SystemRecordBOReturn);
        return SystemRecordBOReturn;
    }
}
