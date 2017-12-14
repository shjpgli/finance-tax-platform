package com.abc12366.bangbang.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lizhongwei
 * @create 2017-12-12 12:21 PM
 */
public class GeneralTree {

    private List<Node> lst = new ArrayList<Node>();

    public class Node {
        public String data;
        public String parent;
    }

    public void addNode(String parent, String child) {
        Node t = new Node();
        t.data = child;
        t.parent = parent;
        lst.add(t);
    }

    public String getParent(String x) {
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).data.equals(x))
                return lst.get(i).parent;
        }
        return null;
    }

    public List<String> getChild(String x) {
        List<String> t = new ArrayList<String>();
        for (int i = 0; i < lst.size(); i++) {
            if (lst.get(i).parent.equals(x))
                t.add(lst.get(i).data);
        }
        return t;
    }

   public static final GeneralTree t = new GeneralTree();
    /**
     * @param args
     */
    static{
        // TODO Auto-generated method stub  
        /*1、操作行为包括：我的首页、我的企业、小艾客服、我是会员、发现之旅、财税帮帮、个人中心，点击大模块时再统计该模块下各功能点的操作次数；
        2、统计图形采用柱状图显示时，横坐标是功能点，纵坐标为次数。
        3、点击统计数可显示用户详情列表，列表数据可导出。*/

        //--------------第1级-----------------//
        t.addNode("系统", "我的首页");
        t.addNode("系统", "我的企业");
        t.addNode("系统", "小艾客服");
        t.addNode("系统", "我是会员");
        t.addNode("系统", "发现之旅");
        t.addNode("系统", "财税帮帮");
        t.addNode("系统", "个人中心");

        //--------------第2级-----------------//
        //我的首页又分为：登录、签到、做任务、电子申报、个人会员、积分兑换、税银服务、服务续费、积分充值、办税导航、投诉建议、财税资讯、通知公告、热点问题、帮友热议、活动中心、推荐课程、轮播广告；
        t.addNode("我的首页", "登录");
        t.addNode("我的首页", "签到");
        t.addNode("我的首页", "做任务");
        t.addNode("我的首页", "电子申报");
        t.addNode("我的首页", "个人会员");
        t.addNode("我的首页", "积分兑换");
        t.addNode("我的首页", "税银服务");
        t.addNode("我的首页", "服务续费");
        t.addNode("我的首页", "积分充值");
        t.addNode("我的首页", "办税导航");
        t.addNode("我的首页", "投诉建议");
        t.addNode("我的首页", "财税资讯");
        t.addNode("我的首页", "通知公告");
        t.addNode("我的首页", "热点问题");
        t.addNode("我的首页", "帮友热议");
        t.addNode("我的首页", "活动中心");
        t.addNode("我的首页", "推荐课程");
        t.addNode("我的首页", "轮播广告");
        //我的企业又分为：申报、税务、财务、更多；
        t.addNode("我的企业", "申报");
        t.addNode("我的企业", "税务");
        t.addNode("我的企业", "财务");
        t.addNode("我的企业", "更多");
        //小艾客服分为：我的服务单、qq客服、新手指南、服务续费、忘记申报密码、更换绑定手机、实名认证、查询服务网点、投诉建议、索取发票、积分充值、我的积分、热点问题、帮助中心。

        t.addNode("小艾客服", "我的服务单");
        t.addNode("小艾客服", "qq客服");
        t.addNode("小艾客服", "新手指南");
        t.addNode("小艾客服", "服务续费");
        t.addNode("小艾客服", "忘记申报密码");
        t.addNode("小艾客服", "更换绑定手机");
        t.addNode("小艾客服", "实名认证");
        t.addNode("小艾客服", "查询服务网点");
        t.addNode("小艾客服", "投诉建议");
        t.addNode("小艾客服", "索取发票");
        t.addNode("小艾客服", "积分充值");
        t.addNode("小艾客服", "我的积分");
        t.addNode("小艾客服", "热点问题");
        t.addNode("小艾客服", "帮助中心");

        //--------------第3级-----------------//
        //        申报又分为：国税申报、地税申报、艾易税、海关完税、固定资产、历史报表。
        t.addNode("申报", "国税申报");
        t.addNode("申报", "地税申报");
        t.addNode("申报", "艾易税");
        t.addNode("申报", "海关完税");
        t.addNode("申报", "固定资产");
        t.addNode("申报", "历史报表");

        //        税务分为：发票领用、发票代开、发票验旧。。。等全部电子税局应用。
        t.addNode("税务", "发票领用");
        t.addNode("税务", "发票代开");
        t.addNode("税务", "发票验旧");

        //        更多分为：社保、公积金、统计局、工商年审
        t.addNode("更多", "社保");
        t.addNode("更多", "公积金");
        t.addNode("更多", "统计局");
        t.addNode("更多", "工商年审");
    }

    public static void main(String[] args) {
        System.out.println(t.getParent("更多"));
    }
}