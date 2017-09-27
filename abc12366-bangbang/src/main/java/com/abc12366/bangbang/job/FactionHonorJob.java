package com.abc12366.bangbang.job;

import com.abc12366.bangbang.service.QueFactionService;
import com.abc12366.gateway.component.SpringCtxHolder;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xieyanmao
 * @create 2017-09-27 12:21 PM
 * @since 1.0.0
 */
public class FactionHonorJob implements Job{

    private static final Logger LOGGER = LoggerFactory.getLogger(FactionHonorJob.class);

    @Autowired
    private static QueFactionService factionService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("FactionHonorJob: {}", context.getJobDetail().getKey().getName());
        initService();
        factionService.autoCalculateFactionHonor();
    }

    public static void initService(){
        synchronized(FactionHonorJob.class){
            factionService=(QueFactionService) SpringCtxHolder.getApplicationContext().getBean("factionService");
        }
    }
}
