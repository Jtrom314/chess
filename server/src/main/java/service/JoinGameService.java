package service;

import dataAccess.DataAccess;
import dataAccess.DataAccessException;
import model.AuthData;
import model.GameData;
import server.ResponseException;
import server.request.JoinGameRequest;

public class JoinGameService extends SharedService {
    public JoinGameService(DataAccess dataAccess) {
        super(dataAccess);
    }

    public void joinGame(String authToken, JoinGameRequest request) throws ResponseException {
        try {
            AuthData auth = dataAccess.getAuthenticationByAuthToken(authToken);
            if (auth == null) {
                throw new ResponseException(401, "unauthorized");
            }
            GameData game = dataAccess.getGameById(request.gameID());
            GameData updatedGame;

            String playerColor = request.playerColor() == null ? "" : request.playerColor();
            switch (playerColor) {
                case "BLACK":
                    if (game.blackUsername() == null) {
                        updatedGame = new GameData(game.gameID(), game.whiteUsername(), auth.username(), game.gameName(), game.game());
                        dataAccess.updateGame(updatedGame);
                        return;
                    }
                    throw new ResponseException(403, "already taken");
                case "WHITE":
                    if (game.whiteUsername() == null) {
                        updatedGame = new GameData(game.gameID(), auth.username(), game.blackUsername(), game.gameName(), game.game());
                        dataAccess.updateGame(updatedGame);
                        return;
                    }
                    throw new ResponseException(403, "already taken");
                default:
                    return;
            }
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
