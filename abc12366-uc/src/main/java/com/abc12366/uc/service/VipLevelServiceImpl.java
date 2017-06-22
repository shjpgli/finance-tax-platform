package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.VipLevelMapper;
import com.abc12366.uc.mapper.db2.VipLevelRoMapper;
import com.abc12366.uc.model.VipLevel;
import com.abc12366.uc.model.bo.VipLevelBO;
import com.abc12366.uc.model.bo.VipLevelUpdateBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author liuguiyao<435720953@qq.com.com>
 * @create 2017-05-19 10:18 PM
 * @since 2.0.0
 */
@Service
public class VipLevelServiceImpl implements VipLevelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VipLevelServiceImpl.class);

    @Autowired
    private VipLevelMapper vipLevelMapper;

    @Autowired
    private VipLevelRoMapper vipLevelRoMapper;

    @Override
    public List<VipLevelBO> selectList(Map map) {
        return vipLevelRoMapper.selectList(map);
    }

    @Override
    public VipLevelBO selectOne(String id) {
        return vipLevelRoMapper.selectOne(id);
    }

    @Override
    public VipLevelBO insert(VipLevelBO vipLevelBO) {
        if (vipLevelBO == null) {
            LOGGER.warn("新增失败，参数：" + null);
            throw new ServiceException(4101);
        }
        VipLevel vipLevelQuery = vipLevelRoMapper.selectByLevel(vipLevelBO.getLevel());
        if (vipLevelQuery != null) {
            LOGGER.warn("新增失败，参数：level=" + vipLevelBO.getLevel());
            throw new ServiceException(4101);
        }
        VipLevel vipLevel = new VipLevel();
        BeanUtils.copyProperties(vipLevelBO, vipLevel);
        Date date = new Date();
        vipLevel.setId(Utils.uuid());
        vipLevel.setLastUpdate(date);
        vipLevel.setCreateTime(date);
        int result = vipLevelMapper.insert(vipLevel);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + vipLevel.toString());
            throw new ServiceException(4101);
        }
        VipLevelBO vipLevelBOReturn = new VipLevelBO();
        BeanUtils.copyProperties(vipLevel, vipLevelBOReturn);
        return vipLevelBOReturn;
    }

    @Override
    public VipLevelBO update(VipLevelUpdateBO vipLevelUpdateBO) {
        if (vipLevelUpdateBO == null) {
            LOGGER.warn("修改失败，参数：" + null);
            throw new ServiceException(4102);
        }
        VipLevelBO vipLevelQuery = vipLevelRoMapper.selectOne(vipLevelUpdateBO.getId());
        if (vipLevelQuery == null) {
            LOGGER.warn("修改失败，可更新对象为：" + null);
            throw new ServiceException(4102);
        }
        VipLevel vipLevel = new VipLevel();
        BeanUtils.copyProperties(vipLevelUpdateBO, vipLevel);
        int result = vipLevelMapper.update(vipLevel);
        if (result != 1) {
            LOGGER.warn("修改失败，参数为：" + vipLevel);
            throw new ServiceException(4102);
        }
        VipLevelBO vipLevelBOReturn = new VipLevelBO();
        BeanUtils.copyProperties(vipLevel, vipLevelBOReturn);
        return vipLevelBOReturn;
    }

    @Override
    public int delete(String id) {
        LOGGER.info("{}", id);
        int result = vipLevelMapper.delete(id);
        if (result != 1) {
            LOGGER.warn("删除失败，参数为：id=" + id);
            throw new ServiceException(4103);
        }
        return 1;
    }
}
