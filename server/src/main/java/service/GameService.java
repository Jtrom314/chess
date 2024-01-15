package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.GameData;

public class GameService extends SharedService{
    public GameService (DataAccess dataAccess, AuthenticationService authService) {
        super(dataAccess, authService);
    }

    public GameData[] getGames () throws DataAccessException {
        try {
            return dataAccess.getGameList();
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public GameData getGame(int gameID) throws DataAccessException {
        try {
            return dataAccess.getGameById(gameID);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public void createGame(GameData game) throws DataAccessException {
        try {
            dataAccess.createGame(game);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public void updateGame(GameData game) throws DataAccessException {
        try {
            dataAccess.updateGame(game);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }
}
