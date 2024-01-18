package dataAccess;

import model.AuthData;
import model.GameData;
import model.UserData;

import java.sql.SQLException;

public class SQLDataAccess implements DataAccess {

    public SQLDataAccess() throws DataAccessException {
        configureDB();
    }
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
                `id` int NOT NULL,
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
