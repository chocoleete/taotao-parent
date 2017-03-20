package com.taotao.search.exception;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lee on 2017/3/19.
 */
@SuppressWarnings(value = "all")
public class GlobalExceptionResolver implements HandlerExceptionResolver {
    private static Logger logger = Logger.getLogger(GlobalExceptionResolver.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        //写日志文件
        logger.error("运行时异常",ex);
        //发短信，发邮件
        //发短信：第三方运营商的服务。发邮件使用jmail包。
        //跳转到错误页面
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error/exception");
        modelAndView.addObject("message", "您的网络异常，请稍候再试...");
        return modelAndView;
    }
}
