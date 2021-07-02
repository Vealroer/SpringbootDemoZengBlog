package cn.sinap.zengblog.service;


import cn.sinap.zengblog.domain.Comment;

import java.util.List;

public interface CommentService {

    List<Comment> getCommentByBlogId(Long blogId);

    List<Comment> findSecondaryCommentBySelfId(Long id);

    int saveComment(Comment comment);
}
