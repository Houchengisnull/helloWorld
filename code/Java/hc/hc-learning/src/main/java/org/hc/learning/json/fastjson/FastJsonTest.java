package org.hc.learning.json.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.json.pojo.Group;
import org.hc.learning.json.pojo.Member;
import org.hc.learning.test.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

@Slf4j
public class FastJsonTest extends TestCase {

    private final Group group = new Group();
    private final static Integer COUNT = 1;

    @Before
    public void initObj() {
        group.setIndex(1);
        group.setName("青龙学习小组");
        group.setMemberList(new ArrayList<>());
        Member member = new Member();
        member.setIndex(1);
        member.setId("M001");
        member.setName("fastjson");

        group.getMemberList().add(member);
    }

    @Test
    public void testPrintJSON() {
        for (int i = 0; i < COUNT; i++) {
            String text = JSON.toJSONString(group);
            log.debug("JSON.toJSONString(Object): {}", text);
        }
    }

    @Test
    public void testPrintJSONObject (){
        for (int i = 0; i < COUNT; i++) {
            String text = JSONObject.toJSONString(group);
            log.debug("JSONObject.toJSONString(Object) :{}", text);
        }

    }

    /**
     * 主要测试 JSON.toJSONString(Object)\JSONObject.toJSONString(Object)\JSONObject.toJSONString()
     * 三种方法的打印顺序是否相同
     * JSONObject.toJSONString(Object)与JSON.toJSONString(Object)两个方法按照字典序打印
     */
    @Test
    public void testToJSONString(){
        String text = JSON.toJSONString(group);
        JSONObject json = JSON.parseObject(text);
        log.debug("json.toJSONString(): {}",json.toJSONString());
    }

    /**
     * text >> Object 时处理缺省
     */
    @Test
    public void toTextIfAbsent() {
        Group group = new Group();
        group.setMemberList(new ArrayList<>());
        String text = JSON.toJSONString(group
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteNullNumberAsZero);
        log.debug(text);
    }

    /**
     * obj >> text >> Map/JSONObject
     */
    @Test
    public void toJSONObjectIfAbsent() {
        Group group = new Group();
        group.setMemberList(new ArrayList<>());
        String text = JSON.toJSONString(group
                , SerializerFeature.WriteNullStringAsEmpty
                , SerializerFeature.WriteNullNumberAsZero);
        JSONObject jsonObject = JSONObject.parseObject(text);
        log.debug(jsonObject.toJSONString());
    }

    /**
     * 打印null WriteMapNullValue
     * https://www.codenong.com/cs106266288/
     */
    @Test
    public void map(){
        Group group = new Group();
        group.setMemberList(new ArrayList<>());
        String text = JSON.toJSONString(group, SerializerFeature.WriteMapNullValue);
        log.debug(text);
    }

}
