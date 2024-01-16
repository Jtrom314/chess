package server;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import server.handlers.ExceptionalHandlers;
import server.handlers.GameHandlers;
import server.handlers.UserHandlers;
import service.AuthenticationService;
import spark.*;

import java.nio.file.Paths;

public class Server {

    private final UserHandlers userHandlers;
    private final GameHandlers gameHandlers;
    private final ExceptionalHandlers exceptionalHandlers;

    public Server() {
        DataAccess dataAccess = new MemoryDataAccess();
        this.userHandlers = new UserHandlers(dataAccess);
        this.gameHandlers = new GameHandlers(dataAccess);
        this.exceptionalHandlers = new ExceptionalHandlers(dataAccess);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        var webDir = Paths.get(Server.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "web");
        Spark.externalStaticFileLocation(webDir.toString());

        // Register your endpoints and handle exceptions here.
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
    }
}
