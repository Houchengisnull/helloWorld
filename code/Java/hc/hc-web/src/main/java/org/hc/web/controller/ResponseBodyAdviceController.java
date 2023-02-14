package hc.web.controller;

import hc.web.advice.annotation.UpdateResponseBodyAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequestMapping("/responseBodyAdvice")
@RestController
public class ResponseBodyAdviceController {

    @UpdateResponseBodyAdvice
    @GetMapping("/get")
    public String responseBodyAdvice(String message) {
        return message;
    }

}
