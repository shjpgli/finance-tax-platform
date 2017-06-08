package com.abc12366.message.service;

import com.abc12366.message.model.bo.*;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-06
 * Time: 10:45
 */
public interface SmsLogService {
    int smsVerifyCodeInsert(Object object1, Object object2);

    int smsOpsLogInsert(SendTemplateParam sendTemplateParam, SendTemplateResponseBO verifyCodeResponseBO);

    int smsOpsInsert(SmsOps smsOps);

    int smsOpsUpdate(String sendid, QueryStatusResponseBO queryStatusResponseBO);
}
