package com.abc12366.uc.service;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-18
 */



import com.abc12366.uc.model.LotteryActivity;
import com.abc12366.uc.model.bo.LotteryActivityBO;
import java.util.List;
import java.util.Map;

public interface LotteryActivityService {
    List<LotteryActivityBO> selectList(Map map);
    LotteryActivityBO selectOne(String id);
    LotteryActivityBO insert(LotteryActivityBO lotteryActivityBO);
    LotteryActivityBO update(LotteryActivityBO lotteryActivityBO, String id);
    boolean delete(String id);

}
