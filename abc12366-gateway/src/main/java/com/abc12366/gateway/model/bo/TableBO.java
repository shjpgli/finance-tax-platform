package com.abc12366.gateway.model.bo;

/**
 * 分表模型
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-05-03 11:07 AM
 * @since 1.0.0
 */
public class TableBO {

    // 按年分表
    private String yyyy;
    // 按月分表
    private String yyyyMM;
    // 按天分表
    private String yyyyMMdd;

    public TableBO() {
    }

    public TableBO(String yyyyMMdd) {
        this.yyyyMMdd = yyyyMMdd;
    }

    public String getYyyy() {
        return yyyy;
    }

    public void setYyyy(String yyyy) {
        this.yyyy = yyyy;
    }

    public String getYyyyMM() {
        return yyyyMM;
    }

    public void setYyyyMM(String yyyyMM) {
        this.yyyyMM = yyyyMM;
    }

    public String getYyyyMMdd() {
        return yyyyMMdd;
    }

    public void setYyyyMMdd(String yyyyMMdd) {
        this.yyyyMMdd = yyyyMMdd;
    }

    @Override
    public String toString() {
        return "TableBO{" +
                "yyyy='" + yyyy + '\'' +
                ", yyyyMM='" + yyyyMM + '\'' +
                ", yyyyMMdd='" + yyyyMMdd + '\'' +
                '}';
    }
}
