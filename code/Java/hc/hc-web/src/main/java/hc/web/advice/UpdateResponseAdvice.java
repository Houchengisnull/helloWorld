package hc.web.advice;

import cn.hutool.core.util.StrUtil;
import hc.web.advice.annotation.UpdateResponseBodyAdvice;
import hc.web.controller.ResponseBodyAdviceController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import java.lang.reflect.Method;

@Slf4j
@ControllerAdvice(assignableTypes = ResponseBodyAdviceController.class)
public class UpdateResponseAdvice implements ResponseBodyAdvice<String> {

    @Override
    public boolean supports(MethodParameter returnType, Class converterType) {
        Method method = returnType.getMethod();
        log.debug("method name:{}", method);
        UpdateResponseBodyAdvice annotation = method.getAnnotation(UpdateResponseBodyAdvice.class);
        return annotation != null;
    }

    @Override
    public String beforeBodyWrite(String body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        log.debug("response body advice handle message:{}", body);
        return StrUtil.format("Hello {}", body);
    }
}
