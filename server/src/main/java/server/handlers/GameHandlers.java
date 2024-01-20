package server.handlers;

import com.google.gson.Gson;
import dataAccess.DataAccess;
import server.ResponseException;
import server.request.CreateGameRequest;
import server.request.JoinGameRequest;
import service.CreateGameService;
import service.JoinGameService;
import service.ListGamesService;
import spark.Request;
import spark.Response;


public class GameHandlers {

    private final ListGamesService listGamesService;
    private final JoinGameService joinGameService;
    private final CreateGameService createGameService;
    public GameHandlers(DataAccess dataAccess) {
        this.listGamesService = new ListGamesService(dataAccess);
        this.joinGameService = new JoinGameService(dataAccess);
        this.createGameService = new CreateGameService(dataAccess);
    }



    public Object listGames(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        String authToken = req.headers("authorization");
        try {
            return gson.toJson(listGamesService.listGames(authToken));
        } catch (ResponseException exception) {
            throw new ResponseException(exception.statusCode(), exception.getMessage());
        }
    }

    public Object createGame(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        CreateGameRequest request = (CreateGameRequest)gson.fromJson(req.body(), CreateGameRequest.class);
        if (request.gameName() == null) {
            throw new ResponseException(400, "bad request");
        }
        String authToken = req.headers("authorization");
        try {
            return gson.toJson(createGameService.createGame(authToken, request));
        } catch (ResponseException exception) {
            throw new ResponseException(exception.statusCode(), exception.getMessage());
        }
    }

    public Object joinGame(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        JoinGameRequest request = (JoinGameRequest)gson.fromJson(req.body(), JoinGameRequest.class);
        if (request.gameID() == 0) {
            throw new ResponseException(400, "bad request");
        }
        String authToken = req.headers("authorization");
        try {
            joinGameService.joinGame(authToken, request);
        } catch (ResponseException exception) {
            throw new ResponseException(exception.statusCode(), exception.getMessage());
        }
        return gson.toJson(new Object());
    }
}
