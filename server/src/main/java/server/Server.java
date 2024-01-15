package server;

import dataAccess.DataAccess;
import dataAccess.MemoryDataAccess;
import service.AuthenticationService;
import spark.*;

import java.nio.file.Paths;

public class Server {

    private UserHandlers userHandlers;
    private GameHandlers gameHandlers;
    private ExceptionalHandlers exceptionalHandlers;
    private AuthenticationService authService;
    private DataAccess dataAccess;
    public Server() {
        this.dataAccess = new MemoryDataAccess();
        this.authService = new AuthenticationService(dataAccess);
        this.userHandlers = new UserHandlers(dataAccess, authService);
        this.gameHandlers = new GameHandlers(dataAccess, authService);
        this.exceptionalHandlers = new ExceptionalHandlers(dataAccess);
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);

        var webDir = Paths.get(Server.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "web");
        Spark.externalStaticFileLocation(webDir.toString());

        // Register your endpoints and handle exceptions here.
        Spark.post("/user", (req, res) -> {
             return userHandlers.register(req, res);
        });
        // Spark.exception();

        Spark.awaitInitialization();
        return Spark.port();
    }

    public void stop() {
        Spark.stop();
    }
}
