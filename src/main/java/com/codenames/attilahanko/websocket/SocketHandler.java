package com.codenames.attilahanko.websocket;

import com.codenames.attilahanko.websocket.socketsession.SetWebSocketSession;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.servlet.http.HttpServletRequest;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private SetWebSocketSession setWebSocketSession;

    public SocketHandler(SetWebSocketSession setWebSocketSession) {
        this.setWebSocketSession = setWebSocketSession;
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