package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.UserData;
import server.ResponseException;
import server.request.RegisterRequest;
import server.result.RegisterResult;

public class RegisterService extends SharedService {
    public RegisterService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public RegisterResult register(RegisterRequest request) throws ResponseException {
        try {
            if (dataAccess.getUser(request.username()) == null) {
                String authToken = dataAccess.createAuthentication(request.username());
                UserData user = new UserData(request.username(), request.password(), request.email());
                dataAccess.createUser(user);
                return new RegisterResult(request.username(), authToken);
            } else {
                throw new ResponseException(403, "unauthorized");
            }
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
