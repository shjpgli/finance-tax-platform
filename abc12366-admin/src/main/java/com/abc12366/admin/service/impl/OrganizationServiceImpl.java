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
        //验证部门信息
        validateOrg(organizationBO);
        Organization organization = new Organization();
        BeanUtils.copyProperties(organizationBO, organization);
        organization.setId(Utils.uuid());
        Date date = new Date();
        organization.setCreateTime(date);
        organization.setLastUpdate(date);
        int insert = organizationMapper.insert(organization);
        if (insert != 1) {
            LOGGER.warn("插入失败，参数：{}", organization.toString());
            throw new ServiceException(4101);
        }
        return organization;
    }

    /**
     * 验证部门信息
     * @param organizationBO
     */
    private void validateOrg(OrganizationBO organizationBO) {
        if(isOrgName(organizationBO.getName())){
            LOGGER.warn("已存在该机构名称{}", organizationBO);
            throw new ServiceException(4099);
        }
        if(isOrgParentId(organizationBO.getParentId())){
            LOGGER.warn("该机构已禁用{}", organizationBO);
            throw new ServiceException(4099);
        }

    }

    /**
     * 验证部门是否被禁用
     * @param parentId
     * @return
     */
    public boolean isOrgParentId(String parentId){
        OrganizationBO bo = organizationRoMapper.selectOrganizationById(parentId);
        if(bo != null && bo.getStatus() == false){
            return true;
        }
        return false;
    }

    /**
     * 验证部门名称是否存在
     * @param orgName
     * @return
     */
    public boolean isOrgName(String orgName){
        OrganizationBO bo = organizationRoMapper.selectOrganizationByName(orgName);
        if(bo == null){
            return false;
        }
        return true;
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
            Boolean status = updateBO.getStatus();
            organization.setStatus(status);
            int update = organizationMapper.update(organization);
            if (update != 1) {
                LOGGER.warn("修改失败，id：{}", organization.toString());
                throw new ServiceException(4103);
            }
            //查找子节点
            List<OrganizationBO> boList = organizationRoMapper.selectChildOrg(updateBO.getId());
            for (OrganizationBO bo:boList){
                bo.setStatus(status);
                Organization org = new Organization();
                BeanUtils.copyProperties(bo,org);
                int upd = organizationMapper.update(org);
                if (upd != 1) {
                    LOGGER.warn("修改子节点状态失败{}", upd);
                    throw new ServiceException(4097);
                }
            }
        }
    }

    @Override
    public List<OrganizationBO> selectChildOrg(String id) {
        return organizationRoMapper.selectChildOrg(id);
    }

    @Override
    public OrganizationBO selectOrganizationByName(String name) {
        return organizationRoMapper.selectOrganizationByName(name);
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
