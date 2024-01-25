package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
import java.util.concurrent.ConcurrentHashMap;
public class ConnectionManager {
    public final ConcurrentHashMap<String, Connection> connections = new ConcurrentHashMap<>();

    public void add(String authToken, Session session) {
        Connection connection = new Connection(authToken, session);
        connections.put(authToken, connection);
    }

    public void remove(String authToken) {
        connections.remove(authToken);
    }

    public Connection getConnection(String authToken, Session session) {
        Connection connection =  connections.get(authToken);
        if (connection.getAuthToken().equals(authToken) && connection.getSession() == session) {
            return connection;
        }
        return null;
    }
}
