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
import java.util.HashMap;

public class LeaveManager {
    ConnectionManager connectionManager;
    public LeaveManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void leave(Connection connection, UserGameCommand command) throws Exception {
        try {
            GameData game = connectionManager.dataAccess.getGameById(command.getGameID());
            GameRoom room = connectionManager.gameRooms.get(command.getGameID());

            if (room.getWhitePlayer() != null) {
                if (connection.getAuthToken().equals(room.getWhitePlayer().getAuthToken())) {
                    room.setWhitePlayer(null);
                    GameData updatedGameData = new GameData(game.gameID(), null, game.blackUsername(), game.gameName(), game.game());
                    connectionManager.dataAccess.updateGame(updatedGameData);
                }
            }

            if (room.getBlackPlayer() != null) {
                if (connection.getAuthToken().equals(room.getBlackPlayer().getAuthToken())) {
                    room.setBlackPlayer(null);
                    GameData updatedGameData = new GameData(game.gameID(), game.whiteUsername(), null, game.gameName(), game.game());
                    connectionManager.dataAccess.updateGame(updatedGameData);
                }

            }

            AuthData auth = connectionManager.dataAccess.getAuthenticationByAuthToken(command.getAuthString());
            ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            String username = auth.username();
            String message = username + " has left the game";
            notification.setMessage(message);

            Collection<Connection> allConnections = room.getAllConnections();
            for (Connection conn: allConnections) {
                conn.getSession().getRemote().sendString(new Gson().toJson(notification));
            }
            room.removeFromALlConnections(connection);

        } catch (Exception exception) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage("ERROR: GAME ID DOES NOT EXIST");
            connection.getSession().getRemote().sendString(new Gson().toJson(error));
        }
    }
}
