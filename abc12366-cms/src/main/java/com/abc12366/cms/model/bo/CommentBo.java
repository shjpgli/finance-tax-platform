package com.abc12366.cms.model.bo;

import com.abc12366.cms.model.Comment;
import com.abc12366.cms.model.CommentExt;

/**
 *
 * CMS评论表
 * add by xieyanmao on 2017-5-2
 *
 **/
public class CommentBo {
    private Comment comment;
    private CommentExt commentExt;

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public CommentExt getCommentExt() {
        return commentExt;
    }

    public void setCommentExt(CommentExt commentExt) {
        this.commentExt = commentExt;
    }
}
