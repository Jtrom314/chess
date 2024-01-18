package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import server.ResponseException;
import server.result.ListGamesResult;

public class ListGamesService extends SharedService {
    public ListGamesService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public ListGamesResult listGames(String authToken) throws ResponseException {
        try {
            AuthData auth = dataAccess.getAuthenticationByAuthToken(authToken);
            if (auth == null) {
                throw new ResponseException(401, "unauthorized");
            }
            return new ListGamesResult(dataAccess.getGameList());
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
