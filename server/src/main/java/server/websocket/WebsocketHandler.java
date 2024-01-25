package server.websocket;

import com.google.gson.Gson;

import org.eclipse.jetty.websocket.api.Session;


import org.eclipse.jetty.websocket.api.annotations.*;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;
@WebSocket
public class WebsocketHandler {
    private final ConnectionManager connections = new ConnectionManager();

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        Connection connection = connections.getConnection(command.getAuthString(), session);
        if (connection != null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER:
                    return;
                case JOIN_OBSERVER:
                    return;
                case MAKE_MOVE:
                    return;
                case LEAVE:
                    return;
                case RESIGN:
                    return;
            }
        } else {
            // Die
        }
    }

    public void send (Session session) throws Exception {
        session.getRemote().sendString("Hello");
    }

}
