package com.abc12366.uc.util;

/**
 * Created by Administrator on 2017/9/18.
 */
public class MessageConstant {
	//电子申报系统信息查询数量
	public static final String DZSBQNUM="200";
	
    //消息类型
    public static final String SYS_MESSAGE = "1";

    //UC访问地址
    public static final String SPDD = "SPDD";//商品订单
    public static final String ZZFPDD = "ZZFPDD";//纸质发票订单
    public static final String DZFPDD = "DZFPDD";//电子发票订单
    public static final String XTTX = "XTTX";//系统提醒

    public static final String VIEW_DETAILS = "查看详情";


    public static final String YWTX_WEB = "web";    //web消息
    public static final String YWTX_WECHAT = "wechat";  //微信消息
    public static final String YWTX_MESSAGE = "message";    //短信消息
    public static final String YWTX_CODE = "A_YWTX";    //业务提醒代码

    /**实物商品**/
    //发货
    public static final String DELIVER_GOODS_PREFIX = "您的宝贝已发出，订单号：";
    public static final String DELIVER_GOODS_YDH = "，运单号：";
    public static final String DELIVER_GOODS_PREFIX_NO = "您的宝贝已发出，订单号：";
    public static final String SUFFIX = "，如有疑问请联系客服咨询4008873133；";

    //退货审核通过
    public static final String  RETREAT_CHECK_ADOPT = "恭喜您！您的退货审核已通过，订单号：{#DATA.ORDER}，请按退货具体要求退货。";
    //退货审核拒绝
    public static final String  RETREAT_CHECK_REFUSE = "很抱歉！您的退货审核未通过，订单号：{#DATA.ORDER}，具体原因请至退货申请详情里查询；";
    //换货审核通过
    public static final String  EXCHANGE_CHECK_ADOPT = "恭喜您！您的换货审核已通过，订单号：{#DATA.ORDER}，请按换货具体要求换货；";
    //换货审核拒绝
    public static final String  EXCHANGE_CHECK_REFUSE = "很抱歉！您的换货审核未通过，订单号：{#DATA.ORDER}，具体原因请至换货申请详情里查询；";
    //换货发货
    public static final String EXCHANGE_DELIVER_GOODS_PREFIX = "您更换的宝贝已发出，订单号：{#DATA.ORDER}，运单号：（{#DATA.COMP}+{#DATA.EXPRESSNO}），如有疑问请联系客服咨询4008873133；";

    //退款
    public static final String REFUND_PREFIX = "您有一笔退款已完成，返回支付宝或微信";
    public static final String REFUND_SUFFIX = "元，请注意查收，订单号：";

    /**纸质发票**/
    //导入快递信息
    public static final String IMPORT_COURIER_INFO = "您申请的纸质发票已寄出，发票订单号：{#DATA.INVOICE}，运单号：（{#DATA.COMP}+{#DATA.EXPRESSNO}），如有疑问请联系客服咨询4008873133；";
    //审批通过
    public static final String INVOICE_CHECK_ADOPT = "您申请的纸质发票已审批通过，发票订单号：{#DATA.INVOICE}，敬请关注发票寄送信息；";
    //审批拒绝
    public static final String INVOICE_CHECK_REFUSE = "很抱歉！您的纸质发票申请审核未通过，发票订单号：{#DATA.INVOICE}，具体原因请至发票申请详情里查询；";
    /**电子发票**/
    //审批通过
    public static final String ELECTRON_INVOICE_CHECK_ADOPT = "您申请的电子发票已开具，发票订单号：{#DATA.INVOICE}，请至发票申请详情里下载发票；";
    public static final String RED_PACKAGE = "您的微信红包口令是：{#DATA.PACKAGE}，请前往官方《财税专家》微信公众号上领取红包。";


    //审批拒绝
    public static final String ELECTRON_INVOICE_CHECK_REFUSE = "很抱歉！您的电子发票申请审核未通过，发票订单号：{#DATA.INVOICE}，具体原因请至发票申请详情里查询；";
    /**虚拟商品**/
    //购买会员
    public static final String BUYING_MEMBERS_PREFIX = "恭喜您！您已成功升级为{#DATA.VIP}，请及时了解{#DATA.VIP}相关权益；";
    public static final String BUYING_MEMBERS_SUFFIX = "查看详细会员权益";
    //积分充值
    public static final String INTEGRAL_RECHARGE = "恭喜您！您的积分已充值成功，当前积分为{#DATA.POINT}分，详情请查看积分明细；";
    
    
    //会员到期消息
    public static final String HYDQMSG="您的财税专家会员（{#DATA.LEVEL}）即将过期，过期时间:{#DATA.DATE}，为不影响您正常使用，请您及时续费";
    
    public static final String SBQXXTMSG="财税专家用户提醒，本月纳税申报可申报的报表种类：增值税、消费税、所得税、财务报表，申报期限：{#DATA.DATE}，实际申报种类以税务局核定信息为准，请您及时进行申报缴税！";
    
    public static final String SBQXSJMSG="本月纳税申报可申报的报表种类：增值税、消费税、所得税、财务报表，申报期限：{#DATA.DATE}，实际申报种类以税务局核定信息为准，请您及时进行申报缴税";
}
