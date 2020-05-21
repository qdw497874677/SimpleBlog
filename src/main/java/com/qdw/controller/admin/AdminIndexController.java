package com.qdw.controller.admin;

import com.qdw.pojo.UserVo;
import com.qdw.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminIndexController {
    @Autowired
    private UserService userService;
    @RequestMapping("/index")
    public String index(Model model){
//        userService.selectUserById(1);
//        UserVo user = (UserVo) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        System.out.println(user);
//        System.out.println("???");
        model.addAttribute("msg","test");
//        System.out.println("!!"+SecurityContextHolder.getContext().getAuthentication().getName());
        return "admin/index";
    }
}
