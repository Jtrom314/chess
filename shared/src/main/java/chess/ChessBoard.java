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
        for (int i = 0; i < BOARDSIZE; i++) {
            for (int j = 0; j < BOARDSIZE; j++) {
                ChessPiece blank = new ChessPiece();
                this.chessBoard[i][j] = blank;
            }
        }
        // White
        // Rooks
        ChessPiece RookW1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece RookW2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);

        ChessPosition RW1 = new ChessPosition(1,1);
        ChessPosition RW2 = new ChessPosition(1,8);

        addPiece(RW1, RookW1);
        addPiece(RW2, RookW2);

        // Knights
        ChessPiece KnightW1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece KnightW2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);

        ChessPosition KW1 = new ChessPosition(1,2);
        ChessPosition KW2 = new ChessPosition(1, 7);

        addPiece(KW1, KnightW1);
        addPiece(KW2, KnightW2);

        // Bishops
        ChessPiece BishopW1 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece BishopW2 = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);

        ChessPosition BW1 = new ChessPosition(1,3);
        ChessPosition BW2 = new ChessPosition(1,6);

        addPiece(BW1, BishopW1);
        addPiece(BW2, BishopW2);

        // Royals
        ChessPiece KingW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece QueenW = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);

        ChessPosition KW = new ChessPosition(1, 5);
        ChessPosition QW = new ChessPosition(1, 4);

        addPiece(KW, KingW);
        addPiece(QW, QueenW);

        // Pawns
        for (int i = 0; i < BOARDSIZE; i++) {
            ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            ChessPosition position = new ChessPosition(2, i+1);
            addPiece(position, pawn);
        }

        // Black
        // Rooks
        ChessPiece RookB1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece RookB2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);

        ChessPosition RB1 = new ChessPosition(8,1);
        ChessPosition RB2 = new ChessPosition(8,8);

        addPiece(RB1, RookB1);
        addPiece(RB2, RookB2);

        // Knights
        ChessPiece KnightB1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece KnightB2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);

        ChessPosition KB1 = new ChessPosition(8,2);
        ChessPosition KB2 = new ChessPosition(8, 7);

        addPiece(KB1, KnightB1);
        addPiece(KB2, KnightB2);

        // Bishops
        ChessPiece BishopB1 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece BishopB2 = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);

        ChessPosition BB1 = new ChessPosition(8,3);
        ChessPosition BB2 = new ChessPosition(8,6);

        addPiece(BB1, BishopB1);
        addPiece(BB2, BishopB2);

        // Royals
        ChessPiece KingB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece QueenB = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);

        ChessPosition KB = new ChessPosition(8, 5);
        ChessPosition QB = new ChessPosition(8, 4);

        addPiece(KB, KingB);
        addPiece(QB, QueenB);

        // Pawns
        for (int i = 0; i < BOARDSIZE; i++) {
            ChessPiece pawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
            ChessPosition position = new ChessPosition(7, i+1);
            addPiece(position, pawn);
        }


    }

    public void printBoard() {
        for (int i = 0; i < BOARDSIZE; i++) {
            int rowNumber = BOARDSIZE - i;
            System.out.printf("%d\t", rowNumber);
            for (int j = 0; j < BOARDSIZE; j++) {
                ChessPosition currentPosition = new ChessPosition(BOARDSIZE -i,j+1);
                ChessPiece currentChessPiece = getPiece(currentPosition);
                if (currentChessPiece == null) {
                    System.out.printf("-\t");
                } else {
                    System.out.printf("%s\t",currentChessPiece.toString());
                }
            }
            System.out.printf("\n");
        }
        System.out.printf("\t");
        for (int i = 1; i <= BOARDSIZE; i++) {
            System.out.printf("%d\t", i);
        }
    }
}
