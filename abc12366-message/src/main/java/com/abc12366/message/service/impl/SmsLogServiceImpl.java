package com.abc12366.message.service.impl;

import com.abc12366.common.util.Utils;
import com.abc12366.message.mapper.db1.SmsLogMapper;
import com.abc12366.message.model.bo.*;
import com.abc12366.message.service.SmsLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-06-06
 * Time: 15:53
 */
@Service
public class SmsLogServiceImpl implements SmsLogService {

    @Autowired
    private SmsLogMapper smsLogMapper;

    @Override
    public int smsVerifyCodeInsert(Object object1, Object object2) {
        if (object1 instanceof SendCodeParam && object2 instanceof SendCodeResponseBO) {
            SendCodeParam sendCodeParam = (SendCodeParam) object1;
            SendCodeResponseBO sendCodeResponseBO = (SendCodeResponseBO) object2;
            Date date = new Date();
            SmsVerifyCode smsVerifyCode = new SmsVerifyCode();
            smsVerifyCode.setId(Utils.uuid());
            smsVerifyCode.setMobile(sendCodeParam.getMobile());
            smsVerifyCode.setDeviceId(sendCodeParam.getDeviceId());
            smsVerifyCode.setTemplateid(sendCodeParam.getTemplateid().toString());
            smsVerifyCode.setCode(sendCodeResponseBO.getCode());
            smsVerifyCode.setMsg(sendCodeResponseBO.getMsg());
            smsVerifyCode.setObj(sendCodeResponseBO.getObj());
            smsVerifyCode.setCreateTime(date);
            smsVerifyCode.setLastUpdate(date);
            return smsLogMapper.smsVerifyCodeInsert(smsVerifyCode);
        } else if (object1 instanceof VerifyCodeParam && object2 instanceof VerifyCodeResponseBO) {
            VerifyCodeParam sendCodeParam = (VerifyCodeParam) object1;
            VerifyCodeResponseBO sendCodeResponseBO = (VerifyCodeResponseBO) object2;
            Date date = new Date();
            SmsVerifyCode smsVerifyCode = new SmsVerifyCode();
            smsVerifyCode.setId(Utils.uuid());
            smsVerifyCode.setMobile(sendCodeParam.getMobile());
            //smsVerifyCode.setDeviceId(sendCodeParam.getDeviceId());
            //smsVerifyCode.setTemplateId(sendCodeParam.getTemplateid().toString());
            smsVerifyCode.setCode(sendCodeResponseBO.getCode());
            //smsVerifyCode.setMsg(sendCodeResponseBO.getMsg());
            //smsVerifyCode.setObj(sendCodeResponseBO.getObj());
            smsVerifyCode.setCreateTime(date);
            smsVerifyCode.setLastUpdate(date);
            return smsLogMapper.smsVerifyCodeInsert(smsVerifyCode);
        } else {
            return 0;
        }

    }

    @Override
    public int smsOpsLogInsert(SendTemplateParam sendTemplateParam, SendTemplateResponseBO verifyCodeResponseBO) {
        SmsOpsLog smsOpsLog = new SmsOpsLog();
        Date date = new Date();
        smsOpsLog.setId(Utils.uuid());
        smsOpsLog.setTemplateid(sendTemplateParam.getTemplateid().toString());
        smsOpsLog.setMobiles(sendTemplateParam.getMobiles());
        smsOpsLog.setParams(sendTemplateParam.getParams());
        smsOpsLog.setCode(verifyCodeResponseBO.getCode());
        smsOpsLog.setMsg(verifyCodeResponseBO.getMsg());
        smsOpsLog.setObj(verifyCodeResponseBO.getObj());
        smsOpsLog.setCreateTime(date);
        String[] mobilesArr = smsOpsLog.getMobiles().substring(1, smsOpsLog.getMobiles().length() - 1).split(",");
        for (int i = 0; i < mobilesArr.length; i++) {
            SmsOps smsOps = new SmsOps();
            smsOps.setId(Utils.uuid());
            smsOps.setMobile(mobilesArr[i].substring(1, mobilesArr[i].length() - 1));
            smsOps.setTemplateid(smsOpsLog.getTemplateid());
            smsOps.setParams(smsOpsLog.getParams());
            //smsOps.setStatus("");
            //smsOps.setUpdatetime(date);
            smsOps.setCreateTime(date);
            smsOps.setLastUpdate(date);
            smsOps.setSendid(smsOpsLog.getObj());

            smsOpsInsert(smsOps);
        }
        return smsLogMapper.smsOpsLogInsert(smsOpsLog);
    }

    @Override
    public int smsOpsInsert(SmsOps smsOps) {
        return smsLogMapper.smsOpsInsert(smsOps);
    }

    @Override
    public int smsOpsUpdate(String sendid, QueryStatusResponseBO queryStatusResponseBO) {
        SmsOps smsOps = new SmsOps();
        List<QueryStatusBody> queryStatusBodyList = queryStatusResponseBO.getObj();
        for (int i=0; i<queryStatusBodyList.size(); i++) {
            QueryStatusBody queryStatusBody = queryStatusBodyList.get(i);
            smsOps.setStatus(queryStatusBody.getStatus());
            smsOps.setUpdatetime(queryStatusBody.getUpdatetime());
            smsOps.setLastUpdate(new Date());
            smsOps.setSendid(sendid);
            smsOps.setMobile(queryStatusBody.getMobile());
            smsLogMapper.smsOpsUpdate(smsOps);
        }
        return queryStatusBodyList.size();
    }
}
