package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;

public class UserService {
    private final DataAccess dataAccess;

    public UserService (DataAccess dataAccess) {
        this.dataAccess = dataAccess;
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

}
