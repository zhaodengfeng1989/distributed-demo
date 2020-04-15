package com.zhaodf.jms;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 类：JmsSender
 *
 * @author zhaodf
 * @date 2019/9/19
 */
public class JmsTopicPersistentReceiver {
    public static void main(String[] args) {
        //1、创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://172.19.128.78:6161");
        Connection connection = null;
        try {
            //2、新建一个连接
            connection = connectionFactory.createConnection();
            //设置客户端唯一标志
            connection.setClientID("ZHAODF");
            //3、连接启动
            connection.start();
            //4、创建会话 第一个参数：是否在事务中去处理， 第二个参数：应答模式
            Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            //5、创建一个目标队列，存在则不会创建
            Topic topic = session.createTopic("first-topic");
            //6、给对应的客户端创建持久订阅
            MessageConsumer messageConsumer = session.createDurableSubscriber(topic,"ZHAODF");
            //7、接收消息
            TextMessage message = (TextMessage) messageConsumer.receive();
            System.out.println(message.getText());
            //8、会话提交
            session.commit();
            //9、会话关闭
            session.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }finally {
            if (connection!=null){
                try {
                    //10、关闭连接
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
