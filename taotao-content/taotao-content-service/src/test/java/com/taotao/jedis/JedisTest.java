package com.taotao.jedis;

import com.taotao.content.jedis.JedisClient;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by lee on 2017/3/14.
 */
@SuppressWarnings(value = "all")
public class JedisTest {

    //测试单机版的用法
    @Test
    public void testJedisSingle() {
        Jedis jedis = new Jedis("192.168.25.129",6379);
        jedis.set("myTest", "1000");
        String result = jedis.get("myTest");
        System.out.printf(result);
    }

    //测试连接池的使用
    @Test
    public void testJedisPool() {
        //1、创建一个连接池
        JedisPool jedisPool = new JedisPool("192.168.25.129", 6379);
        //2、从连接池获得连接
        Jedis jedis = jedisPool.getResource();
        //3、操作，取值
        String result = jedis.get("myTest");
        System.out.printf(result);
        //4、关闭连接
        jedis.close();
        //5、系统结束前关闭连接池
        jedisPool.close();
    }

    //测试集群的用法
    @Test
    public void testJedisCluster() {
        //连接集群使用JedisCluster对象
        Set<HostAndPort> nodes=new HashSet<>();
        nodes.add(new HostAndPort("192.168.25.129", 7001));
        nodes.add(new HostAndPort("192.168.25.129", 7002));
        nodes.add(new HostAndPort("192.168.25.129", 7003));
        nodes.add(new HostAndPort("192.168.25.129", 7004));
        nodes.add(new HostAndPort("192.168.25.129", 7005));
        nodes.add(new HostAndPort("192.168.25.129", 7006));
        //在系统中可以是单例
        JedisCluster jedisCluster = new JedisCluster(nodes);
        //jedisCluster.set("jedisCluster", "123456");
        String result = jedisCluster.get("jedisClient");
        //String result = jedisCluster.get("jedisCluster");
        System.out.printf(result);
        //系统结束前关闭jedisCluster对象
        jedisCluster.close();
    }

    //测试
    @Test
    public void testJedisClientPool() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        JedisClient jedisClient = context.getBean(JedisClient.class);
        jedisClient.set("jedisClient", "hello");
        String result = jedisClient.get("jedisClient");
        System.out.printf(result);
    }
}
