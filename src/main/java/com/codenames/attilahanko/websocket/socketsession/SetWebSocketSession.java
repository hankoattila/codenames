package com.codenames.attilahanko.websocket.socketsession;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class SetWebSocketSession {

    private WebSocketSessionContainer webSocketSessionContainer;

    public SetWebSocketSession(WebSocketSessionContainer webSocketSessionContainer) {
        this.webSocketSessionContainer = webSocketSessionContainer;
    }

    public void addSession(WebSocketSession webSocketSession) {
        webSocketSessionContainer.addSession(webSocketSession);
    }

    public void removeSession(WebSocketSession session) {
        webSocketSessionContainer.getSessions().remove(session);
    }
}
