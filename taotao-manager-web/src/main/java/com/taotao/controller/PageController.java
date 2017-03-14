package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
 * Created by lee on 2017/3/10.
 */
@SuppressWarnings(value = "all")
@Controller(value = "pageController")
public class PageController {
    /**
     * 请求url:/
     * 参数：无
     * 返回值类型：String
     */
    @RequestMapping(value="/")
    public String showIndex() {
        return "index";
    }

    @RequestMapping(value = "/{page}")
    public String showPage(@PathVariable(value = "page") String page) {
        return page;
    }
}
