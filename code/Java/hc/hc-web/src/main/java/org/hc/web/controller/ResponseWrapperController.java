package org.hc.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/responseWrapper")
public class ResponseWrapperController {

    /**
     * @Return 返回小于8kb的数据
     */
    @PostMapping("/hello/chinese")
    public String helloInChinese(@RequestBody String content){
        return content;
    }

    /**
     * @Return 返回小于8kb的数据
     */
    @GetMapping("/hello/english")
    public String hello(){
        return "hello world";
    }

}
