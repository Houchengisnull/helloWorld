package org.hc.learning.net.resttemplate;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//a2498f901aee076499b2018150838ec4appidtestnonce4fve60p48n4vm7hspageindex0pagesizetimestamp2022-07-28 11:05:39updatetimev1.0a2498f901aee076499b2018150838ec4
//a2498f901aee076499b2018150838ec4v1.0appidtestnonce8fbfoxmtmzb22p6vupdatetimepageindex0pagesizetimestamp2022-07-28 11:06:04a2498f901aee076499b2018150838ec4
public class RestApp {

    /*private static String appid = "test";
    private static String appSecret = "a2498f901aee076499b2018150838ec4";*/

    private static String appid = "b4beccdfe3fc45dda1fe236411f51c12";
    private static String appSecret = "3953d81dd79e4ec49cf5686553c67844";

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(RestTemplateConfig.class);
        RestTemplate t = context.getBean(RestTemplate.class);
        projectSelect(t); // 查询项目
        projectSubContractorSelect(t); // 查询参加单位
        /*projectEmployeeSignSelect(t, "8d2e9956368e4a3eb7743749717e0f3a", "2022-7-28 11:47:21");*/
        /*String idcard = "Gorwd9zhQKwoCzzEJ/cFyITvKC9uoXO14b1ViMW3T74=";
        IvParameterSpec ivParameterSpec = new IvParameterSpec(appSecret.substring(0, 16).getBytes(CharsetUtil.UTF_8));
        byte[] bytes = appSecret.getBytes(CharsetUtil.UTF_8);
        SecretKeySpec aes = new SecretKeySpec(bytes, "AES");
        Cipher instance = Cipher.getInstance("AES/CBC/PKCS5Padding");
        instance.init(Cipher.DECRYPT_MODE, aes, ivParameterSpec);
        String str = StrUtil.str(instance.doFinal(Base64.decode(idcard)), CharsetUtil.UTF_8);
        System.out.println(str);*/
    }

    private static void projectSubContractorSelect(RestTemplate t) {
        HttpHeaders httpHeaders = new HttpHeaders();
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("appid", appid);
        params.add("nonce", RandomUtil.randomString(16));
        params.add("pageIndex", 0);
        params.add("pageSize", "");
        params.add("proCode", "adf3ede28d7e4cbd82a0e4e701d606c6");
        params.add("timestamp", DateUtil.now());
        params.add("updateTime", "");
        params.add("v", "1.0");

        StringBuilder builder = new StringBuilder();
        builder.append(appSecret);
        params.forEach((k, v) -> {
            builder.append(k).append(v.get(0));
        });
        builder.append(appSecret);

        String plain = builder.toString().toLowerCase();
        System.out.println("plain:" + plain);
        String sign = SecureUtil.sha256(plain);
        params.add("sign", sign);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> exchange =
                t.exchange("http://58.22.7.74:8105/share/api/projectSubContractor/projectSubContractorSelect", HttpMethod.POST, entity, String.class);

        System.out.println(exchange.getBody());
    }

    public static void projectSelect(RestTemplate t) {

        HttpHeaders httpHeaders = new HttpHeaders();
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("appid", appid);
        params.add("nonce", RandomUtil.randomString(16));
        params.add("pageIndex", 0);
        params.add("pageSize", "");
        params.add("timestamp", DateUtil.now());
        params.add("updateTime", "");
        params.add("v", "1.0");

        StringBuilder builder = new StringBuilder();
        builder.append(appSecret);
        params.forEach((k, v) -> {
            builder.append(k).append(v.get(0));
        });
        builder.append(appSecret);

        String plain = builder.toString().toLowerCase();
        System.out.println("plain:" + plain);
        String sign = SecureUtil.sha256(plain);
        params.add("sign", sign);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> exchange =
                t.exchange("http://58.22.7.74:8105/share/api/project/projectSelect", HttpMethod.POST, entity, String.class);

        System.out.println(exchange.getBody());
    }

    public static void projectEmployeeSignSelect(RestTemplate t, String proCode, String signTime) {
        HttpHeaders httpHeaders = new HttpHeaders();
        LinkedMultiValueMap<String, Object> params = new LinkedMultiValueMap<>();
        params.add("appid", appid);
        params.add("nonce", RandomUtil.randomString(16));
        params.add("pageIndex", 0);
        params.add("pageSize", "");
        params.add("proCode", proCode);
        params.add("signTime", signTime);
        params.add("timestamp", DateUtil.now());
        params.add("updateTime", "");
        params.add("v", "1.0");

        StringBuilder builder = new StringBuilder();
        builder.append(appSecret);
        params.forEach((k, v) -> {
            builder.append(k).append(v.get(0));
        });
        builder.append(appSecret);

        String plain = builder.toString().toLowerCase();
        System.out.println("plain:" + plain);
        String sign = SecureUtil.sha256(plain);
        params.add("sign", sign);

        HttpEntity<LinkedMultiValueMap<String, Object>> entity = new HttpEntity<>(params, httpHeaders);
        ResponseEntity<String> exchange =
                t.exchange("http://58.22.7.74:8105/share/api/projectEmployeeSign/projectEmployeeSignSelect", HttpMethod.POST, entity, String.class);

        System.out.println(exchange.getBody());
    }
}
