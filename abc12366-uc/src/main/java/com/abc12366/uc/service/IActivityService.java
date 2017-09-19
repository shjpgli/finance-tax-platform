package com.abc12366.uc.service;

import com.abc12366.uc.model.weixin.WxActivity;
import com.abc12366.uc.model.weixin.WxRedEnvelop;
import com.abc12366.uc.model.weixin.bo.redpack.WxLotteryBO;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 11:15 AM
 * @since 1.0.0
 */
public interface IActivityService {
    // 查看红包活动列表
    List<WxActivity> selectList(WxActivity activity, int page, int size);

    // 查看红包活动
    WxActivity selectOne(String id);

    // 新增活动
    WxActivity insert(WxActivity activity);

    // 修改活动
    WxActivity update(WxActivity activity);

    // 删除活动
    void delete(String id);

    // 生成红包口令
    WxRedEnvelop generateSecret(String activityId);

    // 抽奖
    WxRedEnvelop lottery(WxLotteryBO lotteryBO);

    List<WxRedEnvelop> selectRedEnvelopList(WxRedEnvelop redEnvelop, int page, int size);
}
