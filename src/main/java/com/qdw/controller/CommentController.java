package com.qdw.controller;

import com.qdw.pojo.Comment;
import com.qdw.service.BlogService;
import com.qdw.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;
    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Integer blogId, Model model){
        List<Comment> comments = commentService.getCommentsByBlogId(blogId);
        model.addAttribute("comments",comments);
        return "blog::commentList";
//        return "blog";
    }

    @PostMapping("/comments")
    public String post(Comment comment){
        System.out.println("comments");
        comment.setBlog(blogService.getBlogById(comment.getBlog().getId()));
//        commentService.saveComment(comment);
        comment.setAvatar(avatar);
        commentService.saveComment(comment);
        return "redirect:/comments/"+comment.getBlog().getId();
    }
}
