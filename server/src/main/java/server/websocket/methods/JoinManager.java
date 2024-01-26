package server.websocket.methods;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import server.websocket.Connection;
import server.websocket.ConnectionManager;
import server.websocket.GameRoom;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.util.Collection;

public class JoinManager {
    ConnectionManager connectionManager;
    public JoinManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void join (Connection connection, UserGameCommand command) throws Exception {
        try {
            GameData game = connectionManager.dataAccess.getGameById(command.getGameID());
            AuthData auth = connectionManager.dataAccess.getAuthenticationByAuthToken(command.getAuthString());
            String username = auth.username();
            if (command.getPlayerColor() == ChessGame.TeamColor.WHITE) {
                if (game.whiteUsername().equals(username)) {
                    GameRoom room = connectionManager.gameRooms.get(command.getGameID());
                    if (room == null) {
                        connectionManager.addRoom(connection, null, connection, command.getGameID());
                    } else {
                        room.setWhitePlayer(connection);
                        room.addToAllConnections(connection);
                    }

                } else {
                    ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                    error.setErrorMessage("ERROR: PLAYER COLOR ALREADY TAKEN");
                    connection.getSession().getRemote().sendString(new Gson().toJson(error));
                }
            } else {
                if (game.blackUsername().equals(username)) {

                    GameRoom room = connectionManager.gameRooms.get(command.getGameID());
                    if (room == null) {
                        connectionManager.addRoom(null, connection, connection, command.getGameID());
                    } else {
                        room.setBlackPlayer(connection);
                        room.addToAllConnections(connection);
                    }
                } else {
                    ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                    error.setErrorMessage("ERROR: PLAYER COLOR ALREADY TAKEN");
                    connection.getSession().getRemote().sendString(new Gson().toJson(error));
                    return;
                }
            }

            // Server sends a LOAD_GAME message back to the root client
            ServerMessage loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            loadGame.setGame(game);
            connection.getSession().getRemote().sendString(new Gson().toJson(loadGame));

            // Server sends a Notification message back to all other clients in that game informing them what color the root client is joining as
            GameRoom room = connectionManager.gameRooms.get(command.getGameID());
            ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            String color = command.getPlayerColor() == ChessGame.TeamColor.WHITE ? "white" : "black";
            String message = username + " joined as " + color;

            notification.setMessage(message);
            Collection<Connection> allConnections = room.getAllConnections();
            if (allConnections != null) {
                for (Connection conn : allConnections) {
                    if (conn.getSession() != connection.getSession()) {
                        conn.getSession().getRemote().sendString(new Gson().toJson(notification));
                    }
                }
            }

        } catch (Exception e) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage("ERROR: GAME ID DOES NOT EXIST");
            connection.getSession().getRemote().sendString(new Gson().toJson(error));
        }
    }
}
