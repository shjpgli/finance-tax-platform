package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.VipLevelMapper;
import com.abc12366.uc.mapper.db2.VipLevelRoMapper;
import com.abc12366.uc.model.VipLevel;
import com.abc12366.uc.model.bo.VipLevelBO;
import com.abc12366.uc.model.bo.VipLevelInsertBO;
import com.abc12366.uc.model.bo.VipLevelUpdateBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public VipLevelBO insert(VipLevelInsertBO vipLevelInsertBO) {
        if (vipLevelInsertBO == null) {
            LOGGER.warn("新增失败，已存在等级为" + vipLevelInsertBO.getLevel() + "的会员等级！");
            throw new ServiceException(4101);
        }
        VipLevelBO vipLevelQuery = vipLevelRoMapper.selectByLevel(vipLevelInsertBO.getLevel());
        if (vipLevelQuery != null) {
            LOGGER.warn("新增失败，参数：level=" + vipLevelInsertBO.getLevel());
            throw new ServiceException(4101);
        }
        VipLevel vipLevel = new VipLevel();
        BeanUtils.copyProperties(vipLevelInsertBO, vipLevel);
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
    public VipLevelBO update(VipLevelUpdateBO vipLevelUpdateBO, String id) {
        if (vipLevelUpdateBO == null) {
            LOGGER.warn("修改失败，参数：" + null);
            throw new ServiceException(4102);
        }
        VipLevel vipLevel = new VipLevel();
        BeanUtils.copyProperties(vipLevelUpdateBO, vipLevel);
        vipLevel.setId(id);
        vipLevel.setLastUpdate(new Date());
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
