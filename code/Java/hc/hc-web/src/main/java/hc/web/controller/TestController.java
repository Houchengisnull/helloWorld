package hc.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public String test(){
        return "测试成功";
    }

    @GetMapping("/call")
    public String callTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.getForEntity("http://127.0.0.1:8090/hc-web/test/", String.class);
        return res.getBody();
    }

}
