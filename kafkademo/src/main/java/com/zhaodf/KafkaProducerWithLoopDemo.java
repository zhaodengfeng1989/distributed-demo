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
public class KafkaProducerWithLoopDemo implements Runnable{
    private final KafkaProducer<Integer,String > producer;

    /***
     * http://kafka.apachecn.org/documentation.html#producerconfigs
     */
    public KafkaProducerWithLoopDemo(){
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


    @Override
    public void run() {
        int messageNo = 0;
        while (true){
            String message = "message-"+messageNo;
            producer.send(new ProducerRecord<Integer, String>(KafkaProperties.TOPIC, messageNo, message), new Callback() {
                @Override
                public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                    System.out.println("message send to:["+recordMetadata.partition()+"],offset:["+recordMetadata.offset()+"]");
                }
            }) ;
            ++messageNo;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        KafkaProducerWithLoopDemo producerDemo = new KafkaProducerWithLoopDemo();
        new Thread(producerDemo).start();
        System.in.read();
    }


}
