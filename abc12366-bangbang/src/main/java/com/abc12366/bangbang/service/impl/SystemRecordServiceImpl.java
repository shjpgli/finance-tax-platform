package com.abc12366.bangbang.service.impl;

import com.abc12366.bangbang.mapper.db1.SystemRecordMapper;
import com.abc12366.bangbang.mapper.db2.SystemRecordRoMapper;
import com.abc12366.bangbang.model.SystemRecord;
import com.abc12366.bangbang.model.bo.SystemRecordBO;
import com.abc12366.bangbang.model.bo.SystemRecordInsertBO;
import com.abc12366.bangbang.service.SystemRecordService;
import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.model.bo.TableBO;
import com.abc12366.gateway.util.DateUtils;
import com.abc12366.gateway.util.Utils;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private SystemRecordRoMapper systemRecordRoMapper;

    @Autowired
    private SystemRecordMapper systemRecordMapper;

    @Override
    public List<SystemRecordBO> selectList(Map<String, String> map, int page, int size) {
        // 如果不存在表，创建当天的用户日志表
        TableBO tableBO = new TableBO.Builder().yyyyMMdd(map.get("yyyyMMdd")).build();
        systemRecordMapper.create(tableBO);

        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return systemRecordRoMapper.selectList(map);
    }

    @Override
    public SystemRecordBO selectOne(SystemRecord systemRecord) {
        return systemRecordRoMapper.selectOne(systemRecord);
    }

    /**
     * 异步新增；如果规则代码有效，则新增用户经验值
     *
     * @param systemRecordInsertBO 日志BO
     * @return CompletableFuture 系统日志对象
     */
    @Async
    @Override
    public CompletableFuture<SystemRecordBO> insert(SystemRecordInsertBO systemRecordInsertBO) {
        if (systemRecordInsertBO == null) {
            LOGGER.warn("新增失败，参数：" + null);
            throw new ServiceException(4101);
        }

        // 如果不存在表，创建当天的用户日志表
        String today = DateUtils.getDataString();
        TableBO tableBO = new TableBO.Builder().yyyyMMdd(today).build();
        systemRecordMapper.create(tableBO);

        SystemRecord systemRecord = new SystemRecord();
        BeanUtils.copyProperties(systemRecordInsertBO, systemRecord);
        Date date = new Date();
        systemRecord.setYyyyMMdd(today);
        systemRecord.setId(Utils.uuid());
        systemRecord.setCreateTime(date);
        systemRecordSetDate(systemRecord);
        if (systemRecord.getUserId() != null && systemRecord.getSessionId() != null) {
            try {
                //  要搜索一个 sessionId 相等 UserId相等  browseDate 要当天
                List<SystemRecordBO> list = systemRecordRoMapper.findStay(systemRecord);
                if (list != null && list.size() > 0) {
                    SystemRecordBO systemRecordBO = list.get(0);
                    systemRecordBO.setYyyyMMdd(today);
                    Long oldTime = systemRecordBO.getCreateTime().getTime();
                    Long newTime = date.getTime();
                    systemRecordBO.setStayLong((int) (newTime - oldTime));
                    systemRecordMapper.updateStay(systemRecordBO);
                }
            } catch (Exception e) {
                LOGGER.error("错误：" + e.getMessage());
            }
        }
        int result = systemRecordMapper.insert(systemRecord);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + systemRecord);
            throw new ServiceException(4101);
        }

        // todo 如果规则代码有效，则新增用户经验值
        if (!StringUtils.isEmpty(systemRecord.getRuleCode()) && !StringUtils.isEmpty(systemRecord.getUserId())) {

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
