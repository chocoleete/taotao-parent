package com.taotao.sso.service;

import com.taotao.commom.pojo.TaotaoResult;

/**
 * Created by lee on 2017/3/28.
 */
@SuppressWarnings(value = "all")
public interface UserLoginService {
    /**
     * 用户登录
     * @param username
     * @param password
     * @return TaotaoResult
     */
    TaotaoResult login(String username, String password);

    /**
     * 根据token查询用户
     * @param token
     * @return TaotaoResult
     */
    TaotaoResult getUserByToken(String token);
}
