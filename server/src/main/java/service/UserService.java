package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;

public class UserService extends SharedService{

    public UserService (DataAccess dataAccess, AuthenticationService authService) {
        super(dataAccess, authService);
    }

    //getUser
    public UserData getUser(String username) throws DataAccessException{
        try {
            return dataAccess.getUser(username);
        } catch (DataAccessException exception) {
            //
        }
        return null;
    }

    // forwardAuthCreation
    public String forwardAuthCreation(String username) throws DataAccessException {
        try {
            return authService.createAuth(username);
        } catch (DataAccessException exception) {
            //
        }
        return null;
    }
}
