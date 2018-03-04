package com.codenames.attilahanko.websocket.socketsession;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class WebSocketSessionContainer {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    public List<WebSocketSession> getSessions() {
        return sessions;
    }

    public void addSession(WebSocketSession webSocketSession) {
        sessions.add(webSocketSession);
    }
}
