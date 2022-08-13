package hc.web.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/webSocket")
public class WebSocketController {

    @Autowired
    private WebSocketEndpoint endpoint;

    @GetMapping("/hello")
    public void hello(String message) throws IOException {
        WebSocketEndpoint.sendMessage(message);
    }

}
