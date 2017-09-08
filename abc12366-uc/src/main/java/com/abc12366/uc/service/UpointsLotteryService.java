package com.abc12366.uc.service;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-05
 */



import com.abc12366.uc.model.UpointsLottery;
import com.abc12366.uc.model.bo.UpointsLotteryBO;
import java.util.List;
import java.util.Map;

public interface UpointsLotteryService {
    List<UpointsLotteryBO> selectList(Map map);
    UpointsLotteryBO selectOne(String id);
    UpointsLotteryBO insert(UpointsLotteryBO upointsLotteryBO);
    UpointsLotteryBO update(UpointsLotteryBO upointsLotteryBO, String id);
    boolean delete(String id);
    void inits( ) ;
    UpointsLotteryBO getval(String userId,Integer point);
}
