package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.UserData;
import server.ResponseException;

public class SharedService {
    public DataAccess dataAccess;
    public SharedService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

}
