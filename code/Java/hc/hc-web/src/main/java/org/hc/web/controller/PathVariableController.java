package org.hc.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/pathVariable")
public class PathVariableController {

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
        ResponseEntity<String> res = restTemplate.getForEntity("http://127.0.0.1:8090/hc-web/pathVariable/", String.class);
        return res.getBody();
    }

    /**
     * {*spring}
     * 注意事项:
     * - Spring Boot 2.6.0以后才支持该格式(https://www.zhangbj.com/p/1261.html)
     */
    @GetMapping("/get/{*path}")
    public String getPathVariable(@PathVariable("path") String path) {
        log.info(path);
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
     * /{method}/{*path} 若使用{*path},则仅支持一个@PathVariables
     * @param uri
     * @param path
     * @return
     */
    /*@GetMapping("/{method}/{*path}")
    public String pathTest(@PathVariable("method")String uri, @PathVariable("path") String path) {
        return path;
    }*/

    /*@GetMapping("get/**")
    public String getPathVariableAll(){
        return "request **";
    }*/

    /**
     * Not supported by "spring.mvc.pathmatch.matching-strategy=ant-path-matche"
     *
     * 该pattern不允许与 {*spring}或者/** 共同存在
     */
//    @GetMapping("get/**/test")
//    public String getPathVariableAll(){
//        return "request get/**/test";
//    }
}
