package dev.wjteo.test_web_service;

import dev.wjteo.test_web_service.socket.TestClientEndpoint;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@WebListener
public class StartupListener implements ServletContextListener {
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private static TestClientEndpoint endpoint = null;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        executor.submit(() -> {
            try {
                Thread.sleep(5000);
                endpoint = new TestClientEndpoint(new URI("ws://localhost:8080/test_web_service-1.0.0/test"));
                Thread.sleep(5000);
                endpoint.sendMessage("Test");
            } catch (URISyntaxException | InterruptedException e) {
                System.out.println("Endpoint URI syntax exception.");
            }
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        endpoint.shutdown();
        endpoint = null;
    }
}
