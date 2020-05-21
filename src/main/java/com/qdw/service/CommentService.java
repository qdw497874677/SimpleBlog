package com.qdw.service;

import com.qdw.pojo.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsByBlogId(Integer blogId);
    Comment saveComment(Comment comment);

}
