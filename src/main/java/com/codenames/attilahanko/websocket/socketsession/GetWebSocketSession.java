package com.codenames.attilahanko.websocket.socketsession;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Component
public class GetWebSocketSession {

    private WebSocketSessionContainer webSocketSessionContainer;

    public GetWebSocketSession(WebSocketSessionContainer webSocketSessionContainer) {
        this.webSocketSessionContainer = webSocketSessionContainer;
    }

    public List<WebSocketSession> getSessions() {
        return webSocketSessionContainer.getSessions();
    }
}
