package com.qdw.service;

import com.qdw.mapper.CommentMapper;
import com.qdw.pojo.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Override
    public List<Comment> getCommentsByBlogId(Integer blogId) {
        return commentMapper.queryCommentListByBlogId(blogId);
    }

    @Override
    public Comment saveComment(Comment comment) {
        Integer parentId = comment.getParentComment().getId();
        if (parentId!=-1){
            comment.setParentComment(commentMapper.queryCommentById(parentId));
        }else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        int i = commentMapper.insertComment(comment);
        return commentMapper.queryCommentById(i);
    }
}
