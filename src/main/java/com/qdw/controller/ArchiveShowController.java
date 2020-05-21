package com.qdw.controller;

import com.qdw.pojo.Blog;
import com.qdw.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@Controller
public class ArchiveShowController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        Map<String, List<Blog>> archiveMap = blogService.archiveBlog();
        Integer blogsCount = blogService.countBlogs();
        model.addAttribute("archiveMap",archiveMap);
        model.addAttribute("blogsCount",blogsCount);
        return "archives";
    }
}
