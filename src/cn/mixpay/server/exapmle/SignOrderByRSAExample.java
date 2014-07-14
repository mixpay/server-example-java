package cn.mixpay.server.exapmle;

import cn.mixpay.server.RSASignatureTool;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by xyn0563 on 6/5/14.
 * 订单信息签名示例
 */
public class SignOrderByRSAExample {

    public static void main(String[] args) {

        // 金额，不能为空
        int amount = 2;

        // 订单ID，不能为空
        String appOrderId = "order001";

        // 订单标题，不能为空
        String orderTitle = "飞行扫帚两个";

        // 订单描述，不能为空
        String orderDesc = "2014-6-5下单，一红一黑";

        // 后端通知地址，可以为空，为空时默认为您在商户后台为这个app所设置的通知地址
        String notifyUrl = "";

        // 商品描述，可以为空
        String productDesc = "光速2000最新型号，速度超快";

        // 商品ID，可以为空
        String productId = "Product001";

        // 商品名称，可以为空
        String productName = "飞行扫帚";

        // 其他信息，您可以在这里放一些您所需要的信息
        String extData = "";

        String appUserId = "U0001";

        // 1.签名
        String orderSign = RSASignatureTool.signOrderInfo(amount, MerchantConfig.APP_KEY, extData, appOrderId, notifyUrl, orderDesc, orderTitle, productDesc,
                productId, productName, appUserId, MerchantConfig.PRIVATE_KEY);
        String encodedOrderSign = null;
        try {
            encodedOrderSign = URLEncoder.encode(orderSign, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("encode过后的签名值：" + encodedOrderSign);

        // 2.将订单信息以及encode后的签名值传送给APP，在APP中调用Mixpay支付插件进行支付
    }
}
