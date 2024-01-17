package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import server.ResponseException;
import server.request.LoginRequest;
import server.result.LoginResult;

public class LoginService extends SharedService {

    public LoginService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public LoginResult login(LoginRequest request) throws ResponseException {
        try {
            if (dataAccess.getUser(request.username()) != null) {
                String authToken = dataAccess.createAuthentication(request.username());
                return new LoginResult(request.username(), authToken);
            } else {
                throw new ResponseException(401, "unauthorized");
            }
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
