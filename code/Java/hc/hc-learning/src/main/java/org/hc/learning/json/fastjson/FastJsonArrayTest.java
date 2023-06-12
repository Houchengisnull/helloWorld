package org.hc.learning.json.fastjson;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.hc.learning.json.pojo.Member;
import org.hc.learning.test.TestCase;
import org.junit.Assert;
import org.junit.Test;
import java.util.List;

@Slf4j
public class FastJsonArrayTest extends TestCase {

    @Test
    public void test() {
        String memberListAsString = "[{\"id\":\"0\", \"name\":\"Hugo\",\"index\":0}, {\"id\":\"1\", \"name\":\"Lucy\",\"index\":1}]";
        List<Member> members = JSONObject.parseArray(memberListAsString, Member.class);
        Assert.assertEquals(members.get(0).getName(), "Hugo");
    }

}
