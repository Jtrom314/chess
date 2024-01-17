package serviceTests;

import chess.ChessGame;
import dataAccess.MemoryDataAccess;
import model.GameData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import server.result.ListGamesResult;
import service.ListGamesService;

import static org.junit.jupiter.api.Assertions.*;
public class ListGamesTest {
    static final ListGamesService service = new ListGamesService(new MemoryDataAccess());

    @BeforeEach
    void clear() throws Exception {
        service.dataAccess.clear();
    }

    @Test
    void ListGames () throws Exception {
        String authToken = service.dataAccess.createAuthentication("TEST");
        GameData game = new GameData(0, null, null, "Test", new ChessGame());

        service.dataAccess.createGame(game);

        ListGamesResult result = service.listGames(authToken);
        GameData[] actual = result.games();

        assertEquals(actual.length, 1);
    }
    @Test
    void ListGamesThrowsException () {
        String authToken = "Test";

        ResponseException exception = assertThrows(ResponseException.class, () -> service.listGames(authToken));
        assertTrue(exception.getMessage().contains("unauthorized"));
    }

}
