package serviceTests;

import dataAccess.MemoryDataAccess;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import server.request.LoginRequest;
import server.result.LoginResult;
import service.LoginService;

import static org.junit.jupiter.api.Assertions.*;
public class LoginTest {
    static final LoginService service = new LoginService(new MemoryDataAccess());

    @BeforeEach
    void clear() throws Exception {
        service.dataAccess.clear();
    }

    @Test
    void canLogin () throws Exception {
        UserData user = new UserData("Test", "Test", "Test");

        service.dataAccess.createUser(user);

        LoginRequest request = new LoginRequest("Test", "Test");
        LoginResult actual = service.login(request);

        assertNotNull(actual.authToken());
        assertEquals(actual.username(), request.username());
    }

    @Test
    void cannotLogin () {
        ResponseException exception = assertThrows(ResponseException.class, () -> service.login(new LoginRequest("Test", "Test")));

        assertTrue(exception.getMessage().contains("unauthorized"));
    }
}
