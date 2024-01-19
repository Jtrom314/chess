package databaseAccessTests;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.SQLDataAccess;
import model.AuthData;
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

}
