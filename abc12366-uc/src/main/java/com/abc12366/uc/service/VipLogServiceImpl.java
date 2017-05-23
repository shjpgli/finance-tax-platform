package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.VipLogMapper;
import com.abc12366.uc.mapper.db2.VipLogRoMapper;
import com.abc12366.uc.model.VipLog;
import com.abc12366.uc.model.bo.VipLogBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-19
 * Time: 15:39
 */
@Service
public class VipLogServiceImpl implements VipLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VipLogServiceImpl.class);

    @Autowired
    private VipLogRoMapper vipLogRoMapper;

    @Autowired
    private VipLogMapper vipLogMapper;

    @Override
    public List<VipLogBO> selectList() {
        return vipLogRoMapper.selectList();
    }

    @Override
    public VipLogBO insert(VipLogBO vipLogBO) {
        if (vipLogBO == null) {
            LOGGER.warn("新增失败，参数：" + null);
            throw new ServiceException(4101);
        }
        VipLog vipLog = new VipLog();
        BeanUtils.copyProperties(vipLogBO, vipLog);
        vipLog.setId(Utils.uuid());
        vipLog.setCreateTime(new Date());
        int result = vipLogMapper.insert(vipLog);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + vipLog.toString());
            throw new ServiceException(4101);
        }
        VipLogBO vipLogBOReturn = new VipLogBO();
        BeanUtils.copyProperties(vipLog, vipLogBOReturn);
        return vipLogBOReturn;
    }
}
