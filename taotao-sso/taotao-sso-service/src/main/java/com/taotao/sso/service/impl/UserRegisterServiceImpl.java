package com.taotao.sso.service.impl;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserRegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户注册处理Service
 * Created by lee on 2017/3/26.
 */
@SuppressWarnings(value = "all")
@Service(value = "userRegisterServiceImpl")
public class UserRegisterServiceImpl implements UserRegisterService {
    //注入userMapper
    @Autowired
    private TbUserMapper userMapper;

    @Override
    public TaotaoResult checkUserInfo(String parm, Integer type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //判断要校验的数据类型,1/2/3分别代表username/phone/email
        if (type == 1) {
            criteria.andUsernameEqualTo(parm);
        } else if (type == 2) {
            criteria.andPhoneEqualTo(parm);
        } else if (type == 3) {
            criteria.andEmailEqualTo(parm);
        } else {
            return TaotaoResult.build(400, "非法的参数");
        }
        //执行查询
        List<TbUser> list = userMapper.selectByExample(example);
        //判断list,如果数据库中没有值，或者list为null，则表示该数据没有被占用
        if (list == null || list.size() == 0) {
            return TaotaoResult.ok(true);
        }
        return TaotaoResult.ok(false);
    }
}
