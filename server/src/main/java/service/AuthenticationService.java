package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;

public class AuthenticationService {
    public final DataAccess dataAccess;

    public AuthenticationService (DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    // CreateAuth
    public String createAuth(String username) throws DataAccessException{
        try {
            return dataAccess.createAuthentication(username);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    // getAuthByUsername
    public AuthData getAuthByUsername(String username) throws DataAccessException {
        try {
            return dataAccess.getAuthenticationByUsername(username);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    // getAuthByAuthToken
    public AuthData getAuthByAuthToken(String authToken) throws DataAccessException {
        try {
            return dataAccess.getAuthenticationByAuthToken(authToken);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }


}
