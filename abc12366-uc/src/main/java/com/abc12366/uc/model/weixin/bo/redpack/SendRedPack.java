package com.abc12366.uc.model.weixin.bo.redpack;

/**
 * 发送红包对象
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-15 3:35 PM
 * @since 1.0.0
 */
public class SendRedPack {

    // 随机字符串
    private String nonce_str;
    // 签名
    private String sign;
    // 商户订单号 28bit
    private String mch_billno;
    // 商户号
    private String mch_id;
    // 公众账号appid
    private String wxappid;
    // 商户名称
    private String send_name;
    // 用户openid
    private String re_openid;
    // 付款金额
    private Integer total_amount;
    // 红包发放总人数
    private Integer total_num = 1;
    // 红包祝福语
    private String wishing;
    // Ip地址
    private String client_ip;
    // 活动名称
    private String act_name;
    // 备注
    private String remark;
    // 场景id
    private String scene_id = "PRODUCT_2";
    // 活动信息
    private String risk_info;
    // 资金授权商户号
    private String consume_mch_id;

    public SendRedPack() {
    }

    private SendRedPack(Builder builder) {
        setNonce_str(builder.nonce_str);
        setSign(builder.sign);
        setMch_billno(builder.mch_billno);
        setMch_id(builder.mch_id);
        setWxappid(builder.wxappid);
        setSend_name(builder.send_name);
        setRe_openid(builder.re_openid);
        setTotal_amount(builder.total_amount);
        setTotal_num(builder.total_num);
        setWishing(builder.wishing);
        setClient_ip(builder.client_ip);
        setAct_name(builder.act_name);
        setRemark(builder.remark);
        setScene_id(builder.scene_id);
        setRisk_info(builder.risk_info);
        setConsume_mch_id(builder.consume_mch_id);
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    public String getRisk_info() {
        return risk_info;
    }

    public void setRisk_info(String risk_info) {
        this.risk_info = risk_info;
    }

    public String getConsume_mch_id() {
        return consume_mch_id;
    }

    public void setConsume_mch_id(String consume_mch_id) {
        this.consume_mch_id = consume_mch_id;
    }

    @Override
    public String toString() {
        return "SendRedPack{" +
                "nonce_str='" + nonce_str + '\'' +
                ", sign='" + sign + '\'' +
                ", mch_billno='" + mch_billno + '\'' +
                ", mch_id='" + mch_id + '\'' +
                ", wxappid='" + wxappid + '\'' +
                ", send_name='" + send_name + '\'' +
                ", re_openid='" + re_openid + '\'' +
                ", total_amount=" + total_amount +
                ", total_num=" + total_num +
                ", wishing='" + wishing + '\'' +
                ", client_ip='" + client_ip + '\'' +
                ", act_name='" + act_name + '\'' +
                ", remark='" + remark + '\'' +
                ", scene_id='" + scene_id + '\'' +
                ", risk_info='" + risk_info + '\'' +
                ", consume_mch_id='" + consume_mch_id + '\'' +
                '}';
    }

    public static final class Builder {
        private String nonce_str;
        private String sign;
        private String mch_billno;
        private String mch_id;
        private String wxappid;
        private String send_name;
        private String re_openid;
        private Integer total_amount;
        private Integer total_num;
        private String wishing;
        private String client_ip;
        private String act_name;
        private String remark;
        private String scene_id;
        private String risk_info;
        private String consume_mch_id;

        public Builder() {
        }

        public Builder nonce_str(String val) {
            nonce_str = val;
            return this;
        }

        public Builder sign(String val) {
            sign = val;
            return this;
        }

        public Builder mch_billno(String val) {
            mch_billno = val;
            return this;
        }

        public Builder mch_id(String val) {
            mch_id = val;
            return this;
        }

        public Builder wxappid(String val) {
            wxappid = val;
            return this;
        }

        public Builder send_name(String val) {
            send_name = val;
            return this;
        }

        public Builder re_openid(String val) {
            re_openid = val;
            return this;
        }

        public Builder total_amount(Integer val) {
            total_amount = val;
            return this;
        }

        public Builder total_num(Integer val) {
            total_num = val;
            return this;
        }

        public Builder wishing(String val) {
            wishing = val;
            return this;
        }

        public Builder client_ip(String val) {
            client_ip = val;
            return this;
        }

        public Builder act_name(String val) {
            act_name = val;
            return this;
        }

        public Builder remark(String val) {
            remark = val;
            return this;
        }

        public Builder scene_id(String val) {
            scene_id = val;
            return this;
        }

        public Builder risk_info(String val) {
            risk_info = val;
            return this;
        }

        public Builder consume_mch_id(String val) {
            consume_mch_id = val;
            return this;
        }

        public SendRedPack build() {
            return new SendRedPack(this);
        }
    }
}
