package com.abc12366.uc.job.dzsb;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.uc.webservice.AcceptClient;

/**
 * 缴税信息提醒
 * @author zhushuai 2017-11-9
 *
 */
public class JsxxRemindJob implements Job{
	private static final Logger LOGGER = LoggerFactory.getLogger(JsxxRemindJob.class);
	
	private AcceptClient client;
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		client=(AcceptClient) SpringCtxHolder.getApplicationContext().getBean("client");
	}

}
