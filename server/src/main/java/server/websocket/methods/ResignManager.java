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

public class ResignManager {
    ConnectionManager connectionManager;
    public ResignManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void resign(Connection connection, UserGameCommand command) throws Exception {
        try {
            GameRoom room = connectionManager.gameRooms.get(command.getGameID());
            if (!connection.getAuthToken().equals(room.getWhitePlayer().getAuthToken()) && !connection.getAuthToken().equals(room.getBlackPlayer().getAuthToken())) {
                ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage("ERROR: OBSERVER CANNOT RESIGN");
                connection.getSession().getRemote().sendString(new Gson().toJson(error));
                return;
            }

            GameData gameData = connectionManager.dataAccess.getGameById(command.getGameID());
            ChessGame game = gameData.game();

            if (game.getPlayerResigned()) {
                ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage("ERROR: A PLAYER HAS ALREADY RESIGNED");
                connection.getSession().getRemote().sendString(new Gson().toJson(error));
                return;
            }


            game.setPlayerResigned(true);
            connectionManager.dataAccess.updateGame(gameData);

            AuthData auth = connectionManager.dataAccess.getAuthenticationByAuthToken(command.getAuthString());

            ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            String message = auth.username() + " has resigned";
            notification.setMessage(message);

            Collection<Connection> allConnections = room.getAllConnections();
            for (Connection conn: allConnections) {
                conn.getSession().getRemote().sendString(new Gson().toJson(notification));
            }

        } catch (Exception exception) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage("ERROR: INVALID GAME ID");
            connection.getSession().getRemote().sendString(new Gson().toJson(error));
        }
    }
}
