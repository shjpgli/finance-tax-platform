package com.abc12366.uc.model.bo;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-08-10 9:11 PM
 * @since 1.0.0
 */
public class SfImportBO {

    private String orderNo;
    private String expressNo;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getExpressNo() {
        return expressNo;
    }

    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo;
    }

    @Override
    public String toString() {
        return "SfImportBO{" +
                "orderNo='" + orderNo + '\'' +
                ", expressNo='" + expressNo + '\'' +
                '}';
    }
}
