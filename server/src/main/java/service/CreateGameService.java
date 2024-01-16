package service;

import chess.ChessGame;
import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import server.ResponseException;
import server.request.CreateGameRequest;
import server.result.CreateGameResult;

public class CreateGameService extends SharedService {
    public CreateGameService(DataAccess dataAccess) {
        super(dataAccess);
        this.gameID = 0;
    }

    private int gameID;

    public CreateGameResult createGame(String authToken, CreateGameRequest request) throws ResponseException {
        try {
            AuthData auth = dataAccess.getAuthenticationByAuthToken(authToken);
            if (auth == null) {
                throw new ResponseException(401, "unauthorized");
            }
            GameData game = new GameData(gameID, null, null, request.gameName(), new ChessGame());
            dataAccess.createGame(game);
            gameID++;
            return new CreateGameResult(gameID);
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
