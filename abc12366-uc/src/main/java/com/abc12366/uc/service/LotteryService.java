package com.abc12366.uc.service;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */



import com.abc12366.uc.model.Lottery;
import com.abc12366.uc.model.bo.LotteryBO;
import java.util.List;
import java.util.Map;

public interface LotteryService {
    List<LotteryBO> selectList(Map map);
    LotteryBO selectOne(String id);
    LotteryBO insert(LotteryBO lotteryBO);
    LotteryBO update(LotteryBO lotteryBO, String id);
    boolean delete(String id);

}
