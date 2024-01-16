package serviceTests;

import dataAccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.AuthenticationService;
import service.GameService;

public class GameServiceTest {
    static final GameService gameService = new GameService(new AuthenticationService(new MemoryDataAccess()));

    @BeforeEach
    void clear () throws RuntimeException {

    }

    @Test
    void getGamesPositive () throws RuntimeException {

    }

    @Test
    void getGamesNegative () throws RuntimeException {

    }

    @Test
    void getGamePositive () throws RuntimeException {

    }

    @Test
    void getGameNegative () throws RuntimeException {

    }

    @Test
    void createGamePositive () throws RuntimeException {

    }

    @Test
    void createGameNegative () throws RuntimeException {

    }

    @Test
    void updateGamePositive () throws RuntimeException {

    }

    @Test
    void updateGameNegative () throws RuntimeException {

    }
}
