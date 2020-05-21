package com.qdw.controller.admin;

import com.github.pagehelper.PageInfo;
import com.qdw.pojo.*;
import com.qdw.service.BlogService;
import com.qdw.service.TagService;
import com.qdw.service.TypeService;
import com.qdw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@CrossOrigin
public class AdminBlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    private UserService userService;

    public static final int PAGE_SIZE = 6;


    @GetMapping("/blogs")
    public String blogs(@RequestParam(name = "page",defaultValue = "1") int page,BlogQurey blogQurey, Model model){
        Map<String,Object> map = new HashMap<>();
        if (!"".equals(blogQurey.getTitle())){
            map.put("title",blogQurey.getTitle());
        }else {
            map.put("title",null);
        }
        map.put("recommend",blogQurey.getRecommend());
        map.put("typeId",blogQurey.getTypeId());
        PageInfo<Blog> pageInfo = blogService.getBlogPageHelper(page,PAGE_SIZE,map);
        model.addAttribute("pageInfo",pageInfo);
//        model.addAttribute("blogQurey",blogQurey);
        List<Type> typeList = typeService.getTypeList();
        model.addAttribute("typeList",typeList);
//        System.out.println("blogQurey"+blogQurey);
        System.out.println(page);
        System.out.println("bolgs");
        return "/admin/blogs";
    }



    //搜索
    @PostMapping("/blogs/search")
    public String search(BlogQurey blogQurey,@RequestParam(name = "page",defaultValue = "1") int page,Model model){
        System.out.println(blogQurey);
        Map<String,Object> map = new HashMap<>();
        if (!"".equals(blogQurey.getTitle())){
            map.put("title",blogQurey.getTitle());
        }else {
            map.put("title",null);
        }
        map.put("recommend",blogQurey.getRecommend());
        map.put("typeId",blogQurey.getTypeId());
//        Pager<Blog> pager = blogService.getBlogPager(page, PAGE_SIZE,map);
        PageInfo<Blog> pageInfo = blogService.getBlogPageHelper(page, PAGE_SIZE,map);
        model.addAttribute("pageInfo",pageInfo);

        return "/admin/blogs :: blogList";
    }

    //进入输入界面
    @GetMapping("/blogs/save")
    public String toSave(Model model){
//        System.out.println(id);
//        List<Type> typeList = typeService.getTypeList();
//        List<Tag> tagList = tagService.getTagList();
//        model.addAttribute("typeList",typeList);
//        model.addAttribute("tagList",tagList);
        addTypesAndTags(model);

        model.addAttribute("blog",new Blog());
        return "/admin/blogs-input";
    }
    //提交
    @PostMapping("blogs/save")
    public String save(@Valid Blog blog, BindingResult bindingResult, RedirectAttributes redirectAttributes){

        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        UserVo user = userService.getUserByUsername(name);
        blog.setAuthorName(user.getNickname());
        blog.setUserVo(user);
        blog.setType(typeService.getTypeById(blog.getType().getId()));
        blog.setTags(tagService.getTagListByIds(blog.getTagIds()));
        System.out.println("blog.getTagIds():"+blog.getTagIds());
        for (Tag tag : blog.getTags()) {
            System.out.println(tag);
        }
        int i=0;
        if (blog.getId()==null){
            i = blogService.addBlog(blog);
        }else {
            i = blogService.editBlog(blog);
        }
        if (i>0){
            redirectAttributes.addFlashAttribute("msg","添加成功");
        }else {
            redirectAttributes.addFlashAttribute("msg","添加失败");
        }

        return "redirect:/admin/blogs";
    }

    private void addTypesAndTags(Model model){
        List<Type> typeList = typeService.getTypeList();
        List<Tag> tagList = tagService.getTagList();
        model.addAttribute("typeList",typeList);
        model.addAttribute("tagList",tagList);
    }

    //进入编辑界面
    @GetMapping("/blogs/edit")
//    public String toedit(@PathVariable int id,Model model){
    public String toedit(@RequestParam int id,Model model){
        addTypesAndTags(model);
        Blog blog = blogService.getBlogById(id);

        blog.initTagIds();
        model.addAttribute("blog",blog);
        System.out.println("TagIds:"+blog.getTagIds());
        System.out.println("typename:"+blog.getType().getName());
        System.out.println("typeid:"+blog.getType().getId());
        System.out.println("content:"+blog.getContent());
        System.out.println("进入input");

        return "/admin/blogs-input";
    }

    @GetMapping("/blogs/delete/{id}")
    public String delete(@PathVariable int id,RedirectAttributes redirectAttributes){
        int i = blogService.deleteBlog(id);
        if (i>0){
            redirectAttributes.addFlashAttribute("msg","删除成功");
        }else {
            redirectAttributes.addFlashAttribute("msg","删除失败");
        }
        return "redirect:/admin/blogs";
    }

}
