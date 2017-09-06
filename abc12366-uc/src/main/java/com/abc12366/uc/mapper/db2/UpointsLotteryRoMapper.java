package com.abc12366.uc.mapper.db2;

/**
 * Admin: lingsuzhi <554600654@qq.com.com>
 * Date: 2017-09-05
 */


import com.abc12366.uc.model.bo.UpointsLotteryBO;
import java.util.List;
import java.util.Map;

public interface UpointsLotteryRoMapper {
    List<UpointsLotteryBO> selectList(Map map);
    UpointsLotteryBO selectOne(String id);
}
