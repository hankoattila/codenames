package com.codenames.attilahanko.event.player;

import com.codenames.attilahanko.websocket.socketsession.GetWebSocketSession;
import com.google.gson.Gson;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
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
        for (WebSocketSession session : sessions) {
            String yourTeam = (String) session.getAttributes().get("team");
            if (yourTeam.equals(CardSelected.getCurrentTeam())) {
                CardSelected.setYourTurn(true);
                session.sendMessage(new TextMessage(new Gson().toJson(CardSelected)));
            } else {
                CardSelected.setYourTurn(false);
                session.sendMessage(new TextMessage(new Gson().toJson(CardSelected)));
            }
        }
    }
}
