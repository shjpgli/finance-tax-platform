package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.SystemRecordMapper;
import com.abc12366.bangbang.mapper.db2.SystemRecordRoMapper;
import com.abc12366.bangbang.model.SystemRecord;
import com.abc12366.bangbang.model.bo.SystemRecordBO;
import com.abc12366.bangbang.model.bo.SystemRecordInsertBO;
import com.abc12366.bangbang.service.SystemRecordService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author lingsuzhi <554600654@qq.com.com>
 * @create 2017-08-16
 */
@Service
public class SystemRecordServiceImpl implements SystemRecordService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemRecordServiceImpl.class);

    @Autowired
    private SystemRecordRoMapper SystemRecordRoMapper;

    @Autowired
    private SystemRecordMapper SystemRecordMapper;

    @Override
    public List<SystemRecordBO> selectList(Map map) {
        return SystemRecordRoMapper.selectList(map);
    }

    @Override
    public SystemRecordBO selectOne(String id) {
        return SystemRecordRoMapper.selectOne(id);
    }

    /**
     * 异步新增
     */
    @Async
    @Override
    public CompletableFuture<SystemRecordBO> insert(SystemRecordInsertBO systemRecordInsertBO) {
        if (systemRecordInsertBO == null) {
            LOGGER.warn("新增失败，参数：" + null);
            throw new ServiceException(4101);
        }
        SystemRecord systemRecord = new SystemRecord();
        BeanUtils.copyProperties(systemRecordInsertBO, systemRecord);
        Date date = new Date();
        systemRecord.setId(Utils.uuid());
        systemRecord.setCreateTime(date);
        systemRecordSetDate(systemRecord);
        if (systemRecord.getUserId() != null && systemRecord.getSessionId() != null) {
            try {
                //  要搜索一个 sessionId 相等 UserId相等  browseDate 要当天
                List<SystemRecordBO> list = SystemRecordRoMapper.findStay(systemRecord);
                if (list != null && list.size() > 0) {
                    SystemRecordBO systemRecordBO = list.get(0);
                    Long oldTime = systemRecordBO.getCreateTime().getTime();
                    Long newTime = date.getTime();
                    systemRecordBO.setStayLong((int) (newTime - oldTime));
                    SystemRecordMapper.updateStay(systemRecordBO);
                }
            } catch (Exception e) {
                LOGGER.error("错误：" + e.getMessage());
            }
        }
        int result = SystemRecordMapper.insert(systemRecord);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + systemRecord);
            throw new ServiceException(4101);
        }

        SystemRecordBO systemRecordBOReturn = new SystemRecordBO();
        BeanUtils.copyProperties(systemRecord, systemRecordBOReturn);

        return CompletableFuture.completedFuture(systemRecordBOReturn);
    }

    /**
     * 年、月、日。。。是根据浏览日期算出来的，新增的时候不需要用户传过来
     */
    private void systemRecordSetDate(SystemRecord systemRecord) {
        Calendar now = Calendar.getInstance();
        now.setTime(new Date());
        systemRecord.setWeek(String.valueOf(now.get(Calendar.DAY_OF_WEEK) - 1));
        systemRecord.setYear(String.valueOf(now.get(Calendar.YEAR)));
        systemRecord.setMonth(String.valueOf(now.get(Calendar.MONTH) + 1));
        systemRecord.setDay(String.valueOf(now.get(Calendar.DAY_OF_MONTH)));
        systemRecord.setHour(String.valueOf(now.get(Calendar.HOUR_OF_DAY)));
        systemRecord.setMinute(String.valueOf(now.get(Calendar.MINUTE)));
    }
}
