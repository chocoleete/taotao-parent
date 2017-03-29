package com.taotao.sso.service.impl;

import com.taotao.commom.pojo.TaotaoResult;
import com.taotao.commom.utils.JsonUtils;
import com.taotao.jedis.JedisClient;
import com.taotao.mapper.TbUserMapper;
import com.taotao.pojo.TbUser;
import com.taotao.pojo.TbUserExample;
import com.taotao.sso.service.UserLoginService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

/**
 * 用户登录
 * Created by lee on 2017/3/28.
 */
@SuppressWarnings(value = "all")
@Service(value = "userLoginServiceImpl")
public class UserLoginServiceImpl implements UserLoginService {
    //注入userMapper
    @Autowired
    private TbUserMapper userMapper;

    //注入jedisClient
    @Resource(name = "jedisClientPool")
    private JedisClient jedisClient;

    //从配置文件中取值
    @Value(value = "${SESSION_PRE}")
    private String SESSION_PRE;

    @Value(value = "${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public TaotaoResult login(String username, String password) {
        //1、判断用户名密码是否正确。
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> list = userMapper.selectByExample(example);
        if (list == null || list.size() == 0) {
            return TaotaoResult.build(400, "用户名不存在！","notexist");
        }
        //校验密码
        TbUser user = list.get(0);
        if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return TaotaoResult.build(400, "密码错误！","wrong");
        }
        //2、登录成功后生成token。Token相当于原来的jsessionid，字符串，可以使用uuid。
        String token = UUID.randomUUID().toString();
        //3、把用户信息保存到redis。Key就是token，value就是TbUser对象转换成json。
        //4、使用String类型保存Session信息。可以使用“前缀:token”为key
        user.setPassword(null);
        jedisClient.set(SESSION_PRE + ":" + token, JsonUtils.objectToJson(user));
        //5、设置key的过期时间。模拟Session的过期时间。一般半个小时。
        jedisClient.expire(SESSION_PRE + ":" + token, SESSION_EXPIRE);
        //6、返回TaotaoResult包装token。
        return TaotaoResult.ok(token);
    }

    /**
     * 根据token查询用户信息
     * @param token
     * @return TaotaoResult
     */
    @Override
    public TaotaoResult getUserByToken(String token) {
        //1、根据token查询redis
        String json = jedisClient.get(SESSION_PRE + ":" + token);
        //判断json是否为空
        if (StringUtils.isBlank(json)) {
            //2、查询不到，返回用户已过期
            return TaotaoResult.build(400, "用户登录已经过期，请重新登录！");
        }
        //3、如果查询到json，说明用户已经登录，重置token的过期时间
        jedisClient.expire(SESSION_PRE + ":" + token, SESSION_EXPIRE);
        //4、将json转换成TbUser对象，然后使用TaotaoResult包装并返回
        TbUser tbUser = JsonUtils.jsonToPojo(json, TbUser.class);
        return TaotaoResult.ok(tbUser);
    }
}
