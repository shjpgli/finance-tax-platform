package com.abc12366.uc.job.order;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.uc.service.OrderExchangeService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author lizhongwei
 * @create 2017-08-07 12:21 PM
 * @since 1.0.0
 */
public class OrderExchangeReceiptJob implements Job{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderExchangeReceiptJob.class);

    @Autowired
    private static OrderExchangeService orderExchangeService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("JobName: {}", context.getJobDetail().getKey().getName());
        try {
            initService();
            orderExchangeService.automaticReceipt();
        } catch (Exception e) {
            LOGGER.error("{}", e.getMessage());
        }
    }

    public static void initService(){
        synchronized(OrderExchangeReceiptJob.class){
            orderExchangeService=(OrderExchangeService) SpringCtxHolder.getApplicationContext().getBean("orderExchangeService");
        }
    }
}
