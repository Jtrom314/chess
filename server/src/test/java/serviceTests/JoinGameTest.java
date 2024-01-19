package serviceTests;

import chess.ChessGame;
import dataAccess.MemoryDataAccess;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import server.request.JoinGameRequest;
import service.JoinGameService;

import static org.junit.jupiter.api.Assertions.*;
public class JoinGameTest {
    static final JoinGameService service = new JoinGameService(new MemoryDataAccess());

    @BeforeEach
    void clear() throws Exception {
        service.dataAccess.clear();
    }

    @Test
    void JoinGameSuccessful () throws Exception {
        String authToken = service.dataAccess.createAuthentication("TEST");
        int gameID;

        gameID = service.dataAccess.createGame(new GameData(0, null, null, "Test", new ChessGame()));

        service.joinGame(authToken, new JoinGameRequest("WHITE", gameID));

        GameData game = service.dataAccess.getGameById(gameID);

        GameData expected = new GameData(gameID, "TEST", null, "Test", game.game());
        GameData actual = service.dataAccess.getGameById(gameID);

        assertEquals(expected, actual);

        gameID = service.dataAccess.createGame(new GameData(1, "ALREADY", "TAKEN", "Test", new ChessGame()));

        service.joinGame(authToken, new JoinGameRequest(null, gameID));

        game = service.dataAccess.getGameById(gameID);
        expected = new GameData(gameID, "ALREADY", "TAKEN", "Test", game.game());
        actual = service.dataAccess.getGameById(gameID);

        assertEquals(expected, actual);
    }

    @Test
    void JoinFullGameThrowsException () throws Exception{
        String authToken = service.dataAccess.createAuthentication("TEST");

        int gameID = service.dataAccess.createGame(new GameData(0, "ALREADY", "TAKEN", "Test", new ChessGame()));

        ResponseException exception = assertThrows(ResponseException.class, () -> service.joinGame(authToken, new JoinGameRequest("BLACK", gameID)));
        assertTrue(exception.getMessage().contains("already taken"));
    }
}
