package serviceTests;

import dataAccess.MemoryDataAccess;
import model.UserData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import server.request.RegisterRequest;
import server.result.RegisterResult;
import service.RegisterService;

import static org.junit.jupiter.api.Assertions.*;
public class RegisterTest {
    static final RegisterService service = new RegisterService(new MemoryDataAccess());

    @BeforeEach
    void clear() throws Exception {
        service.dataAccess.clear();
    }

    @Test
    void canRegister () throws Exception {
        RegisterRequest request = new RegisterRequest("Test", "Test", "Test");
        RegisterResult actual = service.register(request);

        assertNotNull(actual.authToken());
        assertEquals(request.username(), actual.username());
    }

    @Test
    void cannotRegister () throws Exception {
        UserData user = new UserData("Test", "Test", "Test");

        service.dataAccess.createUser(user);

        ResponseException exception = assertThrows(ResponseException.class, () -> service.register(new RegisterRequest("Test", "Test", "Test")));
        assertTrue(exception.getMessage().contains("unauthorized"));
    }
}
