package com.taotao.order.intercepter;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.commom.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 结算时拦截器
 * Created by lee on 2017/3/30.
 */
@SuppressWarnings(value = "all")
public class LoginInterceptor implements HandlerInterceptor {
    //取值
    @Value(value = "${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    @Value(value = "${SSO_URL}")
    private String SSO_URL;

    //注入userLoginService
    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //a)	从cookie中取token。
        String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
        //b)	没有token，需要跳转到登录页面。
        if (StringUtils.isBlank(token)) {
            //http://localhost:8088/page/login
            String url = SSO_URL + "/page/login?redirect=" + request.getRequestURL().toString();
            response.sendRedirect(url);
            //拦截
            return false;
        }
        //c)	有token。调用sso系统的服务，根据token查询用户信息。
        TaotaoResult result = userLoginService.getUserByToken(token);
        TbUser tbUser = null;
        if (result != null && result.getStatus() == 200) {
            tbUser = (TbUser) result.getData();
        } else {
            //d)	如果查不到用户信息。用户登录已经过期。需要跳转到登录页面。
            String url = SSO_URL + "/page/login?redirect=" + request.getRequestURL().toString();
            response.sendRedirect(url);
            //拦截
            return false;
        }
        //e)	查询到用户信息。放行。
        request.setAttribute("user", result.getData());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }
}
