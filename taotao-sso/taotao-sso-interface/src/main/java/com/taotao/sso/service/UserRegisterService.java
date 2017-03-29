package com.taotao.sso.service;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

/**
 * Created by lee on 2017/3/26.
 */
@SuppressWarnings(value = "all")
public interface UserRegisterService {
    /**
     * 用户数据校验
     * @param parm
     * @param type
     * @return TaotaoResult
     */
    TaotaoResult checkUserInfo(String parm,Integer type);

    /**
     * 用户注册
     * @param user
     * @return TaotaoResult
     */
    TaotaoResult createUser(TbUser user);
}
