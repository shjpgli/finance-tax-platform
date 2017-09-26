package com.abc12366.uc.util;

/**
 * Created by Administrator on 2017/9/18.
 */
public class MessageConstant {

    //UC访问地址
    public static final String ABCUC_URL = "https://testuc.abc12366.com/ABCUC";
    public static final String SPDD = "SPDD";//商品订单
    public static final String ZZFPDD = "ZZFPDD";//纸质发票订单
    public static final String DZFPDD = "DZFPDD";//电子发票订单

    /**实物商品**/
    //发货
    public static final String DELIVER_GOODS_PREFIX = "您的宝贝已发出，运单号：";
    public static final String SUFFIX = "，如有疑问请联系客服咨询4008873133";

    //退货审核通过
    public static final String  RETREAT_CHECK_ADOPT = "恭喜您！您的退货审核已通过，请按退货具体要求退货，查看退货申请详情";
    //退货审核拒绝
    public static final String  RETREAT_CHECK_REFUSE = "很抱歉！您的退货审核未通过，具体原因请";
    //换货审核通过
    public static final String  EXCHANGE_CHECK_ADOPT = "恭喜您！您的换货审核已通过，请按换货具体要求换货，查看换货申请详情";
    //换货审核拒绝
    public static final String  EXCHANGE_CHECK_REFUSE = "很抱歉！您的换货审核未通过，订单号：";
    //换货发货
    public static final String EXCHANGE_DELIVER_GOODS_PREFIX = "您更换的宝贝已发出，运单号：";

    //自动确认收货
    //public static final String AUTOMATIC_CONFIRMATION_RECEIPT = "您的订单已完成，订单号：";
    //退款
    public static final String REFUND_PREFIX = "您有一笔退款已完成，返回支付宝或微信";
    public static final String REFUND_SUFFIX = "元，请注意查收，订单号：";

    /**纸质发票**/
    //导入快递信息
    public static final String IMPORT_COURIER_INFO = "您申请的纸质发票已寄出，运单号：";
    //审批通过
    public static final String INVOICE_CHECK_ADOPT = "您申请的纸质发票已审批通过，敬请关注发票寄送信息。";
    //审批拒绝
    public static final String INVOICE_CHECK_REFUSE = "很抱歉！您的纸质发票申请审核未通过，发票索取申请：";
    //自动确认收货
    //public static final String INVOICE_AUTOMATIC_CONFIRMATION_RECEIPT = "您的纸质发票申请已寄送完成，发票索取申请：（发票申请号链接到发票详情），如有疑问请联系客服咨询4008873133";
    /**电子发票**/
    //审批通过
    public static final String ELECTRON_INVOICE_CHECK_ADOPT = "您申请的电子发票已开具，发票下载地址为：";
    //审批拒绝
    public static final String ELECTRON_INVOICE_CHECK_REFUSE = "很抱歉！您的电子发票申请审核未通过，详细原因请查看：";
    /**虚拟商品**/
    //购买会员
    public static final String BUYING_MEMBERS_PREFIX = "恭喜您！您已成功升级为";
    public static final String BUYING_MEMBERS_SUFFIX = "查看详细会员权益";
    //积分充值
    public static final String INTEGRAL_RECHARGE = "恭喜您！您的积分已充值成功，当前积分为";
}
