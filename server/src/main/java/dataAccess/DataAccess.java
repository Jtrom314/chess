package dataAccess;

import model.GameData;
import model.UserData;
import model.AuthData;
public interface DataAccess {
    // UserDAO
    // Create
    public void createUser(UserData user) throws DataAccessException;

    // Read
    public UserData getUser(String username) throws DataAccessException;

    // AuthDAO
    // Create
    public String createAuthentication(String username) throws DataAccessException;

    // Read
    public AuthData getAuthenticationByAuthToken(String authToken) throws  DataAccessException;
    public AuthData getAuthenticationByUsername(String username) throws DataAccessException;

    // GameDAO
    // Create
    public void createGame(GameData game) throws  DataAccessException;

    // Read
    public GameData getGameById(int id) throws DataAccessException;
    public GameData[] getGameList() throws DataAccessException;

    // Update
    public void updateGame(GameData game) throws DataAccessException;

    // All
    // Delete
    public void clear() throws DataAccessException;
}
