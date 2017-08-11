package com.abc12366.uc.service;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
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
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Admin: liuguiyao<435720953@qq.com.com>
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
    @Autowired
    private VipPrivilegeLevelService vipPrivilegeLevelService;
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

        //特权名称唯一性校验
        List<VipPrivilegeBO> vipPrivilegeBOList = vipPrivilegeRoMapper.selectList(null);
        if (vipPrivilegeInsertBO.getName() != null) {
            for (VipPrivilegeBO vipPrivilegeBO : vipPrivilegeBOList) {
                if (vipPrivilegeBO.getName().equals(vipPrivilegeInsertBO.getName())) {
                    LOGGER.warn("新增失败，参数：{}", vipPrivilegeInsertBO);
                    throw new ServiceException(4605);
                }
            }
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

        //特权名称唯一性校验
        List<VipPrivilegeBO> vipPrivilegeBOList = vipPrivilegeRoMapper.selectList(null);
        //这条数据本身不计入校验数据
        for (int i = 0; i < vipPrivilegeBOList.size(); i++) {
            if ((vipPrivilegeBOList.get(i)).getId().equals(id)) {
                vipPrivilegeBOList.remove(i);
            }
        }
        if (vipPrivilegeUpdateBO.getName() != null) {
            for (VipPrivilegeBO vipPrivilegeBO : vipPrivilegeBOList) {
                if (vipPrivilegeBO.getName().equals(vipPrivilegeUpdateBO.getName())) {
                    LOGGER.warn("修改失败，参数：{}", vipPrivilegeUpdateBO);
                    throw new ServiceException(4605);
                }
            }
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
        //这里把对应的会员权益删除
        int tmpi = vipPrivilegeLevelService.deleteByPrivilege(id);
        LOGGER.info("deleteByPrivilege:{}", tmpi);
        return true;
    }

    @Override
    public void enableOrDisable(String id, String status) {
        LOGGER.info("{}:{}", id, status);
        if ((!status.equals("true")) && (!status.equals("false"))) {
            throw new ServiceException(4614);
        }
        boolean modifyStatus = status.equals("true");
        VipPrivilege vipPrivilege = new VipPrivilege();
        vipPrivilege.setId(id);
        vipPrivilege.setStatus(modifyStatus);
        vipPrivilege.setLastUpdate(new Date());
        int result = vipPrivilegeMapper.update(vipPrivilege);
        if (result < 1) {
            if (modifyStatus) {
                throw new ServiceException(4623);
            }
            throw new ServiceException(4624);
        }
    }
}
