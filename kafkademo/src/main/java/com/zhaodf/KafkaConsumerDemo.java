package com.zhaodf;

import kafka.utils.ShutdownableThread;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

/**
 * 类：KafkaProducer
 *
 * @author zhaodf
 * @date 2019/10/18
 */
public class KafkaConsumerDemo extends ShutdownableThread {
    //high level consumer
    //low level consumer
    private final KafkaConsumer<Integer,String> consumer;
    private List<ConsumerRecord> buffer = new ArrayList<ConsumerRecord>();
    private org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());
    /***
     * http://kafka.apachecn.org/documentation.html#consumerconfigs
     */
    public KafkaConsumerDemo() {
        super("KafkaConsumerTest",false);
        Properties prop = new Properties();
        //这是一个用于建立初始连接到kafka集群的"主机/端口对"配置列表。
        prop.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,KafkaProperties.KAFKA_BROKER_LIST);
        //设置消费者所属的组
        prop.put(ConsumerConfig.GROUP_ID_CONFIG,"group1");
        //设置是否自动提交，如果为true，表示消息消费的offset会自动提交
        prop.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"false");
        //设置自动提交为true的情况下，此属性表示提交的间隔时间
        //prop.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"1000");
        //当Kafka中没有初始偏移量或服务器上不存在当前偏移量时该怎么办（例如，该数据已被删除）：
        //earliest：将偏移量自动重置为最早的偏移量
        //latest：自动将偏移量重置为最新偏移量
        //none：如果未找到消费者组的先前偏移量，则向消费者抛出异常
        //anything else：向消费者抛出异常。
        prop.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,"earliest");
        //组管理时，消费者会定时发送心跳信号，如果在超时时间内未收到，需要把该消费者从组内移除，并进行rebalance
        prop.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG,"30000");
        //key值的反序列化器，实现接口 org.apache.kafka.common.serialization.Deserializer
        prop.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.IntegerDeserializer");
        //value的反序列化类，实现接口 org.apache.kafka.common.serialization.Deserializer
        prop.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,"org.apache.kafka.common.serialization.StringDeserializer");

        this.consumer = new KafkaConsumer<Integer, String>(prop);
        //指定消费主体fifth的0分区
        TopicPartition p0 = new TopicPartition(KafkaProperties.TOPIC,0);
        this.consumer.assign(Arrays.asList(p0));
    }

    @Override
    public void doWork() {
        //consumer.subscribe(Collections.singletonList(KafkaProperties.TOPIC));
        ConsumerRecords<Integer, String> records = consumer.poll(1000);
        for (ConsumerRecord record:records) {
            System.out.println("partition：["+record.partition()+"]，receive message：["+record.key()+"-->"+record.value()+"],offset:"+record.offset());
            buffer.add(record);
        }
        if(buffer.size()>4){
            logger.info("begining message commit!");
            consumer.commitSync();
            buffer.clear();
        }
    }

    public static void main(String[] args) throws IOException {
        //KafkaConsumerDemo consumerDemo = new KafkaConsumerDemo();
        System.out.println(Math.abs("group1".hashCode()%50));
        //consumerDemo.start();
    }

}
