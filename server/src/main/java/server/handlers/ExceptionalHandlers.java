package server.handlers;

import dataAccess.DataAccess;
import server.ResponseException;
import service.ClearService;
import spark.Request;
import spark.Response;

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

    public void handleException(ResponseException ex, Request req, Response res) {
        res.status(ex.statusCode());
        res.body(ex.getMessage());
    }
}
