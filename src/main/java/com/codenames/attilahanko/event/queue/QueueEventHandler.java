package com.codenames.attilahanko.event.queue;

import com.codenames.attilahanko.websocket.socketsession.GetWebSocketSession;
import com.google.gson.Gson;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Component
public class QueueEventHandler {

    private List<WebSocketSession> sessions;

    public QueueEventHandler(GetWebSocketSession getWebSocketSession) {
        this.sessions = getWebSocketSession.getSessions();
    }

    @EventListener
    public void addNewPlayer(newUserJoined newUserJoined) throws IOException {
        for (WebSocketSession session : sessions) {
            session.sendMessage(new TextMessage(new Gson().toJson(newUserJoined)));
        }
    }
}
