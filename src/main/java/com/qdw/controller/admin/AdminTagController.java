package com.qdw.controller.admin;

import com.github.pagehelper.PageInfo;
import com.qdw.pojo.Pager;
import com.qdw.pojo.Tag;
import com.qdw.pojo.Type;
import com.qdw.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class AdminTagController {
    @Autowired
    private TagService tagService;
    private final int PAGE_SIZE = 6;
    @GetMapping("/tags")
    public String tags(@RequestParam(name = "page",defaultValue = "1") int page, Model model){
//        Pager<Tag> pager = tagService.getTagPage(page, 5);
        PageInfo<Tag> pageInfo = tagService.getTagsPageHelper(page, PAGE_SIZE);
//        model.addAttribute("pager",pager);
        model.addAttribute("pageInfo",pageInfo);
        return "admin/tags";
    }

    @GetMapping("/tags/add")
    public String toSave(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }
    @PostMapping("/tags/save")
    //校验type对象，bindingResult为校验结果
    public String save(@Valid Tag tag, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        //如果数据库存在同名的
        if (tag1 != null){
            //第一个参数和字段同名
            bindingResult.rejectValue("name","nameError","不能添加重复标签");
        }
        //如果有错误就转发回去
        if (bindingResult.hasErrors()){
            return "admin/tags-input";
        }
        System.out.println(tag.getName());
        tag.setName(tag.getName());
        int i = tagService.addTag(tag);
        if (i <= 0){
//            model.addAttribute("msg","操作失败");
            //重定向带参
            redirectAttributes.addFlashAttribute("msg","添加失败");
        }else {
            redirectAttributes.addFlashAttribute("msg","添加成功");
//            model.addAttribute("msg","操作成功");
        }
        return "redirect:/admin/tags";
    }
    @GetMapping("/tags/edit/{id}")
    public String toEdit(@PathVariable int id,Model model){
        Tag tag = tagService.getTagById(id);
        model.addAttribute("tag",tag);
        return "/admin/tags-input";
    }
    @PostMapping("/tags/save/{id}")
    public String save(@Valid Tag tag, BindingResult bindingResult,@PathVariable int id, RedirectAttributes redirectAttributes){
        Tag tag1 = tagService.getTagByName(tag.getName());
        //如果数据库存在同名的
        if (tag1 != null){
            //第一个参数和字段同名
            bindingResult.rejectValue("name","nameError","请修改分类标签");
        }
        if (bindingResult.hasErrors()){
            return "admin/tags-input";
        }
        int i = tagService.editTag(tag);
        if (i <= 0){
            redirectAttributes.addFlashAttribute("msg","更新失败");
        }else {
            redirectAttributes.addFlashAttribute("msg","更新成功");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/delete/{id}")
    public String toSave(@PathVariable("id") int id){
        int i = tagService.deletTag(id);
        if (i == 1){
            System.out.println("成功");
        }
        return "redirect:/admin/tags";
    }




}
