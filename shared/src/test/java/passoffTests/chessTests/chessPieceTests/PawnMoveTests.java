package passoffTests.chessTests.chessPieceTests;

import chess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import passoffTests.TestFactory;

import java.util.HashSet;

import static passoffTests.TestFactory.*;

public class PawnMoveTests {

    @Test
    public void pawnMiddleOfBoardWhite() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |P| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(4, 4),
                endPositions(new int[][]{{5, 4}})
        );
    }

    @Test
    public void pawnMiddleOfBoardBlack() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |p| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(4, 4),
                endPositions(new int[][]{{3, 4}})
        );
    }


    @Test
    public void pawnInitialMoveWhite() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | |P| | | |
                        | | | | | | | | |
                        """,
                startPosition(2, 5),
                endPositions(new int[][]{{3, 5}, {4, 5}})
        );
    }

    @Test
    public void pawnInitialMoveBlack() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | |p| | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(7, 3),
                endPositions(new int[][]{{6, 3}, {5, 3}})
        );
    }


    @Test
    public void pawnPromotionWhite() { // Pass
        validatePromotion("""
                        | | | | | | | | |
                        | | |P| | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(7, 3),
                endPositions(new int[][]{{8, 3}})
        );
    }


    @Test
    public void edgePromotionBlack() { // Pass
        validatePromotion("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | |p| | | | | |
                        | | | | | | | | |
                        """,
                startPosition(2, 3),
                endPositions(new int[][]{{1, 3}})
        );
    }


    @Test
    public void pawnPromotionCapture() { // Pass
        validatePromotion("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | |p| | | | | | |
                        |N| | | | | | | |
                        """,
                startPosition(2, 2),
                endPositions(new int[][]{{1, 1}, {1, 2}})
        );
    }


    @Test
    public void pawnAdvanceBlockedWhite() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |n| | | | |
                        | | | |P| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(4, 4),
                endPositions(new int[][]{})
        );
    }

    @Test
    public void pawnAdvanceBlockedBlack() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |p| | | | |
                        | | | |r| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(5, 4),
                endPositions(new int[][]{})
        );
    }


    @Test
    public void pawnAdvanceBlockedDoubleMoveWhite() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | |p| |
                        | | | | | | | | |
                        | | | | | | |P| |
                        | | | | | | | | |
                        """,
                startPosition(2, 7),
                endPositions(new int[][]{{3, 7}})
        );
    }

    @Test
    public void pawnAdvanceBlockedDoubleMoveBlack() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | |p| | | | | |
                        | | |p| | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(7, 3),
                endPositions(new int[][]{})
        );
    }


    @Test
    public void pawnCaptureWhite() { // Pass
        validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | |r| |N| | | |
                        | | | |P| | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(4, 4),
                endPositions(new int[][]{{5, 3}, {5, 4}})
        );
    }

    @Test
    public void pawnCaptureBlack() {
        validateMoves("""
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        | | | |p| | | | |
                        | | | |n|R| | | |
                        | | | | | | | | |
                        | | | | | | | | |
                        """,
                startPosition(4, 4),
                endPositions(new int[][]{{3, 5}})
        );
    }

    private void validatePromotion(String boardText, ChessPosition start, int[][] endPositions) {

        var board = TestFactory.loadBoard(boardText);
        var testPiece = board.getPiece(start);
        var validMoves = new HashSet<ChessMove>();
        for (var endPosition : endPositions) {
            var end = startPosition(endPosition[0], endPosition[1]);
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.QUEEN));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.BISHOP));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.ROOK));
            validMoves.add(TestFactory.getNewMove(start, end, ChessPiece.PieceType.KNIGHT));
        }

        Assertions.assertEquals(validMoves, testPiece.pieceMoves(board, start), "Wrong moves");
    }

}
