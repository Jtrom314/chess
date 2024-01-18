package server.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import server.ResponseException;
import service.ClearService;
import spark.Request;
import spark.Response;

import java.util.Map;

public class ExceptionalHandlers {
    private final ClearService clearService;
    public ExceptionalHandlers(DataAccess dataAccess) {
        this.clearService = new ClearService(dataAccess);
    }

    public Object clear(Request req, Response res) throws ResponseException {
        try {
            clearService.clear();
        } catch (ResponseException exception) {
            throw new ResponseException(exception.statusCode(), exception.getMessage());
        }
        return null;
    }

    public Object handleException(ResponseException ex, Request req, Response res) {
        Gson gson = new Gson();
        res.status(ex.statusCode());
        var response = gson.toJson(Map.of("message", String.format("Error: %s", ex.getMessage())));
        res.body(response);
        return response;
    }
}
