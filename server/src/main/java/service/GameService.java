package service;

import dataAccess.DataAccess;

public class GameService extends SharedService{
    public GameService (DataAccess dataAccess, AuthenticationService authService) {
        super(dataAccess, authService);
    }
}
