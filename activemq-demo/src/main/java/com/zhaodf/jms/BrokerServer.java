package com.zhaodf.jms;

import org.apache.activemq.broker.BrokerService;

/**
 * 类：BrokerServer
 *
 * @author zhaodf
 * @date 2019/9/19
 */
public class BrokerServer {
    public static void main(String[] args) {
        BrokerService brokerService = new BrokerService();
        brokerService.setUseJmx(true);
        try {
            brokerService.addConnector("tcp://localhost:6161");
            brokerService.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
