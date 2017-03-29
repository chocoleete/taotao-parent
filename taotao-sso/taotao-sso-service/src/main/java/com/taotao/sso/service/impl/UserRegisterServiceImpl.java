package com.taotao.sso.service.impl;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserRegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
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

    @Override
    public TaotaoResult createUser(TbUser user) {
        //使用TbUser接收提交的申请
        String username = user.getUsername();
        String password = user.getPassword();
        String phone = user.getPhone();
        String email = user.getEmail();
        if (StringUtils.isBlank(username)) {
            return TaotaoResult.build(400, "用户名不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return TaotaoResult.build(400, "密码不能为空");
        }
        //校验数据是否可用
        TaotaoResult result = checkUserInfo(username, 1);
        boolean flag = (boolean) result.getData();
        if (!flag) {
            return TaotaoResult.build(400, "此用户名已经被使用");
        }
        if (StringUtils.isNotBlank(phone)) {
            result = checkUserInfo(phone, 2);
            if (!(boolean) result.getData()) {
                return TaotaoResult.build(400, "此手机号已经被注册");
            }
        }
        if (StringUtils.isNotBlank(email)) {
            result = checkUserInfo(email, 3);
            if (!(boolean) result.getData()) {
                return TaotaoResult.build(400, "此邮箱已经被注册");
            }
        }
        //补全TbUser其他属性
        Date date = new Date();
        user.setCreated(date);
        user.setUpdated(date);
        //给密码加密
        String md5password = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5password);
        //将用户信息插入数据库
        userMapper.insert(user);
        //返回
        return TaotaoResult.ok();
    }
}
