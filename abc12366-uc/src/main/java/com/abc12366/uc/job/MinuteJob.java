package com.abc12366.uc.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-07 12:21 PM
 * @since 1.0.0
 */
public class MinuteJob implements Job{

    private static final Logger LOGGER = LoggerFactory.getLogger(MinuteJob.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("JobName: {}", context.getJobDetail().getKey().getName());
    }
}
