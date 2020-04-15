package com.zhaodf;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.io.IOException;
import java.util.Properties;

/**
 * 类：KafkaProducer
 *
 * @author zhaodf
 * @date 2019/10/18
 */
public class KafkaProducerDemo {
    private final KafkaProducer<Integer,String > producer;

    /***
     * http://kafka.apachecn.org/documentation.html#producerconfigs
     */
    public KafkaProducerDemo(){
        Properties prop = new Properties();
        //这是一个用于建立初始连接到kafka集群的"主机/端口对"配置列表。
        prop.put("bootstrap.servers",KafkaProperties.KAFKA_BROKER_LIST);
        //关键字的序列化类，实现以下接口： org.apache.kafka.common.serialization.Serializer 接口。
        prop.put("key.serializer","org.apache.kafka.common.serialization.IntegerSerializer");
        //值的序列化类，实现以下接口： org.apache.kafka.common.serialization.Serializer 接口。
        prop.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        //发出请求时传递给服务器的 ID 字符串。这样做的目的是为了在服务端的请求日志中能够通过逻辑应用名称来跟踪请求的来源，而不是只能通过IP和端口号跟进。
        prop.put("client.id","producerDemo");
        this.producer = new KafkaProducer<Integer, String>(prop);
    }

    public void sendMsg(){
        producer.send(new ProducerRecord<Integer, String>(KafkaProperties.TOPIC, 1, "message"), new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                System.out.println("message send to:["+recordMetadata.partition()+"],offset:["+recordMetadata.offset()+"]");
            }
        });
    }

    public static void main(String[] args) throws IOException {
        KafkaProducerDemo producerDemo = new KafkaProducerDemo();
        producerDemo.sendMsg();
        System.in.read();
    }
}
