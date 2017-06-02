package com.abc12366.admin.service.impl;

import com.abc12366.admin.mapper.db1.OrganizationMapper;
import com.abc12366.admin.mapper.db2.OrganizationRoMapper;
import com.abc12366.admin.model.Organization;
import com.abc12366.admin.model.User;
import com.abc12366.admin.model.bo.OrganizationBO;
import com.abc12366.admin.model.bo.OrganizationUpdateBO;
import com.abc12366.admin.model.bo.UserBO;
import com.abc12366.admin.service.OrganizationService;
import com.abc12366.common.exception.ServiceException;
import com.abc12366.common.util.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private static Logger LOGGER = LoggerFactory.getLogger(OrganizationServiceImpl.class);

    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OrganizationRoMapper organizationRoMapper;

    @Override
    public List<OrganizationBO> selectList(Organization organization) {
        return organizationRoMapper.selectList(organization);
    }

    @Transactional("db1TxManager")
    @Override
    public Organization addOrganization(OrganizationBO organizationBO) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationBO, organization);
        organization.setId(Utils.uuid());
        Date date = new Date();
        organization.setCreateTime(date);
        organization.setLastUpdate(date);
        int insert = organizationMapper.insert(organization);
        if (insert != 1) {
            LOGGER.warn("插入失败，参数：{}", organization.toString());
            throw new ServiceException(4001);
        }
        return organization;
    }

    @Override
    public OrganizationBO selectOrganizationById(String id) {
        return organizationRoMapper.selectOrganizationById(id);
    }

    @Transactional("db1TxManager")
    @Override
    public OrganizationBO updateOrganization(OrganizationBO organizationBO) {
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationBO, organization);
        Date date = new Date();
        organization.setLastUpdate(date);
        int update = organizationMapper.update(organization);
        if (update != 1) {
            LOGGER.warn("更新失败，参数：{}", organization.toString());
            throw new ServiceException(4102);
        }
        OrganizationBO bo = new OrganizationBO();
        BeanUtils.copyProperties(organizationBO,bo);
        bo.setLastUpdate(date);
        return bo;
    }

    @Transactional("db1TxManager")
    @Override
    public void deleteOrganizationById(String id) {
        int del = organizationMapper.deleteByPrimaryKey(id);
        if (del != 1) {
            LOGGER.warn("删除失败，id：{}", id);
            throw new ServiceException(4103);
        }
    }

    @Override
    public void enable(OrganizationUpdateBO updateBO) {
        String[] idArray = updateBO.getId().split(",");
        Organization organization = new Organization();
        organization.setLastUpdate(new Date());
        for(String orgId : idArray){
            organization.setId(orgId);
            organization.setStatus(updateBO.getStatus());
            int update = organizationMapper.update(organization);
            if (update != 1) {
                LOGGER.warn("修改失败，id：{}", organization.toString());
                throw new ServiceException(4103);
            }
        }
    }

    @Override
    public void disableAll() {
        Organization organization = new Organization();
        List<OrganizationBO> organizationBOs = organizationRoMapper.selectList(organization);
        for (OrganizationBO temp:organizationBOs){
            organization.setId(temp.getId());
            organization.setStatus(false);
            int enable = organizationMapper.update(organization);
            if(enable != 1){
                LOGGER.warn("修改失败，id：{}", organization.toString());
                throw new ServiceException(4102);
            }
        }
    }
}
