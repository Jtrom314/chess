package server.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import service.LoginService;
import server.ResponseException;
import server.request.LoginRequest;
import server.request.RegisterRequest;
import service.LogoutService;
import service.RegisterService;
import spark.Request;
import spark.Response;

public class UserHandlers {
    private final LoginService loginService;
    private final RegisterService registerService;
    private final LogoutService logoutService;
    public UserHandlers(DataAccess dataAccess) {
        this.loginService  = new LoginService(dataAccess);
        this.registerService = new RegisterService(dataAccess);
        this.logoutService = new LogoutService(dataAccess);
    }

    public Object register(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        RegisterRequest request = gson.fromJson(req.body(), RegisterRequest.class);
        if (request.username() == null || request.password() == null || request.email() == null) {
            throw new ResponseException(400, "bad request");
        }
        try {
            return gson.toJson(registerService.register(request));
        } catch (ResponseException exception) {
            throw new ResponseException(exception.statusCode(), exception.getMessage());
        }
    }

    public Object login(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        LoginRequest request = (LoginRequest)gson.fromJson(req.body(), LoginRequest.class);
        try {
            return gson.toJson(loginService.login(request));
        } catch (ResponseException exception) {
            throw new ResponseException(exception.statusCode(), exception.getMessage());
        }
    }

    public Object logout(Request req, Response res) throws ResponseException {
        String authToken = req.headers("authorization");
        try {
            logoutService.logout(authToken);
        } catch (ResponseException exception) {
            throw new ResponseException(exception.statusCode(), exception.getMessage());
        }
        Gson gson = new Gson();
        return gson.toJson(new Object());
    }
}
