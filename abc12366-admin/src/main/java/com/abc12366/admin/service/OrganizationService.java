package com.abc12366.admin.service;

import com.abc12366.admin.model.Organization;
import com.abc12366.admin.model.bo.OrganizationBO;
import com.abc12366.admin.model.bo.OrganizationUpdateBO;

import java.util.List;

/**
 * @description：组织管理
 */
public interface OrganizationService {

    /**
     * 查询组织列表
     * @return
     * @param organization
     */
    List<OrganizationBO> selectList(Organization organization);

    /**
     * 添加组织
     *
     * @param organizationBO
     */
    Organization addOrganization(OrganizationBO organizationBO);

    /**
     * 根据id查找组织
     *
     * @param id
     * @return
     */
    OrganizationBO selectOrganizationById(String id);

    /**
     * 更新组织
     *
     * @param organizationBO
     */
    OrganizationBO updateOrganization(OrganizationBO organizationBO);

    /**
     * 根据id删除组织
     *
     * @param id
     */
    void deleteOrganizationById(String id);

    void disableAll();

    void enable(OrganizationUpdateBO updateBO);

    List<OrganizationBO> selectChildOrg(String id);
}
