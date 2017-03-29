package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录注册页面展示
 * Created by lee on 2017/3/29.
 */
@SuppressWarnings(value = "all")
@Controller(value = "pageController")
public class PageController {
    @RequestMapping(value = "/page/register")
    public String showRegister() {
        return "register";
    }

    @RequestMapping(value = "/page/login")
    public String showLogin() {
        return "login";
    }
}
