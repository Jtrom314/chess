package service;

import dataAccess.DataAccess;

public class SharedService {
    public DataAccess dataAccess;
    public SharedService(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

}
