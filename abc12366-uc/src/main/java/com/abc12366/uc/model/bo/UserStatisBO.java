package com.abc12366.uc.model.bo;

/**
 * 用户统计实体类
 *
 * @author lizhongwei
 * @create 2017-11-21 4:14 PM
 * @since 1.0.0
 */
public class UserStatisBO {

    /**
     * 条数
     */
    private Integer count;
    /**
     * 月份
     */
    private String months;
    /**
     * 天数
     */
    private String days;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMonths() {
        return months;
    }

    public void setMonths(String months) {
        this.months = months;
    }

    public String getDays() {
        return days;
    }

    public void setDays(String days) {
        this.days = days;
    }
}
