package org.hc.learning.json.fastjson;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.json.pojo.Member;
import org.hc.learning.json.pojo.Page;
import org.hc.learning.test.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * 泛型处理
 */
@Slf4j
public class TypeReferenceTest extends TestCase {

    @Test
    public void 泛型转换() {
        String text = "{\"page\":1, \"size\":100, \"total\":2, \"list\":[{\"id\":\"0\", \"name\":\"Hugo\",\"index\":0}, {\"id\":\"1\", \"name\":\"Lucy\",\"index\":1}]}";
        Page<Member> memberPage = JSONObject.parseObject(text, new TypeReference<Page<Member>>() {});
        Member member = memberPage.getList().get(0);
        Assert.assertEquals(member.getName(), "Hugo");
    }

}
