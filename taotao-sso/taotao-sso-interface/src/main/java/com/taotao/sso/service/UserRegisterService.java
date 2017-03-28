package com.taotao.sso.service;

import com.taotao.commom.pojo.TaotaoResult;

/**
 * Created by lee on 2017/3/26.
 */
@SuppressWarnings(value = "all")
public interface UserRegisterService {
    /**
     * 参数列表：(String parm,Integer type)
     * 返回值类型：TaotaoResult
     * @param parm
     * @param type
     * @return
     */
    TaotaoResult checkUserInfo(String parm,Integer type);
}
