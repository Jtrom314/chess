package serviceTests;


import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import dataAccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import service.AuthenticationService;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationServiceTest {
    static final AuthenticationService authService = new AuthenticationService(new MemoryDataAccess());

    @BeforeEach
    void clear () throws ResponseException {

    }

    @Test
    void createAuthPositive() throws ResponseException {

    }

    @Test
    void createAuthNegative() throws ResponseException {

    }

    @Test
    void getAuthByUsernamePositive() throws ResponseException {

    }

    @Test
    void getAuthByUsernameNegative() throws ResponseException {

    }
    @Test
    void getAuthByAuthTokenPositive() throws ResponseException {

    }

    @Test
    void getAuthByAuthTokenNegative() throws ResponseException {

    }

}
