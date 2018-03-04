package com.codenames.attilahanko.websocket;

import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.websocket.socketsession.SetWebSocketSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.HashMap;
import java.util.Map;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private SetWebSocketSession setWebSocketSession;
    private GameService gameService;

    public SocketHandler(SetWebSocketSession setWebSocketSession, GameService gameService) {
        this.setWebSocketSession = setWebSocketSession;
        this.gameService = gameService;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        setWebSocketSession.addSession(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        setWebSocketSession.removeSession(session);
    }
}