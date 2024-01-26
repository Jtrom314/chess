package service;

import chess.ChessBoard;
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
    }


    public CreateGameResult createGame(String authToken, CreateGameRequest request) throws ResponseException {
        try {
            AuthData auth = dataAccess.getAuthenticationByAuthToken(authToken);
            if (auth == null) {
                throw new ResponseException(401, "unauthorized");
            }
            ChessGame chessGame = new ChessGame();
            ChessBoard chessBoard = new ChessBoard();
            chessBoard.resetBoard();
            chessGame.setBoard(chessBoard);
            GameData game = new GameData(0, null, null, request.gameName(), chessGame);
            int gameID = dataAccess.createGame(game);
            return new CreateGameResult(gameID);
        } catch (DataAccessException exception) {
            throw new ResponseException(500, exception.getMessage());
        }
    }
}
