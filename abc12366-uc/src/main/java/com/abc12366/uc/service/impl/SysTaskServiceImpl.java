package com.abc12366.uc.service.impl;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.SysTaskMapper;
import com.abc12366.uc.mapper.db2.SysTaskRoMapper;
import com.abc12366.uc.model.SysTask;
import com.abc12366.uc.model.bo.SysTaskBO;
import com.abc12366.uc.model.bo.SysTaskInsertAndUpdateBO;
import com.abc12366.uc.service.SysTaskService;
import com.abc12366.uc.web.VipPrivilegeController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-24
 * Time: 9:51
 */
@Service
public class SysTaskServiceImpl implements SysTaskService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SysTaskServiceImpl.class);

    @Autowired
    private SysTaskMapper sysTaskMapper;

    @Autowired
    private SysTaskRoMapper sysTaskRoMapper;

    @Override
    public List<SysTaskBO> selectList(Map<String, String> map) {
        return sysTaskRoMapper.selectList(map);
    }

    @Override
    public List<SysTaskBO> selectDeployedList(Map<String, String> map) {
        return sysTaskRoMapper.selectDeployedList(map);
    }

    @Override
    public SysTaskBO selectOne(String id) {
        return sysTaskRoMapper.selectOne(id);
    }

    @Override
    public SysTaskBO insert(SysTaskInsertAndUpdateBO sysTaskInsertBO) {
        if (sysTaskInsertBO == null) {
            LOGGER.warn("新增失败，参数为：" + null);
            throw new ServiceException(4101);
        }
        SysTask sysTask = new SysTask();
        BeanUtils.copyProperties(sysTaskInsertBO, sysTask);
        Date date = new Date();
        sysTask.setId(Utils.uuid());
        sysTask.setCreateTime(date);
        sysTask.setLastUpdate(date);
        int result = sysTaskMapper.insert(sysTask);
        if (result != 1) {
            LOGGER.warn("新增失败，参数为：", sysTask);
            throw new ServiceException(4101);
        }
        SysTaskBO sysTaskBO = new SysTaskBO();
        BeanUtils.copyProperties(sysTask, sysTaskBO);
        return sysTaskBO;
    }

    @Override
    public SysTaskBO update(SysTaskInsertAndUpdateBO sysTaskUpdateBO, String id) {
        if (sysTaskUpdateBO == null) {
            LOGGER.warn("修改失败，参数为：" + null);
            throw new ServiceException(4102);
        }
        SysTaskBO sysTaskQuery = sysTaskRoMapper.selectOne(id);
        if (sysTaskQuery == null) {
            LOGGER.warn("修改失败，不存在要被修改的数据，参数为：id=" + id);
            throw new ServiceException(4102);
        }
        //若系统任务已发布则不允许修改，修改前必须先撤销发布
        if (sysTaskQuery.isStatus()) {
            LOGGER.warn("修改失败，该任务已发布，撤销发布后才允许修改，参数为：id=" + id);
            throw new ServiceException(4102);
        }
        SysTask sysTask = new SysTask();
        BeanUtils.copyProperties(sysTaskUpdateBO, sysTask);
        sysTask.setId(id);
        sysTask.setLastUpdate(new Date());
        int result = sysTaskMapper.update(sysTask);
        if (result != 1) {
            LOGGER.warn("修改失败，参数为：", sysTask);
            throw new ServiceException(4102);
        }
        SysTaskBO sysTaskBO = new SysTaskBO();
        BeanUtils.copyProperties(sysTask, sysTaskBO);
        return sysTaskBO;
    }

    @Override
    public boolean delete(String id) {
        SysTaskBO sysTaskQuery = sysTaskRoMapper.selectOne(id);
        if (sysTaskQuery == null) {
            LOGGER.warn("删除失败，不存在要被删除的数据，参数为：id=" + id);
            throw new ServiceException(4103);
        }
        int result = sysTaskMapper.delete(id);
        if (result != 1) {
            LOGGER.warn("删除失败，参数为：id=" + id);
            throw new ServiceException(4103);
        }
        return true;
    }
}
