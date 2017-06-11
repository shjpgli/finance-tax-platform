package com.abc12366.cms.service;

import com.abc12366.cms.model.Vote;
import com.abc12366.cms.model.VoteResult;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 投票功能服务类
 *
 * @author lijun <ljun51@outlook.com>
 * @create 2017-06-07 4:19 PM
 * @since 1.0.0
 */
public interface VoteService {
    List<Vote> selectList(Vote vote, int page, int size);

    Vote insert(Vote vote);

    Vote selectOne(String id);

    Vote update(Vote vote);

    void delete(String id);

    List<VoteResult> vote(String voteId, List<VoteResult> resultList, HttpServletRequest request);

    VoteResult vote(VoteResult result, HttpServletRequest request);
}
