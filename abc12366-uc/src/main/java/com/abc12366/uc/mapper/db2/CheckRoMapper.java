package com.abc12366.uc.mapper.db2;

import com.abc12366.uc.model.Check;
import com.abc12366.uc.model.CheckRank;

import java.util.List;

/**
 * User: liuguiyao<435720953@qq.com>
 * Date: 2017-08-21
 * Time: 14:43
 */
public interface CheckRoMapper {
    List<Check> selectByOrder(Check check);

    List<Check> selectRecheck(Check checkTmp);

    List<CheckRank> selectRankList();

    List<CheckRank> selectOneRank(String userId);

    List<Check> selectCheckListDesc(String userId);
}
