package ui;

import chess.*;

import java.util.Collection;

import static ui.EscapeSequences.*;

public class BoardUI {
    public enum STATE {
        LOGGED_OUT,
        LOGGED_IN,
        GAMEPLAY
    }
    public void printWhitePerspective (ChessGame game, Boolean highlight, ChessPosition startingPosition, Collection<ChessMove> validMoves) {
        ChessBoard board = game.getBoard();
        int finalSize = 10;

        String[] col = {"", "A", "B", "C", "D", "E", "F", "G", "H", ""};
        String[] row = {"", "8", "7", "6", "5", "4", "3", "2", "1", ""};


        for (int i = 0; i < finalSize; i ++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_BLACK);
            for (int j = 0; j < finalSize; j++) {
                if (i == 0 || i == finalSize - 1) {
                    if (j == 0 || j == finalSize -1) {
                        System.out.print("   ");
                    } else {
                        System.out.printf(" %s ", col[j]);
                    }
                } else if (i % 2 == 0) {
                    if (j > 0 && j < finalSize - 1) {
                        ChessPosition currentPosition = new ChessPosition(9 - i, j);
                        ChessPiece currentPiece = board.getPiece(currentPosition);
                        if (j % 2 == 0) {
                            System.out.print(SET_BG_COLOR_WHITE);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        } else {
                            System.out.print(SET_BG_COLOR_BLACK);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        }
                    } else {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(SET_TEXT_COLOR_BLACK);
                        System.out.printf(" %s ", row[i]);
                    }
                } else {
                    if (j > 0 && j < finalSize - 1) {
                        ChessPosition currentPosition = new ChessPosition(9 - i, j);
                        ChessPiece currentPiece = board.getPiece(currentPosition);
                        if (j % 2 == 0) {
                            System.out.print(SET_BG_COLOR_BLACK);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                System.out.print("   ");
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        } else {
                            System.out.print(SET_BG_COLOR_WHITE);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        }
                    } else {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(SET_TEXT_COLOR_BLACK);
                        System.out.printf(" %s ", row[i]);
                    }
                }
            }
            System.out.print("\n");
        }
        System.out.print(RESET_BG_COLOR);
        System.out.print(SET_TEXT_COLOR_WHITE);
    }

    public void printBlackPerspective (ChessGame game, Boolean highlight, ChessPosition startingPosition, Collection<ChessMove> validMoves) {
        ChessBoard board = game.getBoard();
        int finalSize = 10;

        String[] col = {"", "H", "G", "F", "E", "D", "C", "B", "A", ""};
        String[] row = {"", "1", "2", "3", "4", "5", "6", "7", "8", ""};


        for (int i = 0; i < finalSize; i ++) {
            System.out.print(SET_BG_COLOR_LIGHT_GREY);
            System.out.print(SET_TEXT_BOLD);
            System.out.print(SET_TEXT_COLOR_BLACK);
            for (int j = 0; j < finalSize; j++) {
                if (i == 0 || i == finalSize - 1) {
                    if (j == 0 || j == finalSize -1) {
                        System.out.print("   ");
                    } else {
                        System.out.printf(" %s ", col[j]);
                    }
                } else if (i % 2 == 0) {
                    if (j > 0 && j < finalSize - 1) {
                        ChessPosition currentPosition = new ChessPosition(i, 9 -j);
                        ChessPiece currentPiece = board.getPiece(currentPosition);
                        if (j % 2 == 0) {
                            System.out.print(SET_BG_COLOR_WHITE);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        } else {
                            System.out.print(SET_BG_COLOR_BLACK);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        }
                    } else {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(SET_TEXT_COLOR_BLACK);
                        System.out.printf(" %s ", row[i]);
                    }
                } else {
                    if (j > 0 && j < finalSize - 1) {
                        ChessPosition currentPosition = new ChessPosition(i, 9 - j);
                        ChessPiece currentPiece = board.getPiece(currentPosition);
                        if (j % 2 == 0) {
                            System.out.print(SET_BG_COLOR_BLACK);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        } else {
                            System.out.print(SET_BG_COLOR_WHITE);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                if (highlight) {
                                    for (ChessMove move : validMoves) {
                                        if (move.getEndPosition().getRow() == currentPosition.getRow() && move.getEndPosition().getColumn() == currentPosition.getColumn()) {
                                            System.out.print(SET_BG_COLOR_GREEN);
                                            break;
                                        }
                                    }
                                    if (startingPosition.getRow() == currentPosition.getRow() && startingPosition.getColumn() == currentPosition.getColumn()) {
                                        System.out.print(SET_BG_COLOR_YELLOW);
                                    }
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        }
                    } else {
                        System.out.print(SET_BG_COLOR_LIGHT_GREY);
                        System.out.print(SET_TEXT_COLOR_BLACK);
                        System.out.printf(" %s ", row[i]);
                    }
                }
            }
            System.out.print("\n");
        }
        System.out.print(RESET_BG_COLOR);
        System.out.print(SET_TEXT_COLOR_WHITE);
    }
    public void printHelp(STATE current_state) {
        switch (current_state) {
            case LOGGED_OUT:
                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("create <NAME>");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to create a game\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("list");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to list all games\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("join <ID> [WHITE|BLACK]");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to join a game as a player\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("observe <ID>");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to join a game as an observer\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("logout");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to quit playing\n");
                break;
            case LOGGED_IN:
                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("register <USERNAME> <PASSWORD> <EMAIL>");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to create an account\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("login <USERNAME> <PASSWORD>");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to play chess\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("quit");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to exit program\n");
                break;
            case GAMEPLAY:
                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("redraw");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - to redraw the current board\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("leave");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - leave the game\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("make move <move> [promotion]");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - make a chess move\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("resign");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - resign the game\n");

                System.out.print(SET_TEXT_COLOR_BLUE);
                System.out.print("highlight <position>");
                System.out.print(SET_TEXT_COLOR_WHITE);
                System.out.print(" - highlight all legal moves for selected piece\n");
        }
        System.out.print(SET_TEXT_COLOR_BLUE);
        System.out.print("help");
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(" - to see possible commands\n");
    }
}
