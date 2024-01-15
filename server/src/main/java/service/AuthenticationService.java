package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;

public class AuthenticationService {
    private final DataAccess dataAccess;

    public AuthenticationService (DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    // CreateAuth
    public String createAuth(String username) throws DataAccessException{
        try {
            return dataAccess.createAuthentication(username);
        } catch (DataAccessException exception) {
            //
        }
        return null;
    }

    // getAuthByUsername
    public AuthData getAuthByUsername(String username) throws DataAccessException {
        try {
            return dataAccess.getAuthenticationByUsername(username);
        } catch (DataAccessException exception) {
            //
        }
        return null;
    }

    // getAuthByAuthToken
    public AuthData getAuthByAuthToken(String authToken) throws DataAccessException {
        try {
            return dataAccess.getAuthenticationByAuthToken(authToken);
        } catch (DataAccessException exception) {
            //
        }
        return null;
    }
}
