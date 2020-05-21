package com.qdw.controller;

import com.qdw.pojo.Blog;
import com.qdw.pojo.Type;
import com.qdw.service.BlogService;
import com.qdw.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ManageBlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;

    @GetMapping("/manageBlog")
    public String toManageBlog(Model model){
        List<Blog> blogList = blogService.getBlogList();
        model.addAttribute("blogList",blogList);
        return "admin/blogs";
    }


    @GetMapping("/updataBlog/{id}")
    public String toUpdataBlog(@PathVariable("id")int id,Model model){
        Blog blog = blogService.getBlogById(id);
        model.addAttribute("blog",blog);
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("typeList",typeList);
        return "updataBlog";
    }
    @PostMapping("/updataBlog/{id}")
    public String updataBlog(Blog blog,Model model){

        model.addAttribute("blog",blog);

        return "";
    }


    @GetMapping("/admin/index")
    public String adminIndex(){

        return "admin/index";
    }

//    @GetMapping("/index2")
//    public String index2(Model model){
//        System.out.println("11111");
//        List<Blog> blogList = blogService.getBlogListSimple();
//        model.addAttribute("blogList",blogList);
//        List<Type> typeList = typeService.getTypeList();
//        model.addAttribute("typeList",typeList);
//        return "index2";
//    }
}
