package com.abc12366.uc.model;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-09-22
 * Time: 10:18
 */
public class PrivilegeItem {
    private String id;
    private String privilegeId;
    //专享会员勋章：1：是，0：否
    private int ZXHYXZ;
    //会员积分加成
    private float HYJFJC;
    //会员经验值加成
    private float HYJYZJC;
    //用户升级奖励积分数
    private int YHSJJL;
    //用户财税经验证明印制,单位：次/年
    private int CSJYZMYZ_TIMES;
    //用户财税经验证明印制,是否包邮
    private int CSJYZMYZ_BAOYOU;
    //用户帐号合并特权\r\n,单位：个/年
    private int YHZHHBTQ;
    //在线课程培训\r\n:VIP1,VIP2,VIP3,VIP4
    private String ZXKCPX;
    //线下课程培训，单位：次
    private int XXKCPX;
    //线下会员活动，单位：次/年
    private int XXHYHD;
    //线上会员日：享有/不享有
    private int XSHYR;
    //个人帐号绑定企业户数,-1代表无限制
    private int GRZHBDQYS;
    //业务提醒-站内,是/否
    private int YWTX_ZN;
    //业务提醒-微信,是/否
    private int YWTX_WX;
    //发布求职信息，次/年
    private int FBQZXX;
    //生日礼包，VIP2，VIP3，VIP4
    private String SRLB;
    //用户积分转让-次数
    private int YHJFZR_CS;
    //用户积分转让-上限
    private int YHJFZR_SX;
    //商品/发票免邮券,单位：张
    private int SPFPMYQ;
    //专属客服经理,享有/不享有
    private int ZSKFJL;
    //财税期刊,享有/不享有
    private int CSQK;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrivilegeId() {
        return privilegeId;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public int getZXHYXZ() {
        return ZXHYXZ;
    }

    public void setZXHYXZ(int ZXHYXZ) {
        this.ZXHYXZ = ZXHYXZ;
    }

    public float getHYJFJC() {
        return HYJFJC;
    }

    public void setHYJFJC(float HYJFJC) {
        this.HYJFJC = HYJFJC;
    }

    public float getHYJYZJC() {
        return HYJYZJC;
    }

    public void setHYJYZJC(float HYJYZJC) {
        this.HYJYZJC = HYJYZJC;
    }

    public int getYHSJJL() {
        return YHSJJL;
    }

    public void setYHSJJL(int YHSJJL) {
        this.YHSJJL = YHSJJL;
    }

    public int getCSJYZMYZ_TIMES() {
        return CSJYZMYZ_TIMES;
    }

    public void setCSJYZMYZ_TIMES(int CSJYZMYZ_TIMES) {
        this.CSJYZMYZ_TIMES = CSJYZMYZ_TIMES;
    }

    public int getCSJYZMYZ_BAOYOU() {
        return CSJYZMYZ_BAOYOU;
    }

    public void setCSJYZMYZ_BAOYOU(int CSJYZMYZ_BAOYOU) {
        this.CSJYZMYZ_BAOYOU = CSJYZMYZ_BAOYOU;
    }

    public int getYHZHHBTQ() {
        return YHZHHBTQ;
    }

    public void setYHZHHBTQ(int YHZHHBTQ) {
        this.YHZHHBTQ = YHZHHBTQ;
    }

    public String getZXKCPX() {
        return ZXKCPX;
    }

    public void setZXKCPX(String ZXKCPX) {
        this.ZXKCPX = ZXKCPX;
    }

    public int getXXKCPX() {
        return XXKCPX;
    }

    public void setXXKCPX(int XXKCPX) {
        this.XXKCPX = XXKCPX;
    }

    public int getXXHYHD() {
        return XXHYHD;
    }

    public void setXXHYHD(int XXHYHD) {
        this.XXHYHD = XXHYHD;
    }

    public int getXSHYR() {
        return XSHYR;
    }

    public void setXSHYR(int XSHYR) {
        this.XSHYR = XSHYR;
    }

    public int getGRZHBDQYS() {
        return GRZHBDQYS;
    }

    public void setGRZHBDQYS(int GRZHBDQYS) {
        this.GRZHBDQYS = GRZHBDQYS;
    }

    public int getYWTX_ZN() {
        return YWTX_ZN;
    }

    public void setYWTX_ZN(int YWTX_ZN) {
        this.YWTX_ZN = YWTX_ZN;
    }

    public int getYWTX_WX() {
        return YWTX_WX;
    }

    public void setYWTX_WX(int YWTX_WX) {
        this.YWTX_WX = YWTX_WX;
    }

    public int getFBQZXX() {
        return FBQZXX;
    }

    public void setFBQZXX(int FBQZXX) {
        this.FBQZXX = FBQZXX;
    }

    public String getSRLB() {
        return SRLB;
    }

    public void setSRLB(String SRLB) {
        this.SRLB = SRLB;
    }

    public int getYHJFZR_CS() {
        return YHJFZR_CS;
    }

    public void setYHJFZR_CS(int YHJFZR_CS) {
        this.YHJFZR_CS = YHJFZR_CS;
    }

    public int getYHJFZR_SX() {
        return YHJFZR_SX;
    }

    public void setYHJFZR_SX(int YHJFZR_SX) {
        this.YHJFZR_SX = YHJFZR_SX;
    }

    public int getSPFPMYQ() {
        return SPFPMYQ;
    }

    public void setSPFPMYQ(int SPFPMYQ) {
        this.SPFPMYQ = SPFPMYQ;
    }

    public int getZSKFJL() {
        return ZSKFJL;
    }

    public void setZSKFJL(int ZSKFJL) {
        this.ZSKFJL = ZSKFJL;
    }

    public int getCSQK() {
        return CSQK;
    }

    public void setCSQK(int CSQK) {
        this.CSQK = CSQK;
    }
}
