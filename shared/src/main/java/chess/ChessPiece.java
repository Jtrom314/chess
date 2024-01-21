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

    private ChessGame.TeamColor pieceColor;
    private ChessPiece.PieceType pieceType;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.pieceType = type;
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
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    public void setPieceType(PieceType type) {
        this.pieceType = type;
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
                return kingsMoves(board, myPosition);
            case QUEEN:
                Collection<ChessMove> queenMoves = bishopMoves(board, myPosition);
                queenMoves.addAll(rookMoves(board, myPosition));
                return queenMoves;
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

    public Collection<ChessMove> kingsMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendly_color = getTeamColor();
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();

        // Determine valid moves
        // N
        if (current_row + 1 <= 8) {
            ChessPosition endPosition = new ChessPosition(current_row + 1, current_col);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
        }

        // NE
        if (current_row + 1 <= 8 && current_col + 1 <= 8) {
            ChessPosition endPosition = new ChessPosition(current_row + 1, current_col + 1);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
        }

        // E
        if (current_col + 1 <= 8) {
            ChessPosition endPosition = new ChessPosition(current_row, current_col + 1);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
        }

        // SE
        if (current_row - 1 >= 1 && current_col + 1 <= 8) {
            ChessPosition endPosition = new ChessPosition(current_row - 1, current_col + 1);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
        }

        // S
        if (current_row - 1 >= 1) {
            ChessPosition endPosition = new ChessPosition(current_row - 1, current_col);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
        }

        // SW
        if (current_row - 1 >= 1 && current_col - 1 >= 1) {
            ChessPosition endPosition = new ChessPosition(current_row - 1, current_col - 1);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
        }

        // W
        if (current_col - 1 >= 1) {
            ChessPosition endPosition = new ChessPosition(current_row, current_col - 1);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
        }

        // NW
        if (current_row + 1 <= 8 && current_col - 1 >= 1) {
            ChessPosition endPosition = new ChessPosition(current_row + 1, current_col - 1);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
        }
        return validMoves;
    }
    public  Collection<ChessMove> bishopMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendly_color = getTeamColor();
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();

        int i = 1;
        // NE
        while (true) {
            if (current_row + i > 8 || current_col + i > 8) {
                break;
            }
            ChessPosition endPosition = new ChessPosition(current_row + i, current_col + i);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece != null) {
                break;
            }
            i++;
        }

        i = 1;
        // SE
        while (true) {
            if (current_row - i < 1 || current_col + i > 8) {
                break;
            }
            ChessPosition endPosition = new ChessPosition(current_row - i, current_col + i);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece != null) {
                break;
            }
            i++;
        }

        i = 1;
        // SW
        while (true) {
            if (current_row - i < 1 || current_col - i < 1) {
                break;
            }
            ChessPosition endPosition = new ChessPosition(current_row - i, current_col - i);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece != null) {
                break;
            }
            i++;
        }

        i = 1;
        // NW
        while (true) {
            if (current_row + i > 8 || current_col - i < 1) {
                break;
            }
            ChessPosition endPosition = new ChessPosition(current_row + i, current_col - i);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece != null) {
                break;
            }
            i++;
        }

        return validMoves;
    }
    public  Collection<ChessMove> knightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendly_color = getTeamColor();
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();

        // UP 2
        if (current_row + 2 <= 8) {
            ChessPosition endPosition;
            if (current_col + 1 <= 8) {
                endPosition = new ChessPosition(current_row + 2, current_col + 1);
                ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }
            if (current_col - 1 >= 1) {
                endPosition = new ChessPosition(current_row + 2, current_col - 1);
                ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }
        }

        // DOWN 2
        if (current_row - 2 >= 1) {
            ChessPosition endPosition;
            if (current_col + 1 <= 8) {
                endPosition = new ChessPosition(current_row - 2, current_col + 1);
                ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }
            if (current_col - 1 >= 1) {
                endPosition = new ChessPosition(current_row - 2, current_col - 1);
                ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }
        }

        // LEFT 2
        if (current_col - 2 >= 1) {
            ChessPosition endPosition;
            if (current_row + 1 <= 8) {
                endPosition = new ChessPosition(current_row + 1, current_col - 2);
                ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }
            if (current_row - 1 >= 1) {
                endPosition = new ChessPosition(current_row - 1, current_col - 2);
                ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }
        }

        // RIGHT 2
        if (current_col + 2 <= 8) {
            ChessPosition endPosition;
            if (current_row + 1 <= 8) {
                endPosition = new ChessPosition(current_row + 1, current_col + 2);
                ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }
            if (current_row - 1 >= 1) {
                endPosition = new ChessPosition(current_row - 1, current_col + 2);
                ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }
        }

        return validMoves;
    }
    public  Collection<ChessMove> rookMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendly_color = getTeamColor();
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();

        int i = 1;
        // N
        while(true) {
            if (current_row + i > 8) {
                break;
            }
            ChessPosition endPosition = new ChessPosition(current_row + i, current_col);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece != null) {
                break;
            }
            i++;
        }

        i = 1;
        // S
        while(true) {
            if (current_row - i < 1) {
                break;
            }
            ChessPosition endPosition = new ChessPosition(current_row - i, current_col);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece != null) {
                break;
            }
            i++;
        }

        i = 1;
        // E
        while(true) {
            if (current_col + i > 8) {
                break;
            }
            ChessPosition endPosition = new ChessPosition(current_row, current_col + i);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece != null) {
                break;
            }
            i++;
        }

        i = 1;
        // W
        while(true) {
            if (current_col - i < 1) {
                break;
            }
            ChessPosition endPosition = new ChessPosition(current_row, current_col - i);
            ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
            if (validMove != null) {
                validMoves.add(validMove);
            }
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece != null) {
                break;
            }
            i++;
        }

        return validMoves;
    }
    public  Collection<ChessMove> pawnMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> validMoves = new HashSet<>();
        ChessGame.TeamColor friendly_color = getTeamColor();
        int current_row = myPosition.getRow();
        int current_col = myPosition.getColumn();

        ChessPosition endPosition;

        if (friendly_color == ChessGame.TeamColor.WHITE) {
            // Move one forward
            if (current_row == 2) {
                endPosition = new ChessPosition(current_row + 1, current_col);
                ChessMove validMove = getValidPawnMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
                ChessPiece current_piece = board.getPiece(endPosition);
                if (current_piece == null) {
                    endPosition = new ChessPosition(current_row + 2, current_col);
                    validMove = getValidPawnMove(board, myPosition, endPosition, friendly_color);
                    if (validMove != null) {
                        validMoves.add(validMove);
                    }
                }
            } else if (current_row + 1 == 8) {
                endPosition = new ChessPosition(current_row + 1, current_col);
                validMoves.addAll(promotionMoves(board, myPosition, endPosition, friendly_color, false));
            } else {
                endPosition = new ChessPosition(current_row + 1, current_col);
                ChessMove validMove = getValidPawnMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }

            // White Capture
            if (current_col + 1 <= 8) {
                if (current_row + 1 == 8) {
                    endPosition = new ChessPosition(current_row + 1, current_col + 1);
                    ChessPiece current_piece = board.getPiece(endPosition);
                    if (current_piece != null) {
                        validMoves.addAll(promotionMoves(board, myPosition, endPosition, friendly_color, true));
                    }
                } else {
                    endPosition = new ChessPosition(current_row + 1, current_col + 1);
                    ChessPiece current_piece = board.getPiece(endPosition);
                    if (current_piece != null) {
                        ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                        if (validMove != null) {
                            validMoves.add(validMove);
                        }
                    }
                }
            }

            if (current_col - 1 >= 1) {
                if (current_row + 1 == 8) {
                    endPosition = new ChessPosition(current_row + 1, current_col - 1);
                    ChessPiece current_piece = board.getPiece(endPosition);
                    if (current_piece != null) {
                        validMoves.addAll(promotionMoves(board, myPosition, endPosition, friendly_color, true));
                    }
                } else {
                    endPosition = new ChessPosition(current_row + 1, current_col - 1);
                    ChessPiece current_piece = board.getPiece(endPosition);
                    if (current_piece != null) {
                        ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                        if (validMove != null) {
                            validMoves.add(validMove);
                        }
                    }
                }
            }
        } else {
            // Move one forward
            if (current_row == 7) {
                endPosition = new ChessPosition(current_row - 1, current_col);
                ChessMove validMove = getValidPawnMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
                ChessPiece current_piece = board.getPiece(endPosition);
                if (current_piece == null) {
                    endPosition = new ChessPosition(current_row - 2, current_col);
                    validMove = getValidPawnMove(board, myPosition, endPosition, friendly_color);
                    if (validMove != null) {
                        validMoves.add(validMove);
                    }
                }
            } else if (current_row - 1 == 1) {
                endPosition = new ChessPosition(current_row - 1, current_col);
                validMoves.addAll(promotionMoves(board, myPosition, endPosition, friendly_color, false));
            } else {
                endPosition = new ChessPosition(current_row - 1, current_col);
                ChessMove validMove = getValidPawnMove(board, myPosition, endPosition, friendly_color);
                if (validMove != null) {
                    validMoves.add(validMove);
                }
            }

            // Black Capture
            if (current_col + 1 <= 8) {
                if (current_row - 1 == 1) {
                    endPosition = new ChessPosition(current_row - 1, current_col + 1);
                    ChessPiece current_piece = board.getPiece(endPosition);
                    if (current_piece != null) {
                        validMoves.addAll(promotionMoves(board, myPosition, endPosition, friendly_color, true));
                    }
                } else {
                    endPosition = new ChessPosition(current_row - 1, current_col + 1);
                    ChessPiece current_piece = board.getPiece(endPosition);
                    if (current_piece != null) {
                        ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                        if (validMove != null) {
                            validMoves.add(validMove);
                        }
                    }
                }
            }

            if (current_col - 1 >= 1) {
                if (current_row - 1 == 1) {
                    endPosition = new ChessPosition(current_row - 1, current_col - 1);
                    ChessPiece current_piece = board.getPiece(endPosition);
                    if (current_piece != null) {
                        validMoves.addAll(promotionMoves(board, myPosition, endPosition, friendly_color, true));
                    }
                } else {
                    endPosition = new ChessPosition(current_row - 1, current_col - 1);
                    ChessPiece current_piece = board.getPiece(endPosition);
                    if (current_piece != null) {
                        ChessMove validMove = getValidMove(board, myPosition, endPosition, friendly_color);
                        if (validMove != null) {
                            validMoves.add(validMove);
                        }
                    }
                }
            }
        }

        return validMoves;
    }

    private ChessMove getValidMove(ChessBoard board, ChessPosition startPosition, ChessPosition endPosition, ChessGame.TeamColor friendly_color) {
        try {
            // Get piece at end position
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece.getTeamColor() != friendly_color) {
                return new ChessMove(startPosition, endPosition, null);
            }
            return null;
        } catch (NullPointerException e) {
            // Empty space
            return new ChessMove(startPosition, endPosition, null);
        }
    }

    private ChessMove getValidPawnMove(ChessBoard board, ChessPosition startPosition, ChessPosition endPosition, ChessGame.TeamColor friendly_color) {
        ChessPiece current_piece = board.getPiece(endPosition);
        if (current_piece == null) {
            return new ChessMove(startPosition, endPosition, null);
        }
        return null;
    }
    private Collection<ChessMove> promotionMoves(ChessBoard board, ChessPosition startPosition, ChessPosition endPosition, ChessGame.TeamColor friendly_color, Boolean capture) {
        Collection<ChessMove> validMoves = new HashSet<>();
        if (capture) {
            try {
                ChessPiece current_piece = board.getPiece(endPosition);
                if (current_piece.getTeamColor() != friendly_color) {
                    ChessMove move_1 = new ChessMove(startPosition, endPosition, PieceType.QUEEN);
                    ChessMove move_2 = new ChessMove(startPosition, endPosition, PieceType.BISHOP);
                    ChessMove move_3 = new ChessMove(startPosition, endPosition, PieceType.KNIGHT);
                    ChessMove move_4 = new ChessMove(startPosition, endPosition, PieceType.ROOK);
                    validMoves.add(move_1);
                    validMoves.add(move_2);
                    validMoves.add(move_3);
                    validMoves.add(move_4);
                }
            } catch (NullPointerException e) {
                ChessMove move_1 = new ChessMove(startPosition, endPosition, PieceType.QUEEN);
                ChessMove move_2 = new ChessMove(startPosition, endPosition, PieceType.BISHOP);
                ChessMove move_3 = new ChessMove(startPosition, endPosition, PieceType.KNIGHT);
                ChessMove move_4 = new ChessMove(startPosition, endPosition, PieceType.ROOK);
                validMoves.add(move_1);
                validMoves.add(move_2);
                validMoves.add(move_3);
                validMoves.add(move_4);
            }
        } else {
            ChessPiece current_piece = board.getPiece(endPosition);
            if (current_piece == null) {
                ChessMove move_1 = new ChessMove(startPosition, endPosition, PieceType.QUEEN);
                ChessMove move_2 = new ChessMove(startPosition, endPosition, PieceType.BISHOP);
                ChessMove move_3 = new ChessMove(startPosition, endPosition, PieceType.KNIGHT);
                ChessMove move_4 = new ChessMove(startPosition, endPosition, PieceType.ROOK);
                validMoves.add(move_1);
                validMoves.add(move_2);
                validMoves.add(move_3);
                validMoves.add(move_4);
            }
        }
        return validMoves;
    }

    public String pieceTypeToString() {
        switch (getPieceType()) {
            case KING:
                return "K";
            case QUEEN:
                return "Q";
            case KNIGHT:
                return "N";
            case BISHOP:
                return "B";
            case ROOK:
                return "R";
            case PAWN:
                return "P";
            default:
                return null;
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, pieceType);
    }
}
