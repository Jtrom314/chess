package server.websocket;

import dataAccess.DataAccess;
import model.AuthData;
import org.eclipse.jetty.websocket.api.Session;
import java.util.concurrent.ConcurrentHashMap;
public class ConnectionManager {
    public final ConcurrentHashMap<Integer, GameRoom> gameRooms = new ConcurrentHashMap<>();

    public DataAccess dataAccess;
    public ConnectionManager (DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Connection getConnection(String authToken, Session session) throws Exception{
        try {
            AuthData auth = dataAccess.getAuthenticationByAuthToken(authToken);
            if (auth == null) {
                throw new Exception();
            }
            return new Connection(authToken, session);
        } catch (NullPointerException exception) {
            throw new Exception();
        }
    }

    public void addRoom (Connection white, Connection black, Connection user, int gameID) {
        GameRoom game = new GameRoom(gameID);
        game.setWhitePlayer(white);
        game.setBlackPlayer(black);
        game.addToAllConnections(user);
        gameRooms.put(gameID, game);
    }
}
