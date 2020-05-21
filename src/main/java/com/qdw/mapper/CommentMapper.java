package com.qdw.mapper;

import com.qdw.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentMapper {
    Comment queryCommentById(Integer id);
    List<Comment> queryCommentListByBlogId(Integer blogId);
    int insertComment(Comment comment);
}
