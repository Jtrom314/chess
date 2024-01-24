package server.websocket;

import org.eclipse.jetty.websocket.api.Session;
public class Connection {
    public String visitorName;
    public Session session;

    public Connection(String visitorName, Session session) {
        this.visitorName = visitorName;
        this.session = session;
    }
}
