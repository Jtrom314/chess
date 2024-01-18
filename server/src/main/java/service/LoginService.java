package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.UserData;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import server.ResponseException;
import server.request.LoginRequest;
import server.result.LoginResult;

public class LoginService extends SharedService {

    public LoginService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public LoginResult login(LoginRequest request) throws ResponseException {
        try {
            UserData response = dataAccess.getUser(request.username());
            if (response != null) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
                if (encoder.matches(request.password(), response.password())) {
                    String authToken = dataAccess.createAuthentication(request.username());
                    return new LoginResult(request.username(), authToken);
                }
            }
            throw new ResponseException(401, "unauthorized");
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
