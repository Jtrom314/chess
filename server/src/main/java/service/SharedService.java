package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;

public class SharedService {
    public final AuthenticationService authService;
    public final DataAccess dataAccess;
    public SharedService(AuthenticationService authService) {
        this.authService = authService;
        this.dataAccess = authService.dataAccess;
    }

    public AuthData verifyAuthToken(String authToken) throws DataAccessException {
        try {
            return authService.getAuthByAuthToken(authToken);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }
}
