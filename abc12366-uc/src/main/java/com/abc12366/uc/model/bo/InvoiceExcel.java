package com.abc12366.uc.model.bo;

import java.io.Serializable;


/**
 * 发票信息
 **/
@SuppressWarnings("serial")
public class InvoiceExcel implements Serializable {

    /**
     * 用户订单号
     **/
    private String userOrderNo;

    /**
     * 收件公司
     **/
    private String receivingCompany;

    /**
     * 联系人
     **/
    private String linkman;

    /**
     * 手机号码
     **/
    private String phone;

    /**
     * 收件详细地址
     **/
    private String address;

    /**
     * 寄托物内容
     **/
    private String cargoContent;

    /**
     * 寄托物数量
     **/
    private Integer cargoNum;

    public String getReceivingCompany() {
        return receivingCompany;
    }

    public void setReceivingCompany(String receivingCompany) {
        this.receivingCompany = receivingCompany;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCargoContent() {
        return cargoContent;
    }

    public void setCargoContent(String cargoContent) {
        this.cargoContent = cargoContent;
    }

    public Integer getCargoNum() {
        return cargoNum;
    }

    public void setCargoNum(Integer cargoNum) {
        this.cargoNum = cargoNum;
    }

    public String getUserOrderNo() {
        return userOrderNo;
    }

    public void setUserOrderNo(String userOrderNo) {
        this.userOrderNo = userOrderNo;
    }
}
