package com.abc12366.uc.job.user;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.uc.service.UserService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lizhongwei
 * @create 2017-09-27 12:21 PM
 * @since 1.0.0
 */
public class UserVipJob implements Job{

    private static final Logger LOGGER = LoggerFactory.getLogger(UserVipJob.class);

    @Autowired
    private static UserService userService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("UserVipJob: {}", context.getJobDetail().getKey().getName());
        initService();
        userService.automaticUserCancel();
    }

    public static void initService(){
        synchronized(UserVipJob.class){
            userService=(UserService) SpringCtxHolder.getApplicationContext().getBean("userService");
        }
    }


}
