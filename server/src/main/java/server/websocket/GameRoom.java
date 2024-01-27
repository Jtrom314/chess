package server.websocket;

import webSocketMessages.serverMessages.ServerMessage;

import javax.websocket.Session;
import java.util.*;

public class GameRoom {
    int gameID;
    Collection<Connection> allConnections = new HashSet<>();
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
       for (Connection conn : allConnections) {
           if (compareConnections(conn, connection)) {
               allConnections.remove(conn);
           }
       }
    }

    private boolean compareConnections (Connection conn1, Connection conn2) {
        return conn1.getSession() == conn2.getSession() && conn1.getAuthToken().equals(conn2.getAuthToken());
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameRoom gameRoom = (GameRoom) o;
        return gameID == gameRoom.gameID && Objects.equals(allConnections, gameRoom.allConnections) && Objects.equals(whitePlayer, gameRoom.whitePlayer) && Objects.equals(blackPlayer, gameRoom.blackPlayer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameID, allConnections, whitePlayer, blackPlayer);
    }
}
