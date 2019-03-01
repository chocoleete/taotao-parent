package com.taotao.sso.controller;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.commom.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserLoginService;
import com.taotao.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户管理Controller
 * Created by lee on 2017/3/26.
 */
@SuppressWarnings(value = "all")
@Controller(value = "userController")
public class UserController {
    // 注入userRegisterService
    @Resource(name = "userRegisterService")
    private UserRegisterService userRegisterService;

    // 注入userLoginService
    @Resource(name = "userLoginService")
    private UserLoginService userLoginService;

    // 取值
    @Value(value = "${COOKIE_TOKEN_KEY}")
    private String COOKIE_TOKEN_KEY;

    /**
     * 数据校验
     *
     * @param param
     * @param type
     * @return TaotaoResult
     */
    @RequestMapping(value = "/user/check/{param}/{type}", method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult checkUserInfo(@PathVariable(value = "param") String param,
                                      @PathVariable(value = "type") Integer type) {
        TaotaoResult result = userRegisterService.checkUserInfo(param, type);
        return result;
    }

    /**
     * 用户注册
     * 页面请求url:/user/register
     *
     * @param user
     * @return TaotaoResult
     */
    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult register(TbUser user) {
        TaotaoResult result = userRegisterService.createUser(user);
        return result;
    }

    /**
     * 用户登录
     * 页面请求url:/user/login
     *
     * @param username
     * @param password
     * @param request
     * @param response
     * @return TaotaoResult
     */
    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password,
                              HttpServletRequest request, HttpServletResponse response) {
        // 1、接收两个参数。
        // 2、调用Service进行登录。
        TaotaoResult result = userLoginService.login(username, password);
        // 3、从返回结果中取token，写入cookie。Cookie要跨域。
        String token = result.getData().toString();
        // Cookie二级域名跨域需要设置:
        // 1）setDomain，设置一级域名：
        // .itcatst.cn
        // .taotao.com
        // .taotao.com.cn
        // 2）setPath。设置为“/”
        // 工具类放到taotao-common工程中。
        CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
        // 4、响应数据。Json数据。TaotaoResult，其中包含Token。
        return result;
    }

    /**
     * 根据token查询用户信息
     * 4.1版本后可使用这个方法
     *
     * @param token
     * @return TaotaoResult
     */
    @RequestMapping(value = "/user/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable(value = "token") String token, String callback) {
        TaotaoResult result = userLoginService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {
            // 设置到包装的数据
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            // 设置回调方法
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }

    //传统支持jsonp的方案
	/*@RequestMapping(value="/user/token/{token}", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public String getUserByToken(@PathVariable(value = "token") String token, String callback) {
		TaotaoResult result = userLoginService.getUserByToken(token);
		if (StringUtils.isNotBlank(callback)) {
			//客户端为jsonp请求。需要返回js代码
			String jsonResutl = callback + "(" + JsonUtils.objectToJson(result) + ");";
			return jsonResutl;
		}
		return JsonUtils.objectToJson(result);
	}*/

    /**
     * 页面请求url:/user/logout/{token}
     * @param token
     * @param callback
     * @return
     */
    /*@RequestMapping(value = "/user/logout/{token}",method = RequestMethod.GET)
    @ResponseBody
    public TaotaoResult logout(@PathVariable(value = "token")String token, String callback) {
        return null;
    }*/
}
