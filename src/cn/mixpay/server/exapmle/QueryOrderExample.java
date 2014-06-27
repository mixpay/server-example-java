package cn.mixpay.server.exapmle;

import cn.mixpay.server.OrderTool;


/**
 * Created by xyn0563 on 6/6/14.
 * 查询订单信息示例
 */
public class QueryOrderExample {

    public static void main(String [] args) {
        Long mixpayOrderId = new Long("140627000949296031");
        String orderInfoString = OrderTool.queryOrder(MerchantConfig.APP_KEY, mixpayOrderId, MerchantConfig.PRIVATE_KEY, false);
        System.out.println("定单信息:" + orderInfoString);
    }
}
