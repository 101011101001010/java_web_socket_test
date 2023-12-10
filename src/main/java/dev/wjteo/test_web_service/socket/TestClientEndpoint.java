package dev.wjteo.test_web_service.socket;

import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.CloseReason;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.WebSocketContainer;

import java.io.IOException;
import java.net.URI;

@ClientEndpoint
public class TestClientEndpoint {
    private Session session = null;

    public TestClientEndpoint(URI endpoint) {
        System.out.println("Connect to server???");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            container.connectToServer(this, endpoint);
        } catch (Exception e) {
            System.out.println("Failed connect to server???");
            e.printStackTrace();
        }
    }

    public void shutdown() {
        try {
            session.close();
        } catch (IOException e) {
            System.out.println("Failed to close session.");
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Session opened.");
        System.out.println(session.getId());
        this.session = session;
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        System.out.println("Session closed.");
        this.session = null;
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println(message);
    }

    public void sendMessage(String message) {
        if (session == null) return;
        this.session.getAsyncRemote().sendText(message);
    }
}
