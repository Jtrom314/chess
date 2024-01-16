package server.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.UserData;
import server.ResponseException;
import server.request.LoginRequest;
import server.request.RegisterRequest;
import server.result.RegisterResult;
import service.AuthenticationService;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandlers {
    private final UserService userService;
    private final AuthenticationService authService;
    public UserHandlers(DataAccess dataAccess) {
        this.userService = new UserService(dataAccess);
        this.authService = new AuthenticationService(dataAccess);
    }

    public Object register(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        RegisterRequest request = (RegisterRequest)gson.fromJson(req.body(), RegisterRequest.class);
        try {
            if (userService.getUser(request.username()) == null) {
                userService.createUser(new UserData(request.username(), request.password(), request.email()));
                String authToken = authService.createAuth(request.username());
                return gson.toJson(new RegisterResult(request.username(), authToken));
            } else {
                throw new ResponseException(403, "already Taken");
            }
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }

    public Object login(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        LoginRequest request = (LoginRequest)gson.fromJson(req.body(), LoginRequest.class);
        try {
            if (userService.getUser(request.username()) != null) {
                AuthData auth = authService.getAuthByUsername(request.username());
                return gson.toJson(auth);
            } else {
                throw new ResponseException(401, "unauthorized");
            }
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }

    public Object logout(Request req, Response res) throws ResponseException {
        String authToken = req.headers("authorization");
        try {
            AuthData auth = authService.getAuthByAuthToken(authToken);
            if (!auth.authToken().equals(authToken)) {
                throw new ResponseException(401, "unauthorized");
            }
            res.status(200);
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
        return null;
    }
}
