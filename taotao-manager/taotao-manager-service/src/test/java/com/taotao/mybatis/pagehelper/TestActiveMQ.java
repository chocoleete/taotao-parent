package com.taotao.mybatis.pagehelper;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;

/**
 * Created by lee on 2017/3/19.
 */
@SuppressWarnings(value = "all")
public class TestActiveMQ {
    //生产者
    @Test
    public void testQueueProducer() throws JMSException {
        //1.创建一个连接工程Connectionfactory对象，指定服务的IP及端口号
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        //2.使用connectionFactory对象创建一个connection对象
        Connection connection = connectionFactory.createConnection();
        //3.开启连接，开启start方法
        connection.start();
        /*4.使用connection对象创建一个session对象
        * 两个参数：第一个参数是否开启事务，一般不开启,只有为false时第二个参数才有意义
        * 第二个参数：消息的应答模式：手动应答，自动应答,一般启用自动 应答 */
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.使用session创建一个destination对象，目的地有两个，一个是Queue、一个是topic
        Queue queue = session.createQueue("test-queue");
        //6.使用session创建一个producer对象
        MessageProducer producer = session.createProducer(queue);
        //7.使用producer消息
        TextMessage textMessage = new ActiveMQTextMessage();
        textMessage.setText("使用activemq发送队列消息");
        //TextMessage textMessage1 = session.createTextMessage("使用activemq发送队列消息");
        producer.send(textMessage);
        //8.关闭资源
        producer.close();
        session.close();
        session.close();
    }
}
