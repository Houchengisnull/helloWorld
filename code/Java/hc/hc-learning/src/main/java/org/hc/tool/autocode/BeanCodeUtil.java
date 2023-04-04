package org.hc.tool.autocode;

import com.alibaba.fastjson.JSONObject;

import java.util.Set;

public class BeanCodeUtil {

    public final static String json2Bean(String jsonString) {
        String br = System.lineSeparator();
        String blank = " ";
        String tab = "\t";
        String dataAnnotation = "@Data";
        String start = "public class Pojo {";
        String fieldPattern = tab + "private %s %s;";
        String end = "}";

        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        StringBuilder builder = new StringBuilder();
        builder.append(dataAnnotation).append(br);
        builder.append(start).append(br);
        Set<String> keySet = jsonObject.keySet();
        for (String key : keySet) {
            Object o = jsonObject.get(key);
            builder.append(String.format(fieldPattern, o.getClass().getSimpleName(), key)).append(br);
        }
        builder.append(end);
        return builder.toString();
    }

    public static void main(String[] args) {
        System.out.println(json2Bean("{\"age\":11,\"name\":\"Hugo\",\"grade\":{},\"list\":[]}"));
    }
}
