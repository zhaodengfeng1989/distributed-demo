package com.zhaodf;

import java.util.concurrent.TimeUnit;

/**
 * 类：OrderServiceImpl
 *
 * @author zhaodf
 * @date 2019/8/26
 */
public class OrderServiceImpl2 implements IOrderService{
    @Override
    public DoOrderResponse doOrder(DoOrderRequest request) {
        System.out.println("版本2曾经来过："+request.toString());
        DoOrderResponse response = new DoOrderResponse();
        response.setCode("0000");
        response.setMemo("版本2，处理成功！");
        return response;
    }
}
