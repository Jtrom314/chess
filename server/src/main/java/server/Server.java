package server;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import dataAccess.SQLDataAccess;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import server.handlers.ExceptionalHandlers;
import server.handlers.GameHandlers;
import server.handlers.UserHandlers;
import server.websocket.WebsocketHandler;
import spark.*;

import java.nio.file.Paths;

public class Server {

    private final UserHandlers userHandlers;
    private final GameHandlers gameHandlers;
    private final ExceptionalHandlers exceptionalHandlers;
    private final WebsocketHandler websocketHandler;


    public Server() {
        DataAccess dataAccess = getDataAccess();
        this.userHandlers = new UserHandlers(dataAccess);
        this.gameHandlers = new GameHandlers(dataAccess);
        this.exceptionalHandlers = new ExceptionalHandlers(dataAccess);
        this.websocketHandler = new WebsocketHandler();
    }

    public static void main(String[] args) {
        new Server().run(8080);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.webSocket("/connect", websocketHandler);

        Spark.post("/user", userHandlers::register);
        Spark.post("/session", userHandlers::login);
        Spark.delete("/session", userHandlers::logout);

        Spark.put("/game", gameHandlers::joinGame);
        Spark.post("/game", gameHandlers::createGame);
        Spark.get("/game", gameHandlers::listGames);

        Spark.delete("/db", exceptionalHandlers::clear);
        Spark.exception(ResponseException.class, exceptionalHandlers::handleException);

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    private static DataAccess getDataAccess () {
        try {
            return new SQLDataAccess();
        } catch (Exception exception) {
            return null;
        }
    }
}
