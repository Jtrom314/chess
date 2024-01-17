package serviceTests;

import chess.ChessGame;
import dataAccess.MemoryDataAccess;
import model.GameData;
import model.UserData;
import org.junit.jupiter.api.Test;
import service.ClearService;

import static org.junit.jupiter.api.Assertions.*;

public class ClearTest {
    static final ClearService service = new ClearService(new MemoryDataAccess());

    @Test
    void clear () throws Exception {
        // Add a User
        UserData user = new UserData("TEST", "TEST", "TEST");
        service.dataAccess.createUser(user);


        // Add an Authentication
        service.dataAccess.createAuthentication("TEST");

        // Add a Game
        service.dataAccess.createGame(new GameData(0, "TEST", null, "TEST", new ChessGame()));

        // Clear
        service.clear();

        GameData[] list = service.dataAccess.getGameList();

        assertEquals(0, list.length);
    }
}
