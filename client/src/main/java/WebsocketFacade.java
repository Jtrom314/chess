import com.google.gson.Gson;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.UserGameCommand;

import javax.websocket.*;
import java.net.URI;

public class WebsocketFacade extends Endpoint {
    public Session session;
    public SharedData sharedData;
    public WebsocketFacade(SharedData sharedData) throws Exception {
        this.sharedData = sharedData;

        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);
        System.out.println(this.session);
        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                ServerMessage serverMessage = new Gson().fromJson(message, ServerMessage.class);
                switch (serverMessage.getServerMessageType()) {
                    case LOAD_GAME:
                        sharedData.setGame(serverMessage.getGame().game());
                        sharedData.redrawBoard();
                        return;
                    case NOTIFICATION:
                        System.out.println(serverMessage.getMessage());
                        return;
                    case ERROR:
                        System.out.println(serverMessage.getErrorMessage());

                }
            }
        });
    }

    public void send(UserGameCommand command) throws Exception {
        this.session.getBasicRemote().sendText(new Gson().toJson(command));
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {

    }
}
