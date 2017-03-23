package com.taotao.activeMQ;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Created by lee on 2017/3/21.
 */
@SuppressWarnings(value = "all")
public class ActiveMQTest {
    //消费者
    @Test
    public void testQueueConsumer() throws IOException {
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-activeMQ.xml");
        //System.in.read();
    }
}
