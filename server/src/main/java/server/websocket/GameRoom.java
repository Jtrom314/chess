package server.websocket;

import webSocketMessages.serverMessages.ServerMessage;

import java.util.ArrayList;
import java.util.Collection;

public class GameRoom {
    int gameID;
    Collection<Connection> allConnections = new ArrayList<>();
    Connection whitePlayer;
    Connection blackPlayer;

    public GameRoom (int gameID) {
        this.gameID = gameID;
        this.whitePlayer = null;
        this.blackPlayer = null;
    }

    public void addToAllConnections(Connection connection) {
        allConnections.add(connection);
    }

    public void removeFromALlConnections(Connection connection) {
        allConnections.remove(connection);
    }

    public void setWhitePlayer(Connection connection) {
        this.whitePlayer = connection;
    }

    public void setBlackPlayer(Connection connection) {
        this.blackPlayer = connection;
    }

    public Collection<Connection> getAllConnections() {
        return this.allConnections;
    }

    public Connection getWhitePlayer() {
        return this.whitePlayer;
    }

    public Connection getBlackPlayer() {
        return this.blackPlayer;
    }

}
