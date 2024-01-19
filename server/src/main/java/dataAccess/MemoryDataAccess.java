package dataAccess;

import model.UserData;
import model.AuthData;
import model.GameData;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashMap;
import java.util.UUID;

public class MemoryDataAccess implements DataAccess {
    public HashMap<String, UserData> users = new HashMap<>();
    public HashMap<String, AuthData> authByToken = new HashMap<>();
    public HashMap<String, AuthData> authByUsername = new HashMap<>();
    public HashMap<Integer, GameData> games = new HashMap<>();

    private int gameID = 0;
    // UserDAO
    // Create
    public void createUser(UserData user) throws DataAccessException {
        String username = user.username();
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        UserData updatedUser = new UserData(user.username(), encoder.encode(user.password()), user.email());
        users.put(username, updatedUser);
    }

    // Read
    public UserData getUser(String username) throws DataAccessException {
        return users.get(username);
    }

    // AuthDAO
    // Create
    public String createAuthentication(String username) throws DataAccessException {
        String authToken = UUID.randomUUID().toString();
        AuthData auth = new AuthData(authToken, username);
        authByToken.put(authToken, auth);
        authByUsername.put(username, auth);
        return authToken;
    }

    // Read
    public AuthData getAuthenticationByAuthToken(String authToken) throws DataAccessException {
        return authByToken.get(authToken);
    }


    // Delete


    public void removeAuthentication(AuthData auth) throws DataAccessException {
        String authToken = auth.authToken();
        String username = auth.username();
        authByToken.remove(authToken);
        authByUsername.remove(username);
    }

    // GameDAO
    // Create
    public int createGame(GameData game) throws DataAccessException {
        gameID++;
        GameData updatedGame = new GameData(gameID, game.whiteUsername(), game.blackUsername(), game.gameName(), game.game());
        games.put(gameID, updatedGame);
        return gameID;
    }

    // Read
    public GameData getGameById(int id) throws DataAccessException {
        return games.get(id);
    }

    public GameData[] getGameList() throws DataAccessException {
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
    public void updateGame(GameData game) throws DataAccessException {
        int id = game.gameID();
        games.put(id, game);
    }

    // All
    // Delete
    public void clear() throws DataAccessException {
        users.clear();
        authByUsername.clear();
        authByToken.clear();
        games.clear();
    }
}
