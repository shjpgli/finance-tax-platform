package com.abc12366.gateway.model.bo;

/**
 * 操作员日志
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-27 2:08 PM
 * @since 1.0.0
 */
public class AdminLogBO {

    private String businessUri;
    // 业务名称
    private String businessName;
    // 业务数据
    private String businessData;
    // 操作类型：POST（新增）、PUT（修改）、GET（查询）、DELETE（删除）
    private String method;
    // 备注
    private String remark;

    public String getBusinessUri() {
        return businessUri;
    }

    public void setBusinessUri(String businessUri) {
        this.businessUri = businessUri;
    }

    public String getBusinessData() {
        return businessData;
    }

    public void setBusinessData(String businessData) {
        this.businessData = businessData;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
