package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;

public class UserService extends SharedService{

    public UserService (AuthenticationService authService) {
        super(authService);
    }

    //getUser
    public UserData getUser(String username) throws DataAccessException{
        try {
            return dataAccess.getUser(username);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    // createUser
    public void createUser(UserData user) throws DataAccessException {
        try {
            dataAccess.createUser(user);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    // forwardAuthCreation
    public String forwardAuthCreation(String username) throws DataAccessException {
        try {
            return authService.createAuth(username);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }

    // forwardAuthFetch
    public AuthData forwardAuthFetch(String username) throws DataAccessException {
        try {
            return authService.getAuthByUsername(username);
        } catch (DataAccessException exception) {
            throw new DataAccessException(exception.getMessage());
        }
    }
}
