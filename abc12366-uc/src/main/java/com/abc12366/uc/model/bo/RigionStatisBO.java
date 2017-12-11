package com.abc12366.uc.model.bo;

/**
 * 用户区域分布统计bo
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-12-08
 * Time: 14:45
 */
public class RigionStatisBO {
    /**
     * 区域名称
     */
    private String regionName;
    /**
     * 区域代码
     */
    private String regionCode;
    /**
     * 数量
     */
    private int allCount;

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public int getAllCount() {
        return allCount;
    }

    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }

    @Override
    public String toString() {
        return "RigionStatisBO{" +
                "regionName='" + regionName + '\'' +
                ", regionCode='" + regionCode + '\'' +
                ", allCount=" + allCount +
                '}';
    }
}
