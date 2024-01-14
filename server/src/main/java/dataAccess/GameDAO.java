package dataAccess;

import model.GameData;

public class GameDAO {
    // CRUD operations:
    // Create
    public void createGame(GameData game) throws DataAccessException {

    }

    // Read
    public GameData getGameById(int id)  throws DataAccessException {
        return null;
    }
    public GameData[] getGameList() throws DataAccessException {
        return null;
    }

    // Update
    public void updateGame(String whiteUsername, String blackUsername, int id) throws DataAccessException {

    }

    // Delete
    public void clear() throws DataAccessException {

    }
}
