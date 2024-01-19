package databaseAccessTests;

import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.SQLDataAccess;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
public class SQLDataAccessTest {
    private DataAccess getDataAccess() throws Exception {
        return new SQLDataAccess();
    }

    @BeforeEach
    void clear() throws Exception {
        DataAccess dataAccess = getDataAccess();
        dataAccess.clear();
    }

    @Test
    void clearDoesNotThrowException () throws Exception {
        DataAccess dataAccess = getDataAccess();
        assertDoesNotThrow(dataAccess::clear);
    }

    @Test
    void createUserDoesNotThrowException () throws Exception {
        DataAccess dataAccess = getDataAccess();
        UserData testUser = new UserData("TEST", "TEST", "TEST");
        assertDoesNotThrow(() -> dataAccess.createUser(testUser));
    }

    @Test
    void createUserDoesThrowException () throws Exception {
        DataAccess dataAccess = getDataAccess();
        UserData testUser = new UserData(null, null, null);
        DataAccessException exception = assertThrows(DataAccessException.class, () -> dataAccess.createUser(testUser));
        assertFalse(exception.getMessage().isEmpty());
    }
    @Test
    void getUserWhenUserIsInDB () throws Exception {
        DataAccess dataAccess = getDataAccess();
        UserData testUser = new UserData("TEST", "TEST", "TEST");

        dataAccess.createUser(testUser);

        UserData response = dataAccess.getUser("TEST");
        assertEquals(testUser.username(), response.username());
        assertEquals(testUser.email(), response.email());
    }

    @Test
    void getUserFailsWhenUserIsNotInDB () throws Exception {
        DataAccess dataAccess = getDataAccess();
        assertNull(dataAccess.getUser("RANDOM"));
    }

    @Test
    void createAuthenticationDoesNotThrow () throws Exception {
        DataAccess dataAccess = getDataAccess();
        assertDoesNotThrow(() -> dataAccess.createAuthentication("TEST"));
        assertNotNull(dataAccess.createAuthentication("RANDOM"));
    }

    @Test
    void createAuthenticationDoesThrow () throws Exception {
        DataAccess dataAccess = getDataAccess();
        DataAccessException exception = assertThrows(DataAccessException.class, () -> dataAccess.createAuthentication(null));
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    void getAuthByAuthTokenReturnsAuthData () throws Exception {
        DataAccess dataAccess = getDataAccess();
        String username = "TEST";

        String authToken = dataAccess.createAuthentication(username);

        AuthData expected = new AuthData(authToken, username);
        AuthData actual = dataAccess.getAuthenticationByAuthToken(authToken);

        assertEquals(expected, actual);
    }

    @Test
    void getAuthByAuthTokenReturnsNull () throws Exception {
        DataAccess dataAccess = getDataAccess();
        assertNull(dataAccess.getAuthenticationByAuthToken("RANDOM"));
    }

    @Test
    void removeAuthenticationSuccessful () throws Exception {
        DataAccess dataAccess = getDataAccess();
        String authToken = dataAccess.createAuthentication("TEST");

        assertNotNull(dataAccess.getAuthenticationByAuthToken(authToken));
        assertDoesNotThrow(() -> dataAccess.removeAuthentication(new AuthData(authToken, "TEST")));
        assertNull(dataAccess.getAuthenticationByAuthToken(authToken));
    }

    @Test
    void removeAuthenticationDoesNotThrowWhenGivenRandomJunk () throws Exception {
        DataAccess dataAccess = getDataAccess();
        assertDoesNotThrow(() -> dataAccess.removeAuthentication(new AuthData("RANDOM", "RANDOM")));
    }

    @Test
    void createGameSuccessful () throws Exception {
        DataAccess dataAccess = getDataAccess();
        GameData gameData = new GameData(0, null, null, "Test", new ChessGame());
        assertNotEquals(0, dataAccess.createGame(gameData));
    }
    @Test
    void createGameThrows () throws Exception {
        DataAccess dataAccess = getDataAccess();
        GameData gameData = new GameData(0, null, null, null, null);
        DataAccessException exception = assertThrows(DataAccessException.class, () -> dataAccess.createGame(gameData));
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    void getGameById () throws Exception {
        DataAccess dataAccess = getDataAccess();
        GameData gameData = new GameData(0, null, null, "Test", new ChessGame());

        int gameID = dataAccess.createGame(gameData);

        GameData expected = new GameData(gameID, gameData.whiteUsername(), gameData.blackUsername(), gameData.gameName(), gameData.game());
        GameData actual = dataAccess.getGameById(gameID);
        assertEquals(expected, actual);
    }

    @Test
    void getGameByRandomID () throws Exception {
        DataAccess dataAccess = getDataAccess();

        assertNull(dataAccess.getGameById(0));
    }

    @Test
    void getGameListReturnsNonEmptyList () throws Exception {
        DataAccess dataAccess = getDataAccess();

        GameData gameData1 = new GameData(0, null, null, "Test1", new ChessGame());
        GameData gameData2 = new GameData(0, null, "Test", "Test2", new ChessGame());
        GameData gameData3 = new GameData(0, "Test", "Test", "Test3", new ChessGame());

        int gameID1 = dataAccess.createGame(gameData1);
        int gameID2 = dataAccess.createGame(gameData2);
        int gameID3 = dataAccess.createGame(gameData3);

        GameData[] expected = new GameData[3];
        expected[0] = new GameData(gameID1, gameData1.whiteUsername(), gameData1.blackUsername(), gameData1.gameName(), gameData1.game());
        expected[1] = new GameData(gameID2, gameData2.whiteUsername(), gameData2.blackUsername(), gameData2.gameName(), gameData2.game());
        expected[2] = new GameData(gameID3, gameData3.whiteUsername(), gameData3.blackUsername(), gameData3.gameName(), gameData3.game());


        GameData[] actual = dataAccess.getGameList();
        assertGameListEqual(expected, actual);
    }

    @Test
    void getGameListReturnsEmptyList () throws Exception {
        DataAccess dataAccess = getDataAccess();

        GameData[] actual = dataAccess.getGameList();
        assertEquals(0, actual.length);
    }

    public static void assertGameListEqual(GameData[] expected, GameData[] actual) {
        assertEquals(expected.length, actual.length);
        for (int i = 0; i < actual.length; i++) {
            assertEquals(expected[i], actual[i]);
        }
    }

}
