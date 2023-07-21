package org.hc.web.controller;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hc.web.util.EventSourceUtil;
import org.hc.web.util.ThreadPoolUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 服务器推送
 */
@Slf4j
@Controller
@RequestMapping("serverPush")
public class ServerPushController {

    @GetMapping("getServerTime")
    @ResponseBody
    public String getServerTime() {
        return DateUtil.now();
    }

    @GetMapping(value = "getServerTimeBySSE", produces = "text/event-stream;charset=UTF-8")
    @ResponseBody
    public void getServerTimeBySSE(HttpServletRequest request, HttpServletResponse response) {
        String remoteHost = request.getRemoteHost();
        String sessionId = request.getRequestedSessionId();
        response.setContentType("text/event-stream");
        response.setCharacterEncoding("utf-8");

        int id = 1;
        try {
            PrintWriter writer = response.getWriter();
            while (true) {
                if (writer.checkError()) {
                    log.info("{}[{}] disconnect", remoteHost, sessionId);
                    break;
                }
                String now = DateUtil.now();
                String message = EventSourceUtil.getEventSourceMessage(String.valueOf(id), "message", now, "");
                writer.write(message);
                writer.flush();
                Thread.sleep(1000);
                id++;
            }
        } catch (IOException|InterruptedException e) {
            e.printStackTrace();
        } finally {
            log.info("{}[{}] stop pushing", remoteHost, sessionId);
        }
    }

    /**
     * 异步返回响应
     *
     * 不建议使用这种方式:
     * 1. 依赖Servlet 3.0
     * 2. 内置Tomcat无法配置<asyncSupported>true</asyncSupported>, 等价于与Spring Boot不匹配
     *
     */
    /*@RequestMapping("getServerTimeByAsync")
    @ResponseBody
    public DeferredResult<String> getServerTimeByAsync() {
        DeferredResult<String> deferredResult = new DeferredResult<>();
        deferredResult.onTimeout(() -> {log.info("time out");});
        deferredResult.onCompletion(() -> {log.info("completion");});

        ThreadPoolUtil.executeBySingle(() -> {
            try {
                Thread.sleep(3000l);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            deferredResult.setResult(DateUtil.now());
            log.info("getServerTimeByAsync");
        });

        return deferredResult;
    }*/
}
