package serviceTests;

import dataAccess.MemoryDataAccess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import server.ResponseException;
import service.AuthenticationService;
import service.SharedService;

public class SharedServiceTest {
    static final SharedService sharedService = new SharedService(new AuthenticationService(new MemoryDataAccess()));

    @BeforeEach
    void clear () throws ResponseException {

    }

    @Test
    void verifyAuthTokenPositive() throws ResponseException {

    }
    @Test
    void verifyAuthTokenNegative() throws ResponseException {

    }
}
