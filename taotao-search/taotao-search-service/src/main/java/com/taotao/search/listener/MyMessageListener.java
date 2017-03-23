package com.taotao.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * 接收activeMQ队列消息的监听器
 * Created by lee on 2017/3/21.
 */
@SuppressWarnings(value = "all")
public class MyMessageListener implements MessageListener {
    @Override
    public void onMessage(Message message) {
        //取消息内容
        String text = null;
        try {
            TextMessage textMessage = (TextMessage) message;
            text = textMessage.getText();
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("接收到的消息:"+text);
        //其他业务逻辑
    }
}
