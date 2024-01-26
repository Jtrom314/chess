package webSocketMessages.userCommands;

import chess.ChessGame;
import chess.ChessMove;

import java.util.Objects;

/**
 * Represents a command a user can send the server over a websocket
 * 
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class UserGameCommand {

    public int gameID;
    public ChessGame.TeamColor playerColor;
    public ChessMove move;
    public UserGameCommand(String authToken) {
        this.authToken = authToken;
    }

    public enum CommandType {
        JOIN_PLAYER,
        JOIN_OBSERVER,
        MAKE_MOVE,
        LEAVE,
        RESIGN
    }

    protected CommandType commandType;

    private final String authToken;

    public String getAuthString() {
        return authToken;
    }

    public CommandType getCommandType() {
        return this.commandType;
    }

    public void setGameID(int gameID) {
        this.gameID = gameID;
    }

    public void setPlayerColor(ChessGame.TeamColor teamColor) {
        this.playerColor = teamColor;
    }
    public void setMove(ChessMove move) {
        this.move = move;
    }

    public int getGameID() {
        return this.gameID;
    }

    public ChessGame.TeamColor getPlayerColor () {
        return this.playerColor;
    }

    public ChessMove getMove() {
        return this.move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof UserGameCommand))
            return false;
        UserGameCommand that = (UserGameCommand) o;
        return getCommandType() == that.getCommandType() && Objects.equals(getAuthString(), that.getAuthString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCommandType(), getAuthString());
    }
}
