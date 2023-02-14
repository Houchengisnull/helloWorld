package org.hc.web.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * /hc-web/ws
 */
@Slf4j
@ServerEndpoint("/ws")
@Component
public class WebSocketEndpoint {

    private Session session;
    private static CopyOnWriteArraySet<WebSocketEndpoint> endpoints = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        endpoints.add(this);
        log.info("有新的连接, 总数:{}", endpoints.size());
    }

    @OnClose
    public void onClose() {
        endpoints.remove(this);
        log.info("连接断开, 总数:{}", endpoints.size());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("收到消息:{}", message);
    }

    public static void sendMessage(String message) throws IOException {
        log.info("广播总数:{}", endpoints.size());
        for (WebSocketEndpoint endpoint : endpoints) {
            log.info("广播消息：{}", message);
            endpoint.session.getBasicRemote().sendText(message);
        }
    }
}
