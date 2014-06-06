package cn.mixpay.server.exapmle;
import cn.mixpay.server.ServerUtil;
import cn.mixpay.server.SignatureUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * Created by xyn0563 on 6/5/14.
 * 订单信息签名示例
 */
public class SignOrderExample {
    static{
        try{
            Security.addProvider(new BouncyCastleProvider());
            System.out.println("加载RSA的BC加密算法");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        // mixpay给app分配的key
        final String appKey = "6018092067351529";
        // 您的私钥
        final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKzgKcAVdV938q/fK3NJDpThSvaXJQPooipTqVDFPHK+uhDUWIu0frwBy96lFWPQCLcXvOKhGi/WDmSaYVY8jIp53qm8r5cReAp1TqctV4lrHBxfWc6INeHj1Qk0jBwGhENQk/ySvEnk3/zTKOzWJh8blXLXIYrwD9mVqMBpDyDDAgMBAAECgYAhoTXd/Q6pNL9MQUDFm4evpKgdkkeMHBw32bCNWuEofTva/EQBYWpqDntnY3vmv9iCLab7+1UJyz1firy2tu6ry4+dlu/LdbEHqMwBPfcKHfwT2EG6Q41mA7KMlf6def+fBHDSRvWGFW82oiMVAUSvchr7lELGbarZuyHL1pFOiQJBAN7KHfNhmPoFhvFT6si5PXpl68a2k7HqjLIU23SiIQVK6RgqvNJolqzTKoE8r8AdGx9QDzDZAxuvTsb1JnmGEA0CQQDGpUeah9XIAEBfGWW1UHHe9QDmRoeynm678zdlsMNZy4O0U/1ucdbcnq5uFKglSDuxCvFURPuP1qUSipQVyPAPAkEAgFGjrJGzHZZfYdI2sYPYAA6CHYL9UIVKoitXNzdGk5jQ5sV+2iW5WzOJEMTWaW2aOI/RIA5uNtzjH78FeQsZZQJBAJ7/G9jscIis6tkzkt0Vjo9Ou3GVcnfdp/R4MBcM7M+qvbhQocENDVV5DVS+4/czYdPLVm6E6HWw8F9u3CiztLECQEh+qsa6qkGys2sJeNX0yOzgiqw3Iha1HMpLFIgx+Bo7hmZBtEZGtL1MpdgoYAv7bwHm3oi+FMYR8NmdNC9HrSs=";

        // 订单信息
        // 金额，不能为空
        double amount = 2.00;

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
        String productName = "行扫帚";

        // 其他信息，您可以在这里放一些您所需要的信息
        String extData = "";

        // 1.生成待签名串
        String orderContent = ServerUtil.generateOrderInfoString(amount, appKey, extData, appOrderId, notifyUrl, orderDesc, orderTitle, productDesc,
                productId, productName);

        System.out.println("签名串：" + orderContent);
        // 2.对订单信息进行签名，并进行utf-8编码
        String orderSign = SignatureUtils.sign(orderContent, privateKey);
        String encodedOrderSign = null;
        try {
            encodedOrderSign = URLEncoder.encode(orderSign, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        System.out.println("encode过后的签名值：" + encodedOrderSign);

        // 3.将订单信息以及encode后的签名值传送给APP，在APP中调用Mixpay支付插件进行支付
    }
}
