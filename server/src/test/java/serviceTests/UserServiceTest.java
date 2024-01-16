package serviceTests;

import dataAccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import service.AuthenticationService;
import service.UserService;

public class UserServiceTest {
    static final UserService userService = new UserService(new MemoryDataAccess());

    @BeforeEach
    void clear () throws ResponseException {

    }

    @Test
    void getUserPositive () throws ResponseException {

    }

    @Test
    void getUserNegative () throws ResponseException {

    }

    @Test
    void createUserPositive () throws ResponseException {

    }

    @Test
    void createUserNegative () throws ResponseException {

    }
}
