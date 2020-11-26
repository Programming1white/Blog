package com.jieyou.blog.service;

import com.jieyou.blog.po.Comment;

import java.util.List;

/**
 * @author jieyou
 * @date 2020/7/31 - 14:15
 */
public interface CommentService
{
    List<Comment> listCommentByBlodId(Long blogId);

    Comment savaComment(Comment comment);
}

