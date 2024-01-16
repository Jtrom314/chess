package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import server.ResponseException;

public class ClearService extends SharedService {
    public ClearService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public void clear() throws ResponseException {
        try {
            dataAccess.clear();
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
