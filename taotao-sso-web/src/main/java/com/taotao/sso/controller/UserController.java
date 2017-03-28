package com.taotao.sso.controller;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.sso.service.UserRegisterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 用户管理Controller
 * Created by lee on 2017/3/26.
 */
@SuppressWarnings(value = "all")
@Controller(value = "userController")
public class UserController {
    //注入userRegisterService
    @Resource(name = "userRegisterService")
    private UserRegisterService userRegisterService;

    /**
     * 返回值类型：TaotaoResult
     * 参数列表：(String param,Integer type)
     *
     * @param param
     * @param type
     * @return
     */
    @RequestMapping(value = "/user/check/{param}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkUserInfo(@PathVariable(value = "param") String param, @PathVariable(value = "type") Integer type) {
        TaotaoResult result = userRegisterService.checkUserInfo(param, type);
        return result;
    }
}
