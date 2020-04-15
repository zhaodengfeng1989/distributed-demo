package com.zhaodf;

/**
 * 类：OrderServiceImpl
 *
 * @author zhaodf
 * @date 2019/8/26
 */
public class OrderQueryServiceImpl implements IOrderQueryService{
    @Override
    public DoOrderResponse doQuryOrder(DoOrderRequest request) {
        System.out.println("doQuryOrder曾经来过："+request.toString());
        DoOrderResponse response = new DoOrderResponse();
        response.setCode("0000");
        response.setMemo("查询处理成功！");
        return response;
    }
}
