package com.zhaodf;


import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("order-consumer.xml");
        IOrderService service = (IOrderService) context.getBean("orderService");
        DoOrderRequest request = new DoOrderRequest();
        request.setName("zhaodf");
        DoOrderResponse response = service.doOrder(request);
        System.out.println(response.toString());

        IOrderQueryService queryService = (IOrderQueryService) context.getBean("orderQueryService");
        DoOrderRequest request2 = new DoOrderRequest();
        request2.setName("dengyj");
        DoOrderResponse response2 = queryService.doQuryOrder(request2);
        System.out.println(response2.toString());
        System.in.read();
    }
}
