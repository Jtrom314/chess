package serviceTests;

import dataAccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import service.LogoutService;

import static org.junit.jupiter.api.Assertions.*;
public class LogoutTest {
    static final LogoutService service = new LogoutService(new MemoryDataAccess());

    @BeforeEach
    void clear() throws Exception {
        service.dataAccess.clear();
    }

    @Test
    void LogoutSuccessful () throws Exception {
        String authToken = service.dataAccess.createAuthentication("TEST");

        service.logout(authToken);

        assertNull(service.dataAccess.getAuthenticationByAuthToken(authToken));
    }

    @Test
    void LogoutUnsuccessful () {
        String authToken = "Test";

        ResponseException exception = assertThrows(ResponseException.class, () -> service.logout(authToken));
        assertTrue(exception.getMessage().contains("unauthorized"));
    }
}
