package chess;

import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    int BOARDSIZE = 8;
    ChessPiece[][] chessboard = new ChessPiece[BOARDSIZE][BOARDSIZE];
    public ChessBoard(ChessBoard board) {
        this.chessboard = board.chessboard;
        this.BOARDSIZE = board.BOARDSIZE;
    }

    public ChessBoard() {

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        int row = position.getRow();
        int col = position.getColumn();

        this.chessboard[BOARDSIZE - row][col - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        return this.chessboard[BOARDSIZE - row][col - 1];
    }

    public void removePiece(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();

        this.chessboard[BOARDSIZE - row][col - 1] = null;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        this.chessboard = new ChessPiece[BOARDSIZE][BOARDSIZE];

        // WHITE
        ChessPosition RW1 = new ChessPosition(1, 1);
        ChessPosition RW2 = new ChessPosition(1, 8);
        ChessPiece RKW1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece RKW2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        addPiece(RW1, RKW1);
        addPiece(RW2, RKW2);

        ChessPosition NW1 = new ChessPosition(1, 2);
        ChessPosition NW2 = new ChessPosition(1, 7);
        ChessPiece KNW1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece KNW2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        addPiece(NW1, KNW1);
        addPiece(NW2, KNW2);

        ChessPosition BW1 = new ChessPosition(1, 3);
        ChessPosition BW2 = new ChessPosition(1, 6);
        ChessPiece BIW1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece BIW2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        addPiece(BW1, BIW1);
        addPiece(BW2, BIW2);

        ChessPosition KW = new ChessPosition(1, 5);
        ChessPosition QW = new ChessPosition(1, 4);
        ChessPiece KIW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece QUW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        addPiece(KW, KIW);
        addPiece(QW, QUW);

        for (int i = 0; i < BOARDSIZE; i++) {
            ChessPosition pawn_pos = new ChessPosition(2, i+1);
            ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            addPiece(pawn_pos, pawn);
        }

        // BLACK
        ChessPosition RB1 = new ChessPosition(8, 1);
        ChessPosition RB2 = new ChessPosition(8, 8);
        ChessPiece RKB1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece RKB2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        addPiece(RB1, RKB1);
        addPiece(RB2, RKB2);

        ChessPosition NB1 = new ChessPosition(8, 2);
        ChessPosition NB2 = new ChessPosition(8, 7);
        ChessPiece KNB1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece KNB2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        addPiece(NB1, KNB1);
        addPiece(NB2, KNB2);

        ChessPosition BB1 = new ChessPosition(8, 3);
        ChessPosition BB2 = new ChessPosition(8, 6);
        ChessPiece BIB1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece BIB2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        addPiece(BB1, BIB1);
        addPiece(BB2, BIB2);

        ChessPosition KB = new ChessPosition(8, 5);
        ChessPosition QB = new ChessPosition(8, 4);
        ChessPiece KIB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece QUB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        addPiece(KB, KIB);
        addPiece(QB, QUB);

        for (int i = 0; i < BOARDSIZE; i++) {
            ChessPosition pawn_pos = new ChessPosition(7, i+1);
            ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            addPiece(pawn_pos, pawn);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessBoard that = (ChessBoard) o;
        return BOARDSIZE == that.BOARDSIZE && Arrays.deepEquals(chessboard, that.chessboard);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(BOARDSIZE);
        result = 31 * result + Arrays.deepHashCode(chessboard);
        return result;
    }
}
