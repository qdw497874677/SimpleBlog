package com.qdw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @RequestMapping("/mylogin")
    public String login(){
        return "/admin/login";
    }

}
