package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.sql.SQLException;

public class SQLDataAccess implements DataAccess {

    public SQLDataAccess() throws DataAccessException {
        configureDB();
    }
    public void createUser(UserData data) throws DataAccessException{
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(data.password());
        try (var connection = DatabaseManager.getConnection()) {
            var statement = "INSERT INTO UserData (username, password, email) VALUES (?, ?, ?)";
            try (var preparedStatement = connection.prepareStatement(statement)) {
                preparedStatement.setString(1, data.username());
                preparedStatement.setString(2, hashedPassword);
                preparedStatement.setString(3, data.email());
            }
        } catch (SQLException exception) {
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
        return null;
    }

    public AuthData getAuthenticationByAuthToken(String authToken) throws DataAccessException {
        return null;
    }

    public AuthData getAuthenticationByUsername(String username) throws DataAccessException {
        return null;
    }

    public void removeAuthentication(AuthData auth) throws DataAccessException {

    }

    public void createGame(GameData game) throws DataAccessException {

    }

    public GameData getGameById(int id) throws DataAccessException {
        return null;
    }

    public GameData[] getGameList() throws DataAccessException {
        return null;
    }

    public void updateGame(GameData game) throws DataAccessException {

    }

    public void clear() throws DataAccessException {

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
                `game` TEXT NOT NULL,
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
