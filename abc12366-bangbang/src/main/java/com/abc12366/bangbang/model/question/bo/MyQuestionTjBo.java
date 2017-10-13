package com.abc12366.bangbang.model.question.bo;

import com.abc12366.bangbang.model.question.QuestionInvite;
import com.abc12366.bangbang.model.question.QuestionTag;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * 
 * 我的帮帮统计信息
 * 
 **/
@SuppressWarnings("serial")
public class MyQuestionTjBo implements Serializable {

    /**回复次数**int(11)**/
    private Integer answerNum;

    /**点赞次数**int(11)**/
    private Integer likeNum;

	/**评论次数**int(11)**/
	private Integer commentNum;

    /**采纳次数**int(11)**/
    private Integer acceptNum;

    /**勋章数**int(11)**/
    private Integer medalNum;

    /**提问次数**int(11)**/
    private Integer questionNum;

    /**邀我答数量**int(11)**/
    private Integer inviteNum;

    /**我加入的邦派数量**int(11)**/
    private Integer applyQueFactionNum;

    /**我管理的邦派数量**int(11)**/
    private Integer manageQueFactionNum;

    /**我的粉丝数量**int(11)**/
    private Integer fansNum;

    /**我的关注数量**int(11)**/
    private Integer attentionNum;

    /**我的收藏数量**int(11)**/
    private Integer collectNum;

    /**我举报的数量**int(11)**/
    private Integer tipNum;

}
