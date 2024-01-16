package server.handlers;

import chess.ChessGame;
import com.google.gson.Gson;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import server.ResponseException;
import server.request.CreateGameRequest;
import server.request.JoinGameRequest;
import server.result.CreateGameResult;
import server.result.GameListResult;
import service.AuthenticationService;
import service.GameService;
import spark.Request;
import spark.Response;

public class GameHandlers {
    private final GameService gameService;
    private final AuthenticationService authService;
    private int gameID = 0;
    public GameHandlers(DataAccess dataAccess) {
        this.gameService = new GameService(dataAccess);
        this.authService = new AuthenticationService(dataAccess);
    }



    public Object listGames(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        String authToken = req.headers("authorization");
        try {
            AuthData auth = authService.getAuthByAuthToken(authToken);
            if (!auth.authToken().equals(authToken)) {
                throw new ResponseException(401, "unauthorized");
            }
            GameData[] games = gameService.getGames();
            return gson.toJson(new GameListResult(games));
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }

    public Object createGame(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        CreateGameRequest request = (CreateGameRequest)gson.fromJson(req.body(), CreateGameRequest.class);
        String authToken = req.headers("authorization");
        try {
            AuthData auth = authService.getAuthByAuthToken(authToken);
            if (!auth.authToken().equals(authToken)) {
                throw new ResponseException(401, "unauthorized");
            }
            GameData game = new GameData(gameID, null, null, request.gameName(), new ChessGame());
            gameService.createGame(game);
            gameID++;
            return gson.toJson(new CreateGameResult(gameID));
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }

    public Object joinGame(Request req, Response res) throws ResponseException {
        Gson gson = new Gson();
        JoinGameRequest request = (JoinGameRequest)gson.fromJson(req.body(), JoinGameRequest.class);
        String authToken = req.headers("authorization");
        try {
            AuthData auth = authService.getAuthByAuthToken(authToken);
            if (!auth.authToken().equals(authToken)) {
                throw new ResponseException(401, "unauthorized");
            }
            GameData game = gameService.getGame(request.gameID());
            switch (request.playerColor()) {
                case "BLACK":
                    if (game.blackUsername() != null) {
                        throw new ResponseException(403, "already taken");
                    } else {
                        GameData updatedGame = new GameData(game.gameID(), game.whiteUsername(), auth.username(), game.gameName(), game.game());
                        gameService.updateGame(updatedGame);
                        res.status(200);
                    }
                    break;
                case "WHITE":
                    if (game.whiteUsername() != null) {
                        throw new ResponseException(403, "already taken");
                    } else {
                        GameData updatedGame = new GameData(game.gameID(), auth.username(), game.blackUsername(), game.gameName(), game.game());
                        gameService.updateGame(updatedGame);
                        res.status(200);
                    }
                    break;
                default:
                    res.status(200);
            }
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
        return null;
    }
}
