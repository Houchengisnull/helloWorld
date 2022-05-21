package hc.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public String test(){
        try {
            Thread.sleep(5000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "测试成功";
    }

    @GetMapping("/call")
    public String callTest() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> res = restTemplate.getForEntity("http://127.0.0.1:8090/hc-web/test/", String.class);
        return res.getBody();
    }

    /**
     * {*spring}
     */
    @GetMapping("/get/{*path}")
    public String getPathVariable(@PathVariable("path") String path) {
        return path;
    }

    /**
     * {*spring}
     */
    @PostMapping("/post/{*path}")
    public String postPathVariable(@PathVariable("path") String path) {
        return path;
    }

    /**
     * 错误示例:
     * /{method}//{*path} 若使用{*path},则仅支持一个@PathVariables
     * @param uri
     * @param path
     * @return
     */
    /*@GetMapping("/{method}//{*path}")
    public String pathTest(@PathVariable("method")String uri, @PathVariable("path") String path) {
        return path;
    }*/

    /*@GetMapping("get/**")
    public String getPathVariableAll(){
        return "request **";
    }*/

    /**
     * Not supported by "spring.mvc.pathmatch.matching-strategy=ant-path-matche"
      */
    @GetMapping("get/**/test")
    public String getPathVariableAll(){
        return "request get/**/test";
    }
}
