package chess;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor current_team_color;
    private ChessBoard current_board;

    public ChessGame() {

    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return current_team_color;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.current_team_color = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition friendly_king_position = null;

        // Get all the validMoves from the opposing team
        Collection<ChessMove> all_valid_moves = new HashSet<>();
        ChessBoard current_board = getBoard();
        for (int i = 0; i < current_board.BOARDSIZE; i++) {
            for (int j = 0; j < current_board.BOARDSIZE; j++) {
                ChessPosition current_position = new ChessPosition(i + 1, j + 1);
                ChessPiece current_piece = current_board.getPiece(current_position);
                if (current_piece != null) {
                    // Piece is there
                    if (current_piece.getPieceType() == ChessPiece.PieceType.KING && current_piece.getTeamColor() == teamColor) {
                        friendly_king_position = current_position;
                    }
                    if (current_piece.getTeamColor() != teamColor) {
                        all_valid_moves.addAll(current_piece.pieceMoves(current_board, current_position));
                    }
                }
            }
        }

        if (all_valid_moves.isEmpty()) {
            return false;
        }

        for (ChessMove move : all_valid_moves) {
            ChessPosition move_end_position = move.getEndPosition();
            if (move_end_position.getColumn() == friendly_king_position.getColumn() && move_end_position.getRow() == friendly_king_position.getRow()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }

        // Get all the validMoves from the opposing team
        Collection<ChessMove> all_valid_king = new HashSet<>();
        ChessBoard current_board = getBoard();
        for (int i = 0; i < current_board.BOARDSIZE; i++) {
            for (int j = 0; j < current_board.BOARDSIZE; j++) {
                ChessPosition current_position = new ChessPosition(i + 1, j + 1);
                ChessPiece current_piece = current_board.getPiece(current_position);
                if (current_piece != null) {
                    // Piece is there
                    if (current_piece.getPieceType() == ChessPiece.PieceType.KING && current_piece.getTeamColor() == teamColor) {
                        all_valid_king.addAll(current_piece.pieceMoves(current_board, current_position));
                    }
                }
            }
        }

        ChessBoard temp_board = new ChessBoard(current_board);
        int counter = 0;
        // For each king valid move, move the king to the valid position and check to see if it is still in check
        for (ChessMove king_move : all_valid_king) {
            ChessPosition start_position = king_move.getStartPosition();
            ChessPosition end_position = king_move.getEndPosition();

            ChessPiece king = new ChessPiece(teamColor, ChessPiece.PieceType.KING);
            temp_board.removePiece(start_position);
            temp_board.addPiece(end_position, king);
            setBoard(temp_board);
            if (!isInCheck(teamColor)) {
                setBoard(current_board);
                return false;
            }
            setBoard(current_board);
            temp_board.removePiece(end_position);
            temp_board.addPiece(start_position, king);
            counter++;
        }
        return true;
    }
    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.current_board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.current_board;
    }
}
