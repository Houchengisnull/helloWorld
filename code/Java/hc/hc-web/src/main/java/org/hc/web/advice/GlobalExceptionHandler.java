package org.hc.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public String handle(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = e.getMessage();
        String localizedMessage = e.getLocalizedMessage();
        return e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
    }

}
