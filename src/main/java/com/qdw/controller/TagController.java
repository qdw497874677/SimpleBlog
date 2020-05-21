package com.qdw.controller;

import com.github.pagehelper.PageInfo;
import com.qdw.pojo.Blog;
import com.qdw.pojo.Tag;
import com.qdw.pojo.Type;
import com.qdw.service.BlogService;
import com.qdw.service.TagService;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    private final int PAGE_SIZE = 6;

    @GetMapping("/tags/{id}")
    public String tags(@RequestParam(name = "page",defaultValue = "1") int page,
                        @PathVariable Integer id, Model model){
        List<Tag> tags = tagService.getTagsOrderByBlogNums(1000);
        if (id == -1){
            id = tags.get(0).getId();
        }
        PageInfo<Blog> pageInfo = blogService.getBlogPageHelperByTag(page, PAGE_SIZE,id);
        model.addAttribute("tags",tags);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
