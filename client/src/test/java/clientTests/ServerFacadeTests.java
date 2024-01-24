package clientTests;

import facade.ServerFacade;
import org.junit.jupiter.api.*;
import result.CreateGameResult;
import result.ListGameResult;
import result.LoginResult;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;
public class ServerFacadeTests {

    private static Server server;
    public static ServerFacade facade;

    @BeforeEach
    public void init() throws Exception {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade();
        facade.clear();
    }

    @AfterEach
    void stopServer() {
        server.stop();
    }


    @Test
    public void clear() {
        assertDoesNotThrow(() -> facade.clear());
    }

    @Test
    public void registerUser () throws Exception {
        LoginResult expected = new LoginResult("TEST", "RANDOM");
        LoginResult actual = facade.register("TEST", "TEST", "TEST");

        assertEquals(expected.username(), actual.username());
        assertFalse(actual.authToken().isEmpty());
    }

    @Test
    public void registerThrowsOnDuplicateUser () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        Exception exception = assertThrows(Exception.class, () -> facade.register("TEST", "TEST", "RANDOM"));
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    public void loginUserSuccessful () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        facade.logout(firstUser.authToken());

        assertDoesNotThrow(() -> facade.login("TEST", "TEST"));
    }

    @Test
    public void loginUserThrowsWithIncorrectPassword () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        facade.logout(firstUser.authToken());

        Exception exception = assertThrows(Exception.class, () -> facade.login("TEST", "test"));
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    public void logoutSuccessful () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        assertDoesNotThrow(() -> facade.logout(firstUser.authToken()));
    }

    @Test
    public void logoutThrowsUnauthorized () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        Exception exception = assertThrows(Exception.class, () -> facade.logout(firstUser.username()));
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    public void createGameSuccessful () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        assertDoesNotThrow(() -> facade.createGame(firstUser.authToken(), "TEST"));
    }

    @Test
    public void createGameThrowsUnauthorized () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        Exception exception = assertThrows(Exception.class, () -> facade.createGame(firstUser.username(), "NO GOOD"));
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    public void listGamesReturnsAllGames () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        facade.createGame(firstUser.authToken(), "Test1");
        facade.createGame(firstUser.authToken(), "Test2");

        ListGameResult games = facade.listGames(firstUser.authToken());
        assertEquals(2, games.games().length);
    }

    @Test
    void listGamesThrowsUnauthorized () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        facade.createGame(firstUser.authToken(), "Test1");
        facade.createGame(firstUser.authToken(), "Test2");

        Exception exception = assertThrows(Exception.class, () -> facade.listGames(firstUser.username()));
        assertFalse(exception.getMessage().isEmpty());
    }

    @Test
    void joinGameAllowsToJoin () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        CreateGameResult result1 = facade.createGame(firstUser.authToken(), "Test1");
        CreateGameResult result2 = facade.createGame(firstUser.authToken(), "Test2");

        assertDoesNotThrow(() -> facade.joinGame(firstUser.authToken(), result1.gameID(), "WHITE"));
        assertDoesNotThrow(() -> facade.joinGame(firstUser.authToken(), result2.gameID(), "BLACK"));
    }

    @Test
    void joinGameThrowsUnauthorized () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        CreateGameResult result1 = facade.createGame(firstUser.authToken(), "Test1");
        CreateGameResult result2 = facade.createGame(firstUser.authToken(), "Test2");

        Exception exception1 = assertThrows(Exception.class, () -> facade.joinGame(firstUser.username(), result1.gameID(), "WHITE"));
        Exception exception2 = assertThrows(Exception.class, () -> facade.joinGame(firstUser.authToken(), result2.gameID(), "GRAY"));

        assertFalse(exception1.getMessage().isEmpty());
        assertFalse(exception2.getMessage().isEmpty());
    }

    @Test
    void observeGameAllowsToObserve () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        CreateGameResult result1 = facade.createGame(firstUser.authToken(), "Test1");
        CreateGameResult result2 = facade.createGame(firstUser.authToken(), "Test2");

        assertDoesNotThrow(() -> facade.observeGame(firstUser.authToken(), result1.gameID()));
        assertDoesNotThrow(() -> facade.observeGame(firstUser.authToken(), result2.gameID()));
    }

    @Test
    void observeGameThrowsUnauthorized () throws Exception {
        LoginResult firstUser = facade.register("TEST", "TEST", "TEST");

        CreateGameResult result1 = facade.createGame(firstUser.authToken(), "Test1");
        CreateGameResult result2 = facade.createGame(firstUser.authToken(), "Test2");

        Exception exception1 = assertThrows(Exception.class, () -> facade.observeGame(firstUser.username(), result1.gameID()));
        Exception exception2 = assertThrows(Exception.class, () -> facade.observeGame(firstUser.username(), result2.gameID()));

        assertFalse(exception1.getMessage().isEmpty());
        assertFalse(exception2.getMessage().isEmpty());
    }
}
