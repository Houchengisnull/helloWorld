package hc.web.controller;

import hc.web.bean.ValidateUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RequestMapping("/validate")
@RestController
public class ValidateController {

    @GetMapping("/not_null")
    public String validateNotNull(@Valid ValidateUser u) {
        return "not_null validate is pass";
    }
}
