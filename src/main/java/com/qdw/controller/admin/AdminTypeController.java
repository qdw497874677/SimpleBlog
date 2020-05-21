package com.qdw.controller.admin;

import com.qdw.pojo.Pager;
import com.qdw.pojo.Type;
import com.qdw.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminTypeController {
    public static final int PAGE_SIZE = 6;
    @Autowired
    private TypeService typeService;

//    @GetMapping("/types")
//    public String types( Model model){
//        Pager<Type> pager = typeService.getTypeByPage(1, 10);
//        model.addAttribute("pager",pager);
//        return "admin/types";
//    }
    @GetMapping("/types")
    public String typesPage(@RequestParam(value = "page",defaultValue = "1") int page
            , Model model){
        Pager<Type> pager = typeService.getTypeByPage(page, PAGE_SIZE);
        model.addAttribute("pager",pager);
        return "admin/types";
    }

//    @PostMapping("/types")
//    public String updataType(Model model){
//        model.addAttribute("msg",1);
//        return "admin/types/{1}/{10}";
//    }

    @GetMapping("/types/save")
    public String toSave(Model model){
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }


    @PostMapping("/types/save")
    //校验type对象，bindingResult为校验结果
    public String save(@Valid Type type, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        Type type1 = typeService.getTypeByName(type.getName());
        //如果数据库存在同名的
        if (type1 != null){
            //第一个参数和字段同名
            bindingResult.rejectValue("name","nameError","不能添加重复分类");
        }
        if (bindingResult.hasErrors()){
            return "admin/types-input";
        }

        System.out.println(type.getName());
        type.setName(type.getName());
        int i = typeService.saveType(type);
        if (i <= 0){
//            model.addAttribute("msg","操作失败");
            //重定向带参
            redirectAttributes.addFlashAttribute("msg","添加失败");
        }else {
            redirectAttributes.addFlashAttribute("msg","添加成功");
//            model.addAttribute("msg","操作成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/edit/{id}")
    public String toEdit(@PathVariable int id,Model model){
        Type type = typeService.getTypeById(id);
        model.addAttribute("type",type);
        return "/admin/types-input";
    }

    //和save共用一个页面
    @PostMapping("/types/save/{id}")
    public String toEdit(@Valid Type type, BindingResult bindingResult,@PathVariable int id, RedirectAttributes redirectAttributes){
        Type type1 = typeService.getTypeByName(type.getName());
        //如果数据库存在同名的
        if (type1 != null){
            //第一个参数和字段同名
            bindingResult.rejectValue("name","nameError","请修改分类名称");
        }
        if (bindingResult.hasErrors()){
            return "admin/types-input";
        }
        int i = typeService.updateType(type);
        if (i <= 0){
//            model.addAttribute("msg","操作失败");
            //重定向带参
            redirectAttributes.addFlashAttribute("msg","更新失败");
        }else {
            redirectAttributes.addFlashAttribute("msg","更新成功");
//            model.addAttribute("msg","操作成功");
        }
        return "redirect:/admin/types";

    }


    @GetMapping("/types/delete/{id}")
    public String toSave(@PathVariable("id") int id){
        int i = typeService.deleteType(id);
        if (i == 1){
            System.out.println("成功");
        }
        return "redirect:/admin/types";
    }

}
