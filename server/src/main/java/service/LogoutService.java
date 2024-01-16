package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import server.ResponseException;

public class LogoutService extends SharedService {
    public LogoutService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public void logout(String authToken) throws ResponseException {
        try {
            AuthData auth = dataAccess.getAuthenticationByAuthToken(authToken);
            if (auth == null) {
                throw new ResponseException(401, "unauthorized");
            }
            dataAccess.removeAuthentication(auth);
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
