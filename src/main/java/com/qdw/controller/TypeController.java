package com.qdw.controller;

import com.github.pagehelper.PageInfo;
import com.qdw.pojo.Blog;
import com.qdw.pojo.Type;
import com.qdw.service.BlogService;
import com.qdw.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class TypeController {
    @Autowired
    private TypeService typeService;
    @Autowired
    private BlogService blogService;


    private final int PAGE_SIZE = 6;

    @GetMapping("/types/{id}")
    public String types(@RequestParam(name = "page",defaultValue = "1") int page,
                        @PathVariable Integer id, Model model){
        List<Type> types = typeService.getTypesOrderByBlogNums(1000);
        //从导航栏链接来的，就默认选最多的那个元素
        if (id == -1){
            id = types.get(0).getId();
        }
        PageInfo<Blog> pageInfo = blogService.getBlogPageHelperByType(page, PAGE_SIZE,id);
        model.addAttribute("types",types);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }
}
