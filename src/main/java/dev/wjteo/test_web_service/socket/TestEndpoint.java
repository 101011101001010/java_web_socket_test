package dev.wjteo.test_web_service.socket;

import jakarta.websocket.CloseReason;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;

@ServerEndpoint("/test")
public class TestEndpoint {
    @OnOpen
    public void open(Session session, EndpointConfig conf) {
        if (session.getUserPrincipal() == null) {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        System.out.println("Session: " + session.getId());
        System.out.println("Session: " + session.getUserPrincipal());
    }

    @OnMessage
    public void message(Session session, String msg) {
        try {
            session.getBasicRemote().sendText(msg);
            System.out.println(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnError
    public void error(Session session, Throwable error) {

    }

    @OnClose
    public void close(Session session, CloseReason reason) {

    }
}
