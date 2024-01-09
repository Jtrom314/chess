package chess;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    private int BOARDSIZE = 8;
    private ChessGame.TeamColor color = null;
    private ChessPiece.PieceType type = null;
    public ChessPiece() {

    }
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.color = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.color;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        switch (getPieceType()) {
            case KING:
                return kingMoves(board, myPosition);
            case QUEEN:
                Collection<ChessMove> moves = bishopMoves(board, myPosition);
                moves.addAll(rookMoves(board, myPosition));
                return moves;
            case BISHOP:
                return bishopMoves(board, myPosition);
            case KNIGHT:
                return knightMoves(board, myPosition);
            case ROOK:
                return rookMoves(board, myPosition);
            case PAWN:
                return pawnMoves(board, myPosition);
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return BOARDSIZE == that.BOARDSIZE && color == that.color && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(BOARDSIZE, color, type);
    }

    public Collection<ChessMove> kingMoves(ChessBoard board, ChessPosition myPosition) {
        //Create an empty collection
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendlyTeamColor = getTeamColor();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        //Check each of the possible directions King can move
        //North (Subtract 1 from row)
        if (currentRow - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow -1, currentCol);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            } catch(NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
            }
        }
        //NE
        if (currentRow -1 > 0 && currentCol +1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow -1, currentCol+1);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            } catch(NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
            }
        }
        //East (Add 1 to column)
        if (currentCol + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol + 1);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            } catch(NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
            }
        }
        //SE
        if (currentRow + 1 < 9 && currentCol + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol + 1);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            } catch(NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
            }
        }
        //South (Add 1 to row)
        if (currentRow + 1 < 9) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            } catch(NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
            }
        }
        //SW
        if (currentRow + 1 < 9 && currentCol - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol - 1);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            } catch(NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
            }
        }
        //West (Subtract 1 from column)
        if (currentCol - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol - 1);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            } catch(NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
            }
        }
        //NW
        if (currentRow -1 > 0 && currentCol - 1 > 0) {
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol - 1);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            } catch(NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
            }
        }


        return validMoves;
    }
//    public Collection<ChessMove> queenMoves(ChessBoard board, ChessPosition myPosition) {}
    public Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        // Create an empty collection
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendlyTeamColor = getTeamColor();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        int iterator = 1;

        // Check to see what moves the bishop can make
        // NW
        while(true) {
            if (currentRow + iterator > 8 || currentCol - iterator == 0) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(currentRow + iterator, currentCol - iterator);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
                break;
            } catch (NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
                iterator++;
            }
        }


        iterator = 1;
        // SW
        while(true) {
            if (currentRow - iterator == 0 || currentCol - iterator == 0) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(currentRow - iterator, currentCol - iterator);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
                break;
            } catch (NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
                iterator++;
            }
        }


        iterator = 1;
        // NE
        while(true) {
            if (currentRow + iterator > 8 || currentCol + iterator > 8) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(currentRow + iterator, currentCol + iterator);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
                break;
            } catch (NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
                iterator++;
            }
        }


        iterator = 1;
        // SE
        while(true) {
            if (currentRow - iterator == 0 || currentCol + iterator > 8) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(currentRow - iterator, currentCol + iterator);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
                break;
            } catch (NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
                iterator++;
            }
        }

        return validMoves;
    }
    public Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        // Create an empty collection
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendlyTeamColor = getTeamColor();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        // Check to see what moves the knight can make
        // 2 spaces up
        if (currentRow + 2 < 8) {
            // 1 space left
            if (currentCol - 1 > 0) {
                ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol -1);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(validMove);
                    }
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }

            // 1 space right
            if (currentCol + 1 < 8) {
                ChessPosition newPosition = new ChessPosition(currentRow + 2, currentCol +1);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(validMove);
                    }
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }
        }


        // 2 spaces left
        if (currentCol - 2 > 0) {
            // 1 space up
            if (currentRow + 1 <= 8) {
                ChessPosition newPosition = new ChessPosition(currentRow +1, currentCol -2);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(validMove);
                    }
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }
            // 1 space down
            if (currentRow -1 > 0) {
                ChessPosition newPosition = new ChessPosition(currentRow -1, currentCol -2);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(validMove);
                    }
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }
        }

        // 2 spaces right
        if (currentCol + 2 <= 8) {
            // 1 space up
            if (currentRow + 1 <= 8) {
                ChessPosition newPosition = new ChessPosition(currentRow +1, currentCol +2);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(validMove);
                    }
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }
            // 1 space down
            if (currentRow -1 > 0) {
                ChessPosition newPosition = new ChessPosition(currentRow -1, currentCol +2);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(validMove);
                    }
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }
        }

        // 2 spaces down
        if (currentRow - 2 > 0) {
            // 1 space left
            if (currentCol - 1 > 0) {
                ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol -1);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(validMove);
                    }
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }

            // 1 space right
            if (currentCol + 1 <= 8) {
                ChessPosition newPosition = new ChessPosition(currentRow - 2, currentCol +1);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                        validMoves.add(validMove);
                    }
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }
        }
        return validMoves;
    }
    public Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        // Create an empty collection
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendlyTeamColor = getTeamColor();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();

        int iteration = 1;

        // Check to see what moves the rook can make
        // North
        while(true) {
            if (currentRow + iteration > 8) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(currentRow + iteration, currentCol);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
                break;
            } catch (NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
                iteration++;
            }
        }

        iteration = 1;
        // South
        while(true) {
            if (currentRow - iteration <= 0) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(currentRow - iteration, currentCol);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
                break;
            } catch (NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
                iteration++;
            }
        }

        iteration = 1;
        // East
        while(true) {
            if (currentCol + iteration > 8) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol + iteration);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
                break;
            } catch (NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
                iteration++;
            }
        }

        iteration = 1;
        // West
        while(true) {
            if (currentCol - iteration <= 0) {
                break;
            }
            ChessPosition newPosition = new ChessPosition(currentRow, currentCol - iteration);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
                if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
                break;
            } catch (NullPointerException e) {
                ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                validMoves.add(validMove);
                iteration++;
            }
        }

        return validMoves;
    }
    public Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        // Create an empty collection
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendlyTeamColor = getTeamColor();
        int currentRow = myPosition.getRow();
        int currentCol = myPosition.getColumn();


        if (friendlyTeamColor == ChessGame.TeamColor.WHITE) {
            boolean isFirstMove = currentRow == 2 ? true : false;
            boolean blocked = true;

            // Check one space in front (Can only advance if there isn't a piece in front of it)
            ChessPosition newPosition = new ChessPosition(currentRow + 1, currentCol);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
            } catch (NullPointerException e) {
                if (currentRow + 1 == 8) {
                    ChessMove validMove1 = new ChessMove(myPosition, newPosition, PieceType.BISHOP);
                    ChessMove validMove2 = new ChessMove(myPosition, newPosition, PieceType.ROOK);
                    ChessMove validMove3 = new ChessMove(myPosition, newPosition, PieceType.KNIGHT);
                    ChessMove validMove4 = new ChessMove(myPosition, newPosition, PieceType.QUEEN);
                    validMoves.add(validMove1);
                    validMoves.add(validMove2);
                    validMoves.add(validMove3);
                    validMoves.add(validMove4);
                } else {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                    blocked = false;
                }
            }
            if (isFirstMove && !blocked) {
                newPosition = new ChessPosition(currentRow + 2, currentCol);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }

            // Check one space in front and to each side
            if (currentCol - 1 > 0) {
                ChessPosition takePosition = new ChessPosition(currentRow + 1, currentCol -1);
                try {
                    ChessPiece currentPiece = board.getPiece(takePosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        if (currentRow + 1 == 8) {
                            ChessMove validMove1 = new ChessMove(myPosition, takePosition, PieceType.BISHOP);
                            ChessMove validMove2 = new ChessMove(myPosition, takePosition, PieceType.ROOK);
                            ChessMove validMove3 = new ChessMove(myPosition, takePosition, PieceType.KNIGHT);
                            ChessMove validMove4 = new ChessMove(myPosition, takePosition, PieceType.QUEEN);
                            validMoves.add(validMove1);
                            validMoves.add(validMove2);
                            validMoves.add(validMove3);
                            validMoves.add(validMove4);
                        } else {
                            ChessMove validMove = new ChessMove(myPosition, takePosition, null);
                            validMoves.add(validMove);
                        }
                    }
                } catch (NullPointerException e) {
                    // Do nothing
                }
            }

            if (currentCol + 1 < 8) {
                ChessPosition takePosition = new ChessPosition(currentRow +1, currentCol +1);
                try {
                    ChessPiece currentPiece = board.getPiece(takePosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        if (currentRow + 1 == 8) {
                            ChessMove validMove1 = new ChessMove(myPosition, takePosition, PieceType.BISHOP);
                            ChessMove validMove2 = new ChessMove(myPosition, takePosition, PieceType.ROOK);
                            ChessMove validMove3 = new ChessMove(myPosition, takePosition, PieceType.KNIGHT);
                            ChessMove validMove4 = new ChessMove(myPosition, takePosition, PieceType.QUEEN);
                            validMoves.add(validMove1);
                            validMoves.add(validMove2);
                            validMoves.add(validMove3);
                            validMoves.add(validMove4);
                        } else {
                            ChessMove validMove = new ChessMove(myPosition, takePosition, null);
                            validMoves.add(validMove);
                        }
                    }
                } catch (NullPointerException e) {
                    // Do nothing
                }
            }


        } else {  // Black
            boolean isFirstMove = currentRow == 7 ? true : false;
            boolean blocked = true;

            // Check one space in front (Can only advance if there isn't a piece in front of it)
            ChessPosition newPosition = new ChessPosition(currentRow - 1, currentCol);
            try {
                ChessPiece currentPiece = board.getPiece(newPosition);
            } catch (NullPointerException e) {
                if (currentRow - 1 == 1) {
                    ChessMove validMove1 = new ChessMove(myPosition, newPosition, PieceType.BISHOP);
                    ChessMove validMove2 = new ChessMove(myPosition, newPosition, PieceType.ROOK);
                    ChessMove validMove3 = new ChessMove(myPosition, newPosition, PieceType.KNIGHT);
                    ChessMove validMove4 = new ChessMove(myPosition, newPosition, PieceType.QUEEN);
                    validMoves.add(validMove1);
                    validMoves.add(validMove2);
                    validMoves.add(validMove3);
                    validMoves.add(validMove4);
                } else {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                    blocked = false;
                }
            }
            if (isFirstMove && !blocked) {
                newPosition = new ChessPosition(currentRow - 2, currentCol);
                try {
                    ChessPiece currentPiece = board.getPiece(newPosition);
                } catch (NullPointerException e) {
                    ChessMove validMove = new ChessMove(myPosition, newPosition, null);
                    validMoves.add(validMove);
                }
            }

            // Check one space in front and to each side
            if (currentCol - 1 > 0) {
                ChessPosition takePosition = new ChessPosition(currentRow - 1, currentCol -1);
                try {
                    ChessPiece currentPiece = board.getPiece(takePosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        if (currentRow - 1 == 1) {
                            ChessMove validMove1 = new ChessMove(myPosition, takePosition, PieceType.BISHOP);
                            ChessMove validMove2 = new ChessMove(myPosition, takePosition, PieceType.ROOK);
                            ChessMove validMove3 = new ChessMove(myPosition, takePosition, PieceType.KNIGHT);
                            ChessMove validMove4 = new ChessMove(myPosition, takePosition, PieceType.QUEEN);
                            validMoves.add(validMove1);
                            validMoves.add(validMove2);
                            validMoves.add(validMove3);
                            validMoves.add(validMove4);
                        } else {
                            ChessMove validMove = new ChessMove(myPosition, takePosition, null);
                            validMoves.add(validMove);
                        }
                    }
                } catch (NullPointerException e) {
                    // Do nothing
                }
            }

            if (currentCol + 1 < 8) {
                ChessPosition takePosition = new ChessPosition(currentRow -1, currentCol +1);
                try {
                    ChessPiece currentPiece = board.getPiece(takePosition);
                    if (currentPiece.getTeamColor() != friendlyTeamColor && currentPiece.getPieceType() != PieceType.KING) {
                        if (currentRow - 1 == 1) {
                            ChessMove validMove1 = new ChessMove(myPosition, takePosition, PieceType.BISHOP);
                            ChessMove validMove2 = new ChessMove(myPosition, takePosition, PieceType.ROOK);
                            ChessMove validMove3 = new ChessMove(myPosition, takePosition, PieceType.KNIGHT);
                            ChessMove validMove4 = new ChessMove(myPosition, takePosition, PieceType.QUEEN);
                            validMoves.add(validMove1);
                            validMoves.add(validMove2);
                            validMoves.add(validMove3);
                            validMoves.add(validMove4);
                        } else {
                            ChessMove validMove = new ChessMove(myPosition, takePosition, null);
                            validMoves.add(validMove);
                        }
                    }
                } catch (NullPointerException e) {
                    // Do nothing
                }
            }
        }
        return validMoves;
    }

    public String toString() {
        if (getTeamColor() == null || getPieceType() == null) {
            return "-";
        }
        switch (getPieceType()) {
            case KING:
                return getTeamColor() == ChessGame.TeamColor.WHITE ? "K" : "k";
            case QUEEN:
                return getTeamColor() == ChessGame.TeamColor.WHITE ? "Q" : "q";
            case BISHOP:
                return getTeamColor() == ChessGame.TeamColor.WHITE ? "B" : "b";
            case KNIGHT:
                return getTeamColor() == ChessGame.TeamColor.WHITE ? "N" : "n";
            case ROOK:
                return getTeamColor() == ChessGame.TeamColor.WHITE ? "R" : "r";
            case PAWN:
                return getTeamColor() == ChessGame.TeamColor.WHITE ? "P" : "p";
        }
        return null;
    }
}
