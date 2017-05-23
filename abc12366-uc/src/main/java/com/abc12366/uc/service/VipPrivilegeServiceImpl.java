package com.abc12366.uc.service;

import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import com.abc12366.uc.mapper.db1.VipPrivilegeMapper;
import com.abc12366.uc.mapper.db2.VipPrivilegeRoMapper;
import com.abc12366.uc.model.VipPrivilege;
import com.abc12366.uc.model.bo.VipPrivilegeBO;
import com.abc12366.uc.model.bo.VipPrivilegeInsertAndUpdateBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * User: liuguiyao<435720953@qq.com.com>
 * Date: 2017-05-22
 * Time: 9:26
 */
@Service
public class VipPrivilegeServiceImpl implements VipPrivilegeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VipPrivilegeServiceImpl.class);

    @Autowired
    private VipPrivilegeRoMapper vipPrivilegeRoMapper;

    @Autowired
    private VipPrivilegeMapper vipPrivilegeMapper;

    @Override
    public List<VipPrivilegeBO> selectList(Map map) {
        return vipPrivilegeRoMapper.selectList(map);
    }

    @Override
    public VipPrivilegeBO selectOne(String id) {
        return vipPrivilegeRoMapper.selectOne(id);
    }

    @Override
    public VipPrivilegeBO insert(VipPrivilegeInsertAndUpdateBO vipPrivilegeInsertBO) {
        if (vipPrivilegeInsertBO == null) {
            LOGGER.warn("新增失败，参数：" + null);
            throw new ServiceException(4101);
        }
        VipPrivilege vipPrivilege = new VipPrivilege();
        BeanUtils.copyProperties(vipPrivilegeInsertBO, vipPrivilege);
        Date date = new Date();
        vipPrivilege.setId(Utils.uuid());
        vipPrivilege.setCreateTime(date);
        vipPrivilege.setLastUpdate(date);
        if (StringUtils.isEmpty(vipPrivilegeInsertBO.getStatus())) {
            vipPrivilege.setStatus(true);
        }
        int result = vipPrivilegeMapper.insert(vipPrivilege);
        if (result != 1) {
            LOGGER.warn("新增失败，参数：" + vipPrivilege);
            throw new ServiceException(4101);
        }
        VipPrivilegeBO vipPrivilegeBOReturn = new VipPrivilegeBO();
        BeanUtils.copyProperties(vipPrivilege, vipPrivilegeBOReturn);
        return vipPrivilegeBOReturn;
    }

    @Override
    public VipPrivilegeBO update(VipPrivilegeInsertAndUpdateBO vipPrivilegeUpdateBO, String id) {
        if (vipPrivilegeUpdateBO == null) {
            LOGGER.warn("修改失败，参数：" + null);
            throw new ServiceException(4102);
        }
        VipPrivilegeBO vipPrivilegeQuery = vipPrivilegeRoMapper.selectOne(id);
        if (vipPrivilegeQuery == null) {
            LOGGER.warn("修改失败，不存在可被修改的数据，参数：" + null);
            throw new ServiceException(4102);
        }
        VipPrivilege vipPrivilege = new VipPrivilege();
        BeanUtils.copyProperties(vipPrivilegeUpdateBO, vipPrivilege);
        vipPrivilege.setId(id);
        vipPrivilege.setCreateTime(null);
        vipPrivilege.setLastUpdate(new Date());
        if (StringUtils.isEmpty(vipPrivilegeUpdateBO.getStatus())) {
            vipPrivilege.setStatus(null);
        }
        int result = vipPrivilegeMapper.update(vipPrivilege);
        if (result != 1) {
            LOGGER.warn("修改失败，参数：" + vipPrivilege);
            throw new ServiceException(4102);
        }
        VipPrivilegeBO vipPrivilegeBOReturn = new VipPrivilegeBO();
        BeanUtils.copyProperties(vipPrivilege, vipPrivilegeBOReturn);
        return vipPrivilegeBOReturn;
    }

    @Override
    public boolean delete(String id) {
        VipPrivilegeBO vipPrivilegeBO = vipPrivilegeRoMapper.selectOne(id);
        if (vipPrivilegeBO == null) {
            LOGGER.warn("删除失败，不存在要被删除的数据,参数：" + id);
            throw new ServiceException(4103);
        }
        int result = vipPrivilegeMapper.delete(id);
        if (result != 1) {
            LOGGER.warn("删除失败，参数：" + id);
            throw new ServiceException(4103);
        }
        return true;
    }
}
