package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class SQLDataAccess implements DataAccess {

    public SQLDataAccess() throws DataAccessException {
        configureDB();
    }
    public void createUser(UserData data) throws DataAccessException{
        try (var connection = DatabaseManager.getConnection()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            String hashedPassword = encoder.encode(data.password());
            var statement = "INSERT INTO UserData (username, password, email) VALUES (?, ?, ?)";
            try (var preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, data.username());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, data.email());

                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public UserData getUser(String username) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("SELECT username, password, email FROM UserData WHERE username = ?")) {
                preparedStatement.setString(1, username);
                try (var response = preparedStatement.executeQuery()) {
                    if (response.next()) {
                        return new UserData(response.getString("username"), response.getString("password"), response.getString("email"));
                    }
                    return null;
                }
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public String createAuthentication(String username) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("INSERT INTO AuthData (authToken, username) VALUES (?, ?)")) {
                String authToken = UUID.randomUUID().toString();
                preparedStatement.setString(1, authToken);
                preparedStatement.setString(2, username);

                preparedStatement.executeUpdate();
                return authToken;
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public AuthData getAuthenticationByAuthToken(String authToken) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("SELECT username FROM AuthData WHERE authToken = ?")) {
                preparedStatement.setString(1, authToken);
                try (var response = preparedStatement.executeQuery()) {
                    if (response.next()) {
                        return new AuthData(authToken, response.getString("username"));
                    }
                    return null;
                }
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public void removeAuthentication(AuthData auth) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("DELETE FROM AuthData WHERE authToken = ?")) {
                preparedStatement.setString(1, auth.authToken());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public int createGame(GameData game) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("INSERT INTO GameData (whiteUsername, blackUsername, gameName, game) VALUES (?,?,?,?)", RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, game.whiteUsername());
                preparedStatement.setString(2, game.blackUsername());
                preparedStatement.setString(3, game.gameName());

                Gson gson = new Gson();
                preparedStatement.setString(4, gson.toJson(game.game()));

                preparedStatement.executeUpdate();

                var result = preparedStatement.getGeneratedKeys();
                int id = 0;
                if (result.next()) {
                    id = result.getInt(1);
                }
                return id;
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public GameData getGameById(int id) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("SELECT * FROM GameData WHERE id = ?")) {
                preparedStatement.setInt(1, id);
                try (var response = preparedStatement.executeQuery()) {
                    if (response.next()) {
                        Gson gson = new Gson();
                        ChessGame game = gson.fromJson(response.getString("game"), ChessGame.class);
                        return new GameData(response.getInt("id"), response.getString("whiteUsername"), response.getString("blackUsername"), response.getString("gameName"), game);
                    }
                    return null;
                }
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public GameData[] getGameList() throws DataAccessException {
        Collection<GameData> gameList = new ArrayList<>();
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("SELECT * FROM GameData")) {
                try (var response = preparedStatement.executeQuery()) {
                    Gson gson = new Gson();
                    while (response.next()) {
                        ChessGame game = gson.fromJson(response.getString("game"), ChessGame.class);
                        gameList.add(new GameData(response.getInt("id"), response.getString("whiteUsername"), response.getString("blackUsername"), response.getString("gameName"), game));
                    }
                    return gameList.toArray(new GameData[gameList.size()]);
                }
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public void updateGame(GameData game) throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("UPDATE GameData SET whiteUsername = ?, blackUsername = ?, game = ? WHERE id = ?")) {
                if (game.game() == null) {
                    throw new Exception("Game cannot be null");
                }
                Gson gson = new Gson();
                preparedStatement.setString(1, game.whiteUsername());
                preparedStatement.setString(2, game.blackUsername());
                preparedStatement.setString(3, gson.toJson(game.game()));
                preparedStatement.setInt(4, game.gameID());

                preparedStatement.executeUpdate();
            }
        } catch (Exception exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    public void clear() throws DataAccessException {
        try (var connection = DatabaseManager.getConnection()) {
            try (var preparedStatement = connection.prepareStatement("DELETE FROM UserData")){
                preparedStatement.executeUpdate();
            }
            try (var preparedStatement = connection.prepareStatement("DElETE FROM AuthData")) {
                preparedStatement.executeUpdate();
            }
            try (var preparedStatement = connection.prepareStatement("DELETE FROM GameData")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    private final String[] createAuthTableStatement = {
            """
            CREATE TABLE IF NOT EXISTS AuthData (
                `authToken` varchar(256) NOT NULL,
                `username` varchar(256) NOT NULL,
                PRIMARY KEY (`authToken`)
            )
            """
    };

    private final String[] createUserTableStatement = {
            """
            CREATE TABLE IF NOT EXISTS UserData (
                `username` varchar(256) NOT NULL,
                `password` varchar(256) NOT NULL,
                `email` varchar(256) NOT NULL,
                PRIMARY KEY (`username`)
            )
            """
    };

    private final String[] createGameTableStatement = {
            """
            CREATE TABLE IF NOT EXISTS GameData (
                `id` int NOT NULL AUTO_INCREMENT,
                `whiteUsername` varchar(256) NULL,
                `blackUsername` varchar(256) NULL,
                `gameName` varchar(256) NOT NULL,
                `game` LONGTEXT NOT NULL,
                PRIMARY KEY (`id`)
            )
            """
    };

    private void configureDB() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var connection = DatabaseManager.getConnection()) {
            for (var statement : createAuthTableStatement) {
                try (var preparedStatement = connection.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
            for (var statement: createGameTableStatement) {
                try (var preparedStatement = connection.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
            for (var statement: createUserTableStatement) {
                try (var preparedStatement = connection.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s",ex.getMessage()));
        }
    }
}
