package com.taotao.mybatis.pagehelper;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

import javax.jms.*;

/**
 * Created by lee on 2017/3/21.
 */
@SuppressWarnings(value = "all")
public class TestSpringActiveMQ {
    //生产者
    @Test
    public void testQueueProducer() {
        //初始化spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/spring/applicationContext-activeMQ.xml");
        //从spring容器中获取jmsTemplate对象
        JmsTemplate jmsTemplate = applicationContext.getBean(JmsTemplate.class);
        //从spring容器中获取destination对象
        Queue queue = applicationContext.getBean(Queue.class);
        /*发送消息
        * 第一个参数，指定发送的目的地
        * 第二个参数，消息的构造器对象*/
        jmsTemplate.send((Destination) queue, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                //发送消息的内容
                TextMessage textMessage = session.createTextMessage("使用spring和activeMQ整合发送queue消息:再发一条消息");
                return textMessage;
            }
        });
     }
}
