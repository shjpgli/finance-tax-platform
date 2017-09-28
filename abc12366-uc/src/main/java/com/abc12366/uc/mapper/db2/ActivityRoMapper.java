package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.weixin.WxActivity;
import com.abc12366.uc.model.weixin.WxLotteryLog;
import com.abc12366.uc.model.weixin.WxRedEnvelop;
import com.abc12366.uc.model.weixin.bo.redpack.ActivityBO;
import com.abc12366.uc.model.weixin.bo.redpack.SentReceived;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 11:26 AM
 * @since 1.0.0
 */
public interface ActivityRoMapper {
    List<WxActivity> selectList(WxActivity activity);

    WxActivity selectOne(String id);

    List<WxRedEnvelop> selectRedEnvelop(WxRedEnvelop redEnvelop);

    Integer queryRedEnvelopCount(String activityId);

    List<WxLotteryLog> selectLotteryLogList(WxLotteryLog lotteryLog);

    List<WxRedEnvelop> selectRedEnvelopList(WxRedEnvelop redEnvelop);

    WxRedEnvelop selectRedEnvelopOne(String id);

    SentReceived selectSentReceivedCount(String activityId);

    List<ActivityBO> selectSimpleList();
}
