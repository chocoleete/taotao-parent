package com.taotao.mybatis.pagehelper;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.junit.Test;

import javax.jms.*;
import java.io.IOException;

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

    //消费者
    @Test
    public void testQueueConsumer() throws JMSException, InterruptedException, IOException {
        //创建一个连接工厂对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        //使用工厂对象创建一个连接
        Connection connection = connectionFactory.createConnection();
        //开启连接
        connection.start();
        //使用连接对象创建一个session对象
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //创建一个destination对象
        Queue queue = session.createQueue("test-queue");
        //使用session对象创建一个消费者
        MessageConsumer consumer = session.createConsumer(queue);
        //使用消费者对象接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                String text = null;
                try {
                    text = textMessage.getText();
                    //打印消息
                    System.out.println("接收的消息为：" + text);
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
        //System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }

    /**
     * topic
     */
    //生产者
    @Test
    public void testTopicProducer() throws JMSException {
        //创建一个connectionFactory对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        //使用connectionFactory对象获取连接connection
        Connection connection = connectionFactory.createConnection();
        //启动connection
        connection.start();
        //使用connection创建一个session对象
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //使用session对象创建一个topic
        Topic topic = session.createTopic("test-topic");
        //使用session创建一个生产者producer,指定目的地是topic
        MessageProducer producer = session.createProducer(topic);
        //使用session创建一个TextMessage对象
        TextMessage textMessage = session.createTextMessage("使用topic发送消息...hello");
        //使用producer发送消息
        producer.send(textMessage);
        //关闭资源
        producer.close();
        session.close();
        connection.close();
    }

    //消费者
    @Test
    public void testTopicConsumer() throws JMSException, IOException {
        //创建一个connectionFactory对象
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.25.129:61616");
        //使用connectionFactory对象获取连接connection
        Connection connection = connectionFactory.createConnection();
        //启动connection
        connection.start();
        //使用connection创建一个session
        Session session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
        //使用session对象创建一个topic
        Topic topic = session.createTopic("test-topic");
        //使用session对象创建一个consumer对象，指定目的地topic
        MessageConsumer consumer = session.createConsumer(topic);
        System.out.println("topic消费者3");
        //使用consumer对象接收消息
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                //向下转型
                TextMessage textMessage = (TextMessage) message;
                String text = null;
                try {
                    text = textMessage.getText();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
                System.out.println("接收topic消息" + text);
            }
        });
        //System.in.read();
        //关闭资源
        consumer.close();
        session.close();
        connection.close();
    }
}