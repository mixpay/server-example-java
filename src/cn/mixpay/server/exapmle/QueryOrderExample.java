package cn.mixpay.server.exapmle;

import cn.mixpay.server.OrderUtil;
import cn.mixpay.server.SignatureUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.Security;


/**
 * Created by xyn0563 on 6/6/14.
 * 查询订单状态示例
 */
public class QueryOrderExample {

    static{
        try{
            Security.addProvider(new BouncyCastleProvider());
            System.out.println("加载RSA的BC加密算法");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String [] args) {
        // mixpay给app分配的key
        final String appKey = "6018092067351529";
        // 您的私钥
        final String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAKzgKcAVdV938q/fK3NJDpThSvaXJQPooipTqVDFPHK+uhDUWIu0frwBy96lFWPQCLcXvOKhGi/WDmSaYVY8jIp53qm8r5cReAp1TqctV4lrHBxfWc6INeHj1Qk0jBwGhENQk/ySvEnk3/zTKOzWJh8blXLXIYrwD9mVqMBpDyDDAgMBAAECgYAhoTXd/Q6pNL9MQUDFm4evpKgdkkeMHBw32bCNWuEofTva/EQBYWpqDntnY3vmv9iCLab7+1UJyz1firy2tu6ry4+dlu/LdbEHqMwBPfcKHfwT2EG6Q41mA7KMlf6def+fBHDSRvWGFW82oiMVAUSvchr7lELGbarZuyHL1pFOiQJBAN7KHfNhmPoFhvFT6si5PXpl68a2k7HqjLIU23SiIQVK6RgqvNJolqzTKoE8r8AdGx9QDzDZAxuvTsb1JnmGEA0CQQDGpUeah9XIAEBfGWW1UHHe9QDmRoeynm678zdlsMNZy4O0U/1ucdbcnq5uFKglSDuxCvFURPuP1qUSipQVyPAPAkEAgFGjrJGzHZZfYdI2sYPYAA6CHYL9UIVKoitXNzdGk5jQ5sV+2iW5WzOJEMTWaW2aOI/RIA5uNtzjH78FeQsZZQJBAJ7/G9jscIis6tkzkt0Vjo9Ou3GVcnfdp/R4MBcM7M+qvbhQocENDVV5DVS+4/czYdPLVm6E6HWw8F9u3CiztLECQEh+qsa6qkGys2sJeNX0yOzgiqw3Iha1HMpLFIgx+Bo7hmZBtEZGtL1MpdgoYAv7bwHm3oi+FMYR8NmdNC9HrSs=";

        Long mixpayOrderId = new Long("1406060000000000247");
        StringBuffer raw = new StringBuffer("");

        // 1. 组装参数签名串，app_key=xxx&order_id=xxx，顺序不能改变
        raw.append("app_key=" + appKey + "&");
        raw.append("order_id=" + mixpayOrderId);
        System.out.println("待签名串：" + raw.toString());
        // 2. 对参数进行签名
        String sign = SignatureUtils.sign(raw.toString(), privateKey);
        String orderInfo = "";

        // 3.对签名结果进行encode
        try {
            sign = URLEncoder.encode(sign, "utf-8");
            System.out.println("encoded签名：" + sign);
            // 4.查询订单信息
            orderInfo = OrderUtil.queryOrder(appKey, mixpayOrderId, sign, true);
            // 5.得到订单信息，进行后续处理
            System.out.print("定单信息：" + orderInfo);

        } catch (UnsupportedEncodingException e) {
            sign = null;
            e.printStackTrace();
        }
        if (sign == null) {
            System.out.print("签名错误");
        }

    }
}
