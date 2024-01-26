package server.websocket.methods;

import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import server.websocket.Connection;
import server.websocket.ConnectionManager;
import server.websocket.GameRoom;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.util.Collection;

public class ObserveManager {
    ConnectionManager connectionManager;
    public ObserveManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void observe (Connection connection, UserGameCommand command) throws Exception {
        try {
            GameData game = connectionManager.dataAccess.getGameById(command.getGameID());
            if (game == null) {
                throw new Exception();
            }
            AuthData auth = connectionManager.dataAccess.getAuthenticationByAuthToken(command.getAuthString());

            GameRoom room = connectionManager.gameRooms.get(command.getGameID());
            if (room == null) {
                connectionManager.addRoom(null, null, connection, command.getGameID());
            } else {
                room.addToAllConnections(connection);
            }

            ServerMessage loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            loadGame.setGame(game);
            connection.getSession().getRemote().sendString(new Gson().toJson(loadGame));

            ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            String message = auth.username() + " joined as an observer";
            notification.setMessage(message);

            try {
                Collection<Connection> allConnections = room.getAllConnections();
                for (Connection conn : allConnections) {
                    if (conn.getSession() != connection.getSession()) {
                        conn.getSession().getRemote().sendString(new Gson().toJson(notification));
                    }
                }
            } catch (NullPointerException exception) {
                return;
            }
        } catch (Exception e) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage("ERROR: GAME ID DOES NOT EXIST");
            connection.getSession().getRemote().sendString(new Gson().toJson(error));
        }
    }
}
