package clientTests;

import facade.ServerFacade;
import org.junit.jupiter.api.*;
import result.LoginResult;
import server.Server;

import static org.junit.jupiter.api.Assertions.*;
public class ServerFacadeTests {

    private static Server server;
    public static ServerFacade facade;

    @BeforeAll
    public static void init() throws Exception {
        server = new Server();
        var port = server.run(8080);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade();
        facade.clear();
    }

    @AfterAll
    static void stopServer() {
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

}
