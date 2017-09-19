package com.abc12366.uc.service.impl;

import com.abc12366.gateway.exception.ServiceException;
import com.abc12366.gateway.util.Utils;
import com.abc12366.uc.config.SpringCtxHolder;
import com.abc12366.uc.mapper.db1.ActivityMapper;
import com.abc12366.uc.mapper.db2.ActivityRoMapper;
import com.abc12366.uc.model.weixin.WxActivity;
import com.abc12366.uc.model.weixin.WxLotteryLog;
import com.abc12366.uc.model.weixin.WxRedEnvelop;
import com.abc12366.uc.model.weixin.bo.redpack.ReceiveRedPack;
import com.abc12366.uc.model.weixin.bo.redpack.SendRedPack;
import com.abc12366.uc.model.weixin.bo.redpack.WxLotteryBO;
import com.abc12366.uc.service.IActivityService;
import com.abc12366.uc.util.wx.SignUtil;
import com.abc12366.uc.util.wx.WechatUrl;
import com.abc12366.uc.util.wx.WxConnectFactory;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 11:24 AM
 * @since 1.0.0
 */
@Service
public class ActivityService implements IActivityService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ActivityService.class);

    @Autowired
    private ActivityMapper activityMapper;

    @Autowired
    private ActivityRoMapper activityRoMapper;

    @Override
    public List<WxActivity> selectList(WxActivity activity, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return activityRoMapper.selectList(activity);
    }

    @Override
    public WxActivity selectOne(String id) {
        return activityRoMapper.selectOne(id);
    }

    @Override
    public WxActivity insert(WxActivity activity) {
        activity.setId(Utils.uuid());
        Date now = new Date();
        activity.setLastUpdate(now);
        activity.setCreateTime(now);
        activityMapper.insert(activity);
        return activity;
    }

    @Override
    public WxActivity update(WxActivity activity) {
        activity.setLastUpdate(new Date());
        activityMapper.update(activity);
        return activity;
    }

    @Override
    public void delete(String id) {
        activityMapper.delete(id);
    }

    /**
     * 生成口令
     */
    @Override
    public WxRedEnvelop generateSecret(String activityId) {
        WxActivity activity = selectOne(activityId);
        if (activity != null) {
            if (!activity.getStatus()) { // 活动是否激活
                throw new ServiceException(6001);
            }
            Date now = new Date();
            if (now.before(activity.getStartTime()) || now.after(activity.getEndTime())) { // 活动是否在有效期内
                throw new ServiceException(6002);
            }
            WxRedEnvelop redEnvelop = new WxRedEnvelop.Builder()
                    .id(Utils.uuid())
                    .secret(secretRule(activity.getRuleType(), activity.getRule()))
                    .createTime(new Date())
                    .activityId(activity.getId())
                    .startTime(activity.getStartTime())
                    .endTime(activity.getEndTime())
                    .amount(activity.getAmount())
                    .amountType(activity.getAmountType())
                    .probability(activity.getProbability())
                    .build();
            activityMapper.generateSecret(redEnvelop);
            return redEnvelop;
        }
        return null;
    }

    /**
     * 抽奖
     */
    @Override
    public WxRedEnvelop lottery(WxLotteryBO lotteryBO) {
        LOGGER.info("记录抽奖日志");
        WxLotteryLog lotteryLog = new WxLotteryLog.Builder()
                .id(Utils.uuid())
                .activityId(lotteryBO.getActivityId())
                .openId(lotteryBO.getOpenId())
                .secret(lotteryBO.getSecret())
                .createTime(new Date())
                .build();
        activityMapper.insertLotteryLog(lotteryLog);

        LOGGER.info("查询今天参与次数,活动:{}, openId:{}", lotteryBO.getActivityId(), lotteryBO.getOpenId());
        List<WxLotteryLog> lotteryLogs = activityRoMapper.selectLotteryLogList(lotteryLog);
        WxActivity activity = activityRoMapper.selectOne(lotteryBO.getActivityId());
        if (lotteryLogs.size() > activity.getTimes()) {
            throw new ServiceException(6006);
        }
        List<WxRedEnvelop> dataList = selectRedEnvelop(lotteryBO.getActivityId(), lotteryBO.getSecret());

        if (dataList == null || dataList.size() < 1) {
            throw new ServiceException(6003);
        }
        WxRedEnvelop redEnvelop = dataList.get(0);// 取第一条记录
        Date now = new Date();
        LOGGER.info("活动是否在有效期内");
        if (now.before(redEnvelop.getStartTime()) || now.after(redEnvelop.getEndTime())) {
            throw new ServiceException(6002);
        }
        LOGGER.info("是否超出红包总数");
        if (isOverRedEnvelopCount(activity.getNum(), lotteryBO.getActivityId())) {
            throw new ServiceException(6005);
        }
        String probabilityStr = redEnvelop.getProbability();
        if(probabilityStr.contains("%")) {
            probabilityStr = probabilityStr.replaceAll("%", "");
            Double probability = Double.valueOf(probabilityStr) / 100;
            LOGGER.info("开始抽奖");
            if (inProbability(probability)) { // 中奖
                LOGGER.info("中奖:", redEnvelop.getSecret());
                redEnvelop.setSendAmount(amountRule(redEnvelop.getAmountType(), redEnvelop.getAmount()));

                redEnvelop.setSendStatus("0"); // 已中奖未发送
                redEnvelop.setSendTime(new Date());
                redEnvelop.setSendTimes(1);
                redEnvelop.setReceiveStatus("0"); // 已中奖未领取

                LOGGER.info("发送微信红包");
                sendRedPack(lotteryBO, activity, redEnvelop);
            } else { // 未中奖
                LOGGER.info("未中奖:", redEnvelop.getSecret());
                redEnvelop.setReceiveStatus("2");
                redEnvelop.setReceiveTime(new Date());
            }
            redEnvelop.setOpenId(lotteryBO.getOpenId());
            activityMapper.updateRedEnvelop(redEnvelop);
        } else {
            throw new ServiceException(5000);
        }
        return redEnvelop;
    }

    @Override
    public List<WxRedEnvelop> selectRedEnvelopList(WxRedEnvelop redEnvelop, int page, int size) {
        PageHelper.startPage(page, size, true).pageSizeZero(true).reasonable(true);
        return activityRoMapper.selectRedEnvelopList(redEnvelop);
    }

    /**
     * 查询未抽奖的口令，过滤已中奖、已抽奖的数据
     */
    public List<WxRedEnvelop> selectRedEnvelop(String activityId, String secret) {
        WxRedEnvelop redEnvelop = new WxRedEnvelop.Builder()
                .activityId(activityId)
                .secret(secret)
                .build();
        return activityRoMapper.selectRedEnvelop(redEnvelop);
    }

    /**
     * 发送红包
     */
    private void sendRedPack(WxLotteryBO lotteryBO, WxActivity activity, WxRedEnvelop redEnvelop) {
        SendRedPack srp = new SendRedPack.Builder()
                .nonce_str(redEnvelop.getId())
                .mch_billno(String.valueOf(redEnvelop.getCreateTime().getTime()))
                .mch_id(SpringCtxHolder.getProperty("abc.mch_id"))
                .wxappid(SpringCtxHolder.getProperty("abc.appid"))
                .send_name(SpringCtxHolder.getProperty("abc.send_name"))
                .re_openid(lotteryBO.getOpenId())
                .total_amount(Integer.parseInt(String.valueOf(redEnvelop.getSendAmount() * 100)))
                .total_num(1)
                .wishing(activity.getWishing())
                .client_ip(getAddress() != null ? getAddress().getHostAddress() : "127.0.0.1")
                .build();
        srp.setSign(SignUtil.signKey(srp));
        for(int i = 1; i <= 4; i++) { // 重发3次
            LOGGER.info("第{}次发送", i);
            ReceiveRedPack rrp = WxConnectFactory.mch_post(WechatUrl.SENDREDPACK, null, srp, ReceiveRedPack.class);
            if (rrp != null && "SUCCESS".equals(rrp.getReturn_code())) { // 发送请求成功
                if ("SUCCESS".equals(rrp.getResult_code())) { // 发红包成功
                    redEnvelop.setSendStatus("1"); // 发送成功
                    redEnvelop.setSendTime(new Date());
                    redEnvelop.setSendTimes(i);
                } else {
                    redEnvelop.setSendStatus("2"); // 发送失败
                    redEnvelop.setSendTime(new Date());
                    redEnvelop.setSendTimes(i);
                }
                break;
            }
            // 第一次过3s重发，第二次6s，第二次9s
            try {
                Thread.sleep(i * 3000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 是否超出红包总数
     */
    private boolean isOverRedEnvelopCount(Integer num, String activityId) {
         return num <= activityRoMapper.queryRedEnvelopCount(activityId);
    }

    /**
     * 是否在概率中
     */
    private boolean inProbability(Double probability) {
        // 概率范围
        final int seed = 10000;
        return new Random().nextInt(seed) < seed * probability;
    }

    /**
     * 生成金额
     */
    private Double amountRule(String amountType, Double amount) {
        if ("1".equals(amountType)) { // 固定金额
            return amount;
        } else { // 随机金额
            if (amount.intValue() == amount) {
                return (double) ThreadLocalRandom.current().nextInt(0, amount.intValue());
            } else {
                DecimalFormat df = new DecimalFormat("#.##");
                return Double.parseDouble(df.format(ThreadLocalRandom.current().nextDouble(0.01, amount)));
            }
        }
    }

    /**
     * 口令生成规则
     */
    private String secretRule(String ruleType, String rule) {
        // 规则1口令格式为：2个大写字母+六位1至9个数字组合的随机字符串，总共8位长度
        if ("1".equals(ruleType)) {
            String random = String.valueOf(new Random().nextInt(1000000));
            if (random.length() < 6) { // 不足6位补0
                for (int i = 0; i < 6 - random.length(); i++) {
                    random = "0" + random;
                }
            }
            return random;
        } else {
            // 规则2口令格式为：管理员自主输入的中文字符串，用#符号分割，
            // 如：艾博克#财税平台#爱我中华#美丽中国，只要匹配其中一个词即可
            return rule;
        }
    }

    /**
     * 获取本机ip
     */
    private static InetAddress getAddress() {
        try {
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces
                    .hasMoreElements(); ) {
                NetworkInterface networkInterface = interfaces.nextElement();
                if (networkInterface.isLoopback() || networkInterface.isVirtual() || !networkInterface.isUp()) {
                    continue;
                }
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
                if (addresses.hasMoreElements()) {
                    return addresses.nextElement();
                }
            }
        } catch (SocketException e) {
            LOGGER.debug("Error when getting host ip address: <{}>.", e.getMessage());
        }
        return null;
    }
}
