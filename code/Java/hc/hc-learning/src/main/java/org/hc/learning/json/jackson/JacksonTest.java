package org.hc.learning.json.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.pojo.Group;
import org.hc.learning.test.TestCase;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * 官网: https://github.com/FasterXML/jackson-docs
 */
@Slf4j
public class JacksonTest extends TestCase {

    /**
     * 常规
     * @throws JsonProcessingException
     */
    @Test
    public void normal() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Group group = new Group();
        Map<String, String> map = mapper.convertValue(group, Map.class);
        log.debug("convert result:{}", mapper.writeValueAsString(map));
    }

    /**
     * map >> jsonNode
     */
    @Test
    public void mapToJsonNode() {
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String, Object> map = new HashMap<>();
        map.put("index", 100);
        map.put("attitude", "Hello world");
        JsonNode jsonNode = mapper.valueToTree(map);
        log.debug("convert result:{}", jsonNode.toString());
    }

    /**
     * 根据序列化前的map key排序
     * @throws JsonProcessingException
     */
    @Test
    public void order() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        Group group = new Group();
        Map<String, String> map = mapper.convertValue(group, Map.class);
        log.debug("convert result:{}", mapper.writeValueAsString(map));
    }

    /**
     * 自定义标准序列化器
     * @throws JsonProcessingException
     */
    @Test
    public void customStdSerializer() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Group.class, new GroupSerializer());
        mapper.registerModule(simpleModule);
        Group group = new Group();
        /*String text = mapper.writeValueAsString(group);*/
        Map<String, String> map = mapper.convertValue(group, Map.class);
        log.debug("convert result:{}", mapper.writeValueAsString(map));
    }

    /**
     * 不打印 value == null的键值对
     * @throws JsonProcessingException
     */
    @Test
    public void notNull() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        Group group = new Group();
        Map<String, String> map = mapper.convertValue(group, Map.class);
        log.debug("convert result:{}", mapper.writeValueAsString(map));
    }

}
