package com.abc12366.uc.service.admin.impl;

import com.abc12366.uc.mapper.db1.OperateMessageMapper;
import com.abc12366.uc.model.admin.OperateMessage;
import com.abc12366.uc.model.admin.bo.OperateMessageBO;
import com.abc12366.uc.service.admin.OperateMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-18
 * Time: 11:03
 */
@Service
public class OperateMessageServiceImpl implements OperateMessageService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OperateMessageServiceImpl.class);

    @Autowired
    private OperateMessageMapper operateMessageMapper;

    @Override
    public void insert(OperateMessageBO operateMessageBO) {
        LOGGER.info("发送运营消息：{}",operateMessageBO);
        operateMessageMapper.insert(new OperateMessage());
    }
}
