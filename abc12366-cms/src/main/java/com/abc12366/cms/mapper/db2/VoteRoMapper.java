package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Vote;
import com.abc12366.cms.model.VoteResult;

import java.util.List;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-07 4:26 PM
 * @since 1.0.0
 */
public interface VoteRoMapper {
    List<Vote> selectList(Vote vote);

    Vote selectOne(String id);

    // 查询参与人数
    Integer selectResultCount(VoteResult result);
}
