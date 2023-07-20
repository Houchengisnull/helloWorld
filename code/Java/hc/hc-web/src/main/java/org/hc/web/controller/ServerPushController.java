package org.hc.web.controller;

import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hc.web.util.EventSourceUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ThreadFactory;

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
}
