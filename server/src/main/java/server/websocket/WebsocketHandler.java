package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;

import dataAccess.DataAccess;
import model.AuthData;
import model.GameData;
import org.eclipse.jetty.websocket.api.Session;


import org.eclipse.jetty.websocket.api.annotations.*;
import server.websocket.methods.JoinManager;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.util.ArrayList;
import java.util.Collection;

@WebSocket
public class WebsocketHandler {
    DataAccess dataAccess;
    ConnectionManager connections;
    JoinManager joinManager;
    public WebsocketHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
        this.connections = new ConnectionManager(dataAccess);
        this.joinManager = new JoinManager(connections);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        Connection connection = connections.getConnection(command.getAuthString(), session);
        if (connection != null) {
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> joinManager.join(connection, command);
                case JOIN_OBSERVER -> observe(connection, message);
                case MAKE_MOVE -> move(connection, message);
                case LEAVE -> leave(connection, message);
                case RESIGN -> resign(connection, message);
            }
        } else {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage("ERROR: INVALID COMMAND");
            session.getRemote().sendString(new Gson().toJson(error));
        }
    }
    public void observe(Connection connection, String msg) {}

    public void move(Connection connection, String msg) {}
    public void leave(Connection connection, String msg) {}

    public void resign(Connection connection, String msg) {}

}
