package com.jieyou.blog.dao;

import com.jieyou.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author jieyou
 * @date 2020/7/31 - 14:18
 */
public interface CommentRepository extends JpaRepository<Comment,Long>
{
    List<Comment> findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
}
