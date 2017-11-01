package com.abc12366.uc.job.user;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.uc.model.weixin.WxRedEnvelop;
import com.abc12366.uc.service.IActivityService;
import com.abc12366.uc.service.UserService;
import com.abc12366.uc.service.impl.ActivityService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 定时同步状态为'已发送，接收状态为空'值的微信红包信息
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-27 12:21 PM
 * @since 1.0.0
 */
public class WxLotteryJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(WxLotteryJob.class);

    @Autowired
    private IActivityService activityService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("WxLotteryJob: {}", context.getJobDetail().getKey().getName());
        activityService = (IActivityService) SpringCtxHolder.getApplicationContext().getBean("activityService");

        WxRedEnvelop redEnvelop = new WxRedEnvelop.Builder()
                .sendStatus("1")
                .receiveStatus("UNUSED")
                .build();
        List<WxRedEnvelop> dataList = activityService.selectRedEnvelopList(redEnvelop, 1, 100);
        for(WxRedEnvelop wre : dataList) {
            LOGGER.info("同步口令ID: {}", wre.getId());
            WxRedEnvelop data = activityService.gethbinfo(wre.getId());
            LOGGER.info("同步完成信息: {}", data);
        }
    }
}