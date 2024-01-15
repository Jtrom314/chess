package server;

import dataAccess.DataAccess;
import service.AuthenticationService;
import service.GameService;

public class GameHandlers {
    private DataAccess dataAccess;
    private GameService gameService;
    public GameHandlers(DataAccess dataAccess, AuthenticationService authService) {
        this.dataAccess = dataAccess;
        this.gameService = new GameService(dataAccess, authService);
    }
}
