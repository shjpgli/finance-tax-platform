package com.abc12366.message.web;

import com.abc12366.message.Application;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author lijun <ljun51@outlook.com>
 * @create 2017-03-23 4:01 PM
 * @since 1.0.0
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApplicationTests {
    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Value("${local.server.port}")
    String port;

    @Autowired
    WebSocketHandler webSocketHandler;

    @Test
    public void run() throws Exception {
        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockJsClient = new SockJsClient(transports);
        ListenableFuture<WebSocketSession> wsSession = sockJsClient.doHandshake(
                this.webSocketHandler, "ws://localhost:" + this.port + "/sockjs");

        this.thrown.expect(ExecutionException.class);
        wsSession.get().sendMessage(new TextMessage("a"));
    }

}
