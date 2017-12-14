package com.abc12366.bangbang.job;

import com.abc12366.bangbang.service.SystemRecordService;
import com.abc12366.gateway.util.DateUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * 软件用户行为分析定时器
 * @author lizhongwei
 * @create 2017-12-12 12:21 PM
 * @since 1.0.0
 */
public class SystemRecordJob implements Job{

    private static final Logger LOGGER = LoggerFactory.getLogger(SystemRecordJob.class);

    @Autowired
    private static SystemRecordService systemRecordService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Map<String,Object> map = new HashMap<>();
        //获取上一天时间
        map.put("yyyyMMdd", DateUtils.getDateToYYYYMMDD(-1));
        LOGGER.info("SystemRecordJob: {}", context.getJobDetail().getKey().getName());
        systemRecordService.autoRecordStatis(map);
    }

}
