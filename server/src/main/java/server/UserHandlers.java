package server;

import dataAccess.DataAccess;
import service.AuthenticationService;
import service.UserService;
import spark.Request;
import spark.Response;

public class UserHandlers {
    private DataAccess dataAccess;
    private UserService userService;
    public UserHandlers(DataAccess dataAccess, AuthenticationService authService) {
        this.dataAccess = dataAccess;
        this.userService = new UserService(dataAccess, authService);
    }

    public Object register(Request req, Response res) {




        return null;
    }
}
