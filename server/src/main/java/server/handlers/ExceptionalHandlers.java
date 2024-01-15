package server.handlers;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import server.ResponseException;
import spark.Request;
import spark.Response;

public class ExceptionalHandlers {
    private final DataAccess dataAccess;
    public ExceptionalHandlers(DataAccess dataAccess) {
        this.dataAccess = dataAccess;
    }

    public Object clear(Request req, Response res) throws ResponseException {
        try {
            dataAccess.clear();
            res.status(200);
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
        return null;
    }

    public void handleException(ResponseException ex, Request req, Response res) {
        res.status(ex.statusCode());
        res.body(ex.getMessage());
    }
}
