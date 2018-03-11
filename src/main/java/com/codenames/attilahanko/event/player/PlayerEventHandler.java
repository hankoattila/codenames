package com.codenames.attilahanko.event.player;

import com.codenames.attilahanko.websocket.socketsession.GetWebSocketSession;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;

@Component
public class PlayerEventHandler {

    private List<WebSocketSession> sessions;

    public PlayerEventHandler(GetWebSocketSession getWebSocketSession) {
        this.sessions = getWebSocketSession.getSessions();
    }

    @EventListener
    public void editBoard(CardSelected CardSelected) throws IOException {
        // TODO: 2018.03.09. Should change, and handle TEXT_PARTIAL_WRITING exception
        for (WebSocketSession session : sessions) {
            String yourTeam = (String) session.getAttributes().get("team");
        }
    }
}
