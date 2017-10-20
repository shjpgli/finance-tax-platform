package com.abc12366.uc.job.order;

import com.abc12366.gateway.component.SpringCtxHolder;
import com.abc12366.uc.service.order.OrderService;
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
public class OrderReceiptJob implements Job{

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderReceiptJob.class);

    @Autowired
    private static OrderService orderService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        LOGGER.info("JobName2: {}", context.getJobDetail().getKey().getName());
        initService();
        orderService.automaticReceipt();
    }

    public static void initService(){
        synchronized(OrderReceiptJob.class){
            orderService=(OrderService) SpringCtxHolder.getApplicationContext().getBean("orderService");
        }
    }


}
