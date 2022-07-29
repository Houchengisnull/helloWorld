package org.hc.learning.hutool;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.http.HttpUtil;
import java.util.Map;
import java.util.TreeMap;

public class HttpUtilTest {

    public static void main(String[] args) {
        Map<String, Object> paramMap = new TreeMap<>();
        paramMap.put("v", "1.0");
        paramMap.put("appid", "test");
        paramMap.put("nonce", RandomUtil.randomString(16));
        paramMap.put("updateTime", "");
        paramMap.put("pageIndex", 0);
        paramMap.put("pageSize", "");
        paramMap.put("timestamp", DateUtil.now());

        String appSecret = "a2498f901aee076499b2018150838ec4";
        StringBuilder builder = new StringBuilder();
        builder.append(appSecret);
        paramMap.forEach((k, v) -> {
            builder.append(k).append(v);
        });
        builder.append(appSecret);
        String plain = builder.toString().toLowerCase();
        System.out.println("plain:" + plain);
        String sign = SecureUtil.sha256(plain);
        paramMap.put("sign", sign);
        String post = HttpUtil.post("http://58.22.7.74:8105/share/api/project/projectSelect", paramMap);
        System.out.println(post);
    }

}
