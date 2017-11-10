package com.abc12366.message.service;

import com.abc12366.message.model.bo.MobileMsgBO;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-10-24
 * Time: 9:59
 */
public interface SendMobileMsgService {
    void sendMsg(MobileMsgBO mobileMsgBO);

    void sendMsgByUppyun(MobileMsgBO mobileMsgBO);

    //void sendMsgByAliyun(MobileMsgBO mobileMsgBO);
}
