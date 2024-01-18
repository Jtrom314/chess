package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

public class SQLDataAccess implements DataAccess {
    public void createUser(UserData data) throws DataAccessException{

    }

    public UserData getUser(String username) throws DataAccessException {
    }

    public String createAuthentication(String username) throws DataAccessException {
    }

    public AuthData getAuthenticationByAuthToken(String authToken) throws DataAccessException {

    }

    public AuthData getAuthenticationByUsername(String username) throws DataAccessException {

    }

    public void removeAuthentication(AuthData auth) throws DataAccessException {

    }

    public void createGame(GameData game) throws DataAccessException {

    }

    public GameData getGameById(int id) throws DataAccessException {

    }

    public GameData[] getGameList() throws DataAccessException {

    }

    public void updateGame(GameData game) throws DataAccessException {

    }

    public void clear() throws DataAccessException {

    }

}
