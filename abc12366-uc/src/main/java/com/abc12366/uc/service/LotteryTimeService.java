package com.abc12366.uc.service;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-13
 */



import com.abc12366.uc.model.LotteryTime;
import com.abc12366.uc.model.bo.LotteryTimeBO;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LotteryTimeService {
    List<LotteryTimeBO> selectList(Map map);
    public LotteryTimeBO findbyTime(Date date);
    LotteryTimeBO selectOne(String id);
    LotteryTimeBO insert(LotteryTimeBO lotteryTimeBO);
    LotteryTimeBO update(LotteryTimeBO lotteryTimeBO, String id);
    boolean delete(String id);

}
