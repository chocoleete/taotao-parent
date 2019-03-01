package com.taotao.sso.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 登录注册页面展示
 * Created by lee on 2017/3/29.
 */
@SuppressWarnings(value = "all")
@Controller(value = "pageController")
public class PageController {
    /**
     * 显示注册页面
     *
     * @return
     */
    @RequestMapping(value = "/page/register")
    public String showRegister() {
        return "register";
    }

    /**
     * 显示登录页面
     *
     * @param redirect
     * @param model
     * @return
     */
    @RequestMapping(value = "/page/login")
    public String showLogin(String redirect, Model model) {
        //将redirect传递到页面
        model.addAttribute("redirect", redirect);
        return "login";
    }
}
