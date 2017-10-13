package com.abc12366.uc.mapper.db1;

import com.abc12366.uc.model.weixin.WxActivity;
import com.abc12366.uc.model.weixin.WxLotteryLog;
import com.abc12366.uc.model.weixin.WxRedEnvelop;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-09-14 11:25 AM
 * @since 1.0.0
 */
public interface ActivityMapper {
    void insert(WxActivity activity);

    void update(WxActivity activity);

    void delete(String id);

    void generateSecret(WxRedEnvelop redEnvelop);

    void updateRedEnvelop(WxRedEnvelop redEnvelop);

    void insertLotteryLog(WxLotteryLog lotteryLog);

    void batchGenerateSecret(List<WxRedEnvelop> dataList);
}
