package server.websocket.methods;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import chess.InvalidMoveException;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import server.websocket.Connection;
import server.websocket.ConnectionManager;
import server.websocket.GameRoom;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import java.util.Collection;

public class MoveManager {
    ConnectionManager connectionManager;
    public MoveManager(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    public void move(Connection connection, UserGameCommand command) throws Exception {
        // Server verifies the validity of the move
        try {
            GameRoom room = connectionManager.gameRooms.get(command.getGameID());
            Boolean isWhite = (connection.getAuthToken().equals(room.getWhitePlayer().getAuthToken()) && connection.getSession() == room.getWhitePlayer().getSession());
            Boolean isBlack = (connection.getAuthToken().equals(room.getBlackPlayer().getAuthToken()) && connection.getSession() == room.getBlackPlayer().getSession());
            Boolean isObserver = (!isWhite && !isBlack);


            if (isObserver) {
                ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage("ERROR: OBSERVER CANNOT MAKE MOVES");
                connection.getSession().getRemote().sendString(new Gson().toJson(error));
                return;
            }

            ChessGame.TeamColor teamColor = isWhite ? ChessGame.TeamColor.WHITE : ChessGame.TeamColor.BLACK;
            GameData gameData = connectionManager.dataAccess.getGameById(command.getGameID());
            ChessGame game = gameData.game();

            if (game.getTeamTurn() != teamColor) {
                ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                error.setErrorMessage("ERROR: ILLEGAL TURN");
                connection.getSession().getRemote().sendString(new Gson().toJson(error));
                return;
            }

            // Game is updated to represent the move
            try {
                game.setIsInCheckmate(game.isInCheckmate(teamColor));
                game.setIsInStalemate(game.isInStalemate(teamColor));
                game.makeMove(command.getMove());
            } catch (InvalidMoveException exception) {
                ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
                String errorMessage = "ERROR: " + exception.getMessage();
                error.setErrorMessage(errorMessage);
                connection.getSession().getRemote().sendString(new Gson().toJson(error));
                return;
            }


            // Game is updated in the database
            connectionManager.dataAccess.updateGame(gameData);

            // Server sends a LOAD_GAME message to all clients in the game including root client with updated game
            Collection<Connection> allConnections = room.getAllConnections();
            ServerMessage loadGame = new ServerMessage(ServerMessage.ServerMessageType.LOAD_GAME);
            loadGame.setGame(gameData);

            for (Connection conn: allConnections) {
                conn.getSession().getRemote().sendString(new Gson().toJson(loadGame));
            }

            // Server sends a Notification message to all other clients in that game informing them what move was made
            AuthData auth = connectionManager.dataAccess.getAuthenticationByAuthToken(command.getAuthString());
            ServerMessage notification = new ServerMessage(ServerMessage.ServerMessageType.NOTIFICATION);
            String move = convertMoveToString(command.getMove());
            String message = auth.username() + " made the move: " + move;
            notification.setMessage(message);
            for (Connection conn: allConnections) {
                if (conn.getSession() != connection.getSession()) {
                    conn.getSession().getRemote().sendString(new Gson().toJson(notification));
                }
            }


        } catch (Exception e) {
            ServerMessage error = new ServerMessage(ServerMessage.ServerMessageType.ERROR);
            error.setErrorMessage("ERROR: INVALID GAME ID");
            connection.getSession().getRemote().sendString(new Gson().toJson(error));
        }
    }

    public String convertMoveToString(ChessMove move) {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        return columnConverter(startPosition.getColumn()) + startPosition.getRow() + columnConverter(endPosition.getColumn()) + endPosition.getRow();
    }

    public String columnConverter(int col) {
        String[] colNames = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return colNames[col - 1];
    }
}
