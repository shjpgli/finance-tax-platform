package com.abc12366.uc.model.bo;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-25
 * Time: 10:29
 */
public class ExpComputeBO {
    @NotEmpty
    private String userId;
    @NotEmpty
    private String uexpruleId;
    @NotEmpty
    private String clientType;

    public ExpComputeBO() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUexpruleId() {
        return uexpruleId;
    }

    public void setUexpruleId(String uexpruleId) {
        this.uexpruleId = uexpruleId;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}
