package com.abc12366.message.mapper.db1;

import com.abc12366.message.model.bo.SmsOps;
import com.abc12366.message.model.bo.SmsOpsLog;
import com.abc12366.message.model.bo.SmsVerifyCode;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-06
 * Time: 16:08
 */
public interface SmsLogMapper {
    int smsVerifyCodeInsert(SmsVerifyCode smsVerifyCode);

    int smsOpsLogInsert(SmsOpsLog smsOpsLog);

    int smsOpsInsert(SmsOps smsOps);

    int smsOpsUpdate(SmsOps smsOps);
}
