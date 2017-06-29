package com.abc12366.cms.mapper.db2;

import com.abc12366.cms.model.Vote;
import com.abc12366.cms.model.VoteResult;
import com.abc12366.cms.model.bo.VoteStatAreaBO;
import com.abc12366.cms.model.bo.VoteStatBrowserBO;

import java.util.List;
import java.util.Map;

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

    // 查询浏览人数
    Integer selectHistoryCount(String voteId);

    // 统计浏览器访问情况
    List<VoteStatBrowserBO> statBrowser(String voteId);

    // 统计IP区域访问情况
    List<VoteStatAreaBO> statIpArea(String voteId);
}
