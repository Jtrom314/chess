package serviceTests;

import dataAccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import server.request.CreateGameRequest;
import server.result.CreateGameResult;
import service.CreateGameService;

import static org.junit.jupiter.api.Assertions.*;

public class CreateGameTest {
    static final CreateGameService service = new CreateGameService(new MemoryDataAccess());

    @BeforeEach
    void clear() throws Exception {
        service.dataAccess.clear();
    }

    @Test
    void CreatesGame () throws Exception {
        String authToken = service.dataAccess.createAuthentication("Test");
        CreateGameRequest request = new CreateGameRequest("TestGame");

        CreateGameResult expectedResult = new CreateGameResult(1);
        CreateGameResult actualResult = service.createGame(authToken, request);

        assertEquals(expectedResult, actualResult);
    }
    @Test
    void FailsToCreateGame () {
        String authToken = "Test";
        CreateGameRequest request = new CreateGameRequest("TestGame");

        ResponseException exception = assertThrows(ResponseException.class, () -> service.createGame(authToken, request));
        assertTrue(exception.getMessage().contains("unauthorized"));
    }
}
