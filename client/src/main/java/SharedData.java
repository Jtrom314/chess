import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import ui.BoardUI;

import java.util.Collection;
import java.util.HashSet;

public class SharedData {
    ChessGame game;
    ChessGame.TeamColor currentTeamColor;
    BoardUI boardUI = new BoardUI();

    public ChessGame getGame() {
        return game;
    }
    public ChessGame.TeamColor getCurrentTeamColor() {
        return this.currentTeamColor;
    }

    public void setGame(ChessGame game) {
        this.game = game;

    }
    public void setCurrentTeamColor(String color) {
        switch (color) {
            case "WHITE":
                this.currentTeamColor = ChessGame.TeamColor.WHITE;
                break;
            case "BLACK":
                this.currentTeamColor = ChessGame.TeamColor.BLACK;
                break;
            default:
                this.currentTeamColor = null;
        }
    }

    public void redrawBoard () {
        System.out.println();
        if (currentTeamColor == ChessGame.TeamColor.BLACK) {
            boardUI.printBlackPerspective(getGame(), false, null, null);
        } else {
            boardUI.printWhitePerspective(getGame(), false, null, null);
        }
    }

    public void highlightBoard (ChessPosition startingPosition) {
        ChessPiece currentPiece = getGame().getBoard().getPiece(startingPosition);
        if (currentPiece == null) {
            System.out.println("Cannot highlight moves for empty space");
            return;
        }

        if (currentPiece.getTeamColor() != getGame().getTeamTurn()) {
            System.out.println("Cannot highlight moves for opposing team");
            return;
        }

        Collection<ChessMove> validMoves = getGame().validMoves(startingPosition);

        if (validMoves == null) {
            validMoves = new HashSet<>();
        }

        if (getCurrentTeamColor() == ChessGame.TeamColor.BLACK) {
            boardUI.printBlackPerspective(getGame(), true, startingPosition, validMoves);
        } else {
            boardUI.printWhitePerspective(getGame(), true, startingPosition, validMoves);
        }
    }

}
