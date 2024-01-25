package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
public class Connection {
    public String authToken;
    public Session session;

    public Connection(String authToken, Session session) {
        this.authToken = authToken;
        this.session = session;
    }

    public String getAuthToken() {
        return authToken;
    }

    public Session getSession() {
        return session;
    }
}
