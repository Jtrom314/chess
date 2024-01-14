package dataAccess;

import model.GameData;
import model.UserData;
import model.AuthData;
public interface DataAccess {
    // UserDAO
    // Create
    void createUser(UserData user) throws DataAccessException;

    // Read
    UserData getUser(String username) throws DataAccessException;

    // AuthDAO
    // Create
    String createAuthentication(String username) throws DataAccessException;

    // Read
    AuthData getAuthenticationByAuthToken(String authToken) throws  DataAccessException;
    AuthData getAuthenticationByUsername(String username) throws DataAccessException;

    // GameDAO
    // Create
    void createGame(GameData game) throws  DataAccessException;

    // Read
    GameData getGameById(int id) throws DataAccessException;
    GameData[] getGameList() throws DataAccessException;

    // Update
    void updateGame(GameData game) throws DataAccessException;

    // All
    // Delete
    void clear() throws DataAccessException;
}
