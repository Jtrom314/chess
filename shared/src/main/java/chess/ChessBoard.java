package chess;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */

public class ChessBoard {
    public int BOARDSIZE = 8;
    private ChessPiece[][] chessBoard = new ChessPiece[BOARDSIZE][BOARDSIZE];

    public ChessBoard() {
        resetBoard();
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

        this.chessBoard[BOARDSIZE - row][col - 1] = piece;
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

        ChessPiece currentPiece = this.chessBoard[BOARDSIZE - row][col - 1];
        return currentPiece.getPieceType() == null ? null : currentPiece;
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        // Create an empty board
        this.chessBoard = new ChessPiece[BOARDSIZE][BOARDSIZE];
        // Fill board with pieces
        // White
        for (int i = 0; i < BOARDSIZE; i++) {
            var pawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            var position = new ChessPosition(2, i+1);
            addPiece(position, pawn);
        }

        // Black


    }

    public void printBoard() {
        for (int i = 0; i < BOARDSIZE; i++) {
            int rowNumber = BOARDSIZE + 1 - i;
            System.out.printf("%d\t", rowNumber);
            for (int j = 0; j < BOARDSIZE; j++) {
                System.out.printf("-\t");
            }
            System.out.printf("\n");
        }
        System.out.printf("\t");
        for (int i = 1; i <= BOARDSIZE; i++) {
            System.out.printf("%d\t", i);
        }
    }
}
