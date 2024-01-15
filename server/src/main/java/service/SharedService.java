package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;

public class SharedService {
    public final DataAccess dataAccess;
    public final AuthenticationService authService;
    public SharedService(DataAccess dataAccess, AuthenticationService authService) {
        this.dataAccess = dataAccess;
        this.authService = authService;
    }

    public AuthData verifyAuthToken(String authToken) throws DataAccessException {
        try {
            return authService.getAuthByAuthToken(authToken);
        } catch (DataAccessException exception) {
            //
        }
        return null;
    }
}
