package server.websocket;

import chess.ChessGame;
import com.google.gson.Gson;

import dataAccess.DataAccess;
import org.eclipse.jetty.websocket.api.Session;


import org.eclipse.jetty.websocket.api.annotations.*;
import server.websocket.methods.JoinManager;
import server.websocket.methods.MoveManager;
import server.websocket.methods.ObserveManager;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

@WebSocket
public class WebsocketHandler {
    DataAccess dataAccess;
    ConnectionManager connections;
    JoinManager joinManager;
    ObserveManager observeManager;
    MoveManager moveManager;
    public WebsocketHandler(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
        this.connections = new ConnectionManager(dataAccess);
        this.joinManager = new JoinManager(connections);
        this.observeManager = new ObserveManager(connections);
        this.moveManager = new MoveManager(connections);
    }

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        UserGameCommand command = new Gson().fromJson(message, UserGameCommand.class);
        try {
            Connection connection = connections.getConnection(command.getAuthString(), session);
            switch (command.getCommandType()) {
                case JOIN_PLAYER -> joinManager.join(connection, command);
                case JOIN_OBSERVER -> observeManager.observe(connection, command);
                case MAKE_MOVE -> moveManager.move(connection, command);
                case LEAVE -> leave(connection, command);
                case RESIGN -> resign(connection, command);
                default -> throw new Exception();
            }
        } catch (Exception exception) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage("ERROR: INVALID COMMAND");
            session.getRemote().sendString(new Gson().toJson(error));
        }
    }
    public void leave(Connection connection, UserGameCommand command) {}

    public void resign(Connection connection, UserGameCommand command) {}

}
