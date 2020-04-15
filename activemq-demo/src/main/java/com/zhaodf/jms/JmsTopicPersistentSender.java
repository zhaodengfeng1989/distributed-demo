package com.zhaodf.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 类：JmsSender
 *
 * @author zhaodf
 * @date 2019/9/19
 */
public class JmsTopicPersistentSender {
    public static void main(String[] args) {
        //1、创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://172.19.128.78:6161");
        Connection connection = null;
        try {
            //2、新建一个连接
            connection = connectionFactory.createConnection();
            //3、连接启动
            connection.start();
            //4、创建会话 第一个参数：是否在事务中去处理， 第二个参数：应答模式
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //5、创建一个目标队列，存在则不会创建
            Destination destination = session.createTopic("first-topic");
            //6、创建消息生产者，并生产到指定的队列中
            MessageProducer messageProducer = session.createProducer(destination);
            //7、创建消息
            TextMessage message = session.createTextMessage("hello,zhaodf,welcome!"+System.currentTimeMillis());
            //设置消息属性
            message.setStringProperty("key","value");
            //8、发送消息
            messageProducer.send(message);
            //9、会话提交
            session.commit();
            //10、会话关闭
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    //11、关闭连接
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
