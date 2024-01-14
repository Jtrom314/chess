package dataAccess;

import model.UserData;
import model.AuthData;
import model.GameData;

import java.util.HashMap;
import java.util.UUID;

public class MemoryDataAccess implements DataAccess {
    final private HashMap<String, UserData> users = new HashMap<>();
    final private HashMap<String, AuthData> authByToken = new HashMap<>();
    final private HashMap<String, AuthData> authByUsername = new HashMap<>();
    final private HashMap<Integer, GameData> games = new HashMap<>();

    // UserDAO
    // Create
    void createUser(UserData user) throws DataAccessException {
        String username = user.username();
        users.put(username, user);
    }

    // Read
    UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    // AuthDAO
    // Create
    String createAuthentication(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        authByToken.put(authToken, auth);
        authByUsername.put(username, auth);
        return authToken;
    }

    // Read
    AuthData getAuthenticationByAuthToken(String authToken) throws DataAccessException {
        return authByToken.get(authToken);
    }

    AuthData getAuthenticationByUsername(String username) throws DataAccessException {
        return authByUsername.get(username);
    }

    // GameDAO
    // Create
    void createGame(GameData game) throws DataAccessException {
        int id = game.gameID();
        games.put(id, game);
    }

    // Read
    GameData getGameById(int id) throws DataAccessException {
        return games.get(id);
    }

    GameData[] getGameList() throws DataAccessException {
        GameData[] listOfGames = new GameData[games.size()];
        int i = 0;
        for (int key : games.keySet()) {
            GameData currentGame = games.get(key);
            listOfGames[i] = currentGame;
            i++;
        }
        return listOfGames;
    }

    // Update
    void updateGame(GameData game) throws DataAccessException {
        int id = game.gameID();
        games.put(id, game);
    }

    // All
    // Delete
    void clear() throws DataAccessException {
        users.clear();
        authByUsername.clear();
        authByToken.clear();
        games.clear();
    }
}
