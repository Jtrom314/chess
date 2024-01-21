import chess.*;
import facade.ServerFacade;
import model.GameData;
import result.CreateGameResult;
import result.ListGameResult;
import result.LoginResult;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {
    public String username;
    public String authToken;
    public int currentGameID;

    public Main () {}

    public static void main(String[] args) throws Exception {
        System.out.print(RESET_BG_COLOR);
        System.out.print(SET_TEXT_COLOR_WHITE);
        new Main().preLoginUI();
    }

    private static void clear () throws Exception {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.clear();
    }
    public boolean register (String[] request) throws Exception {
        if (request.length != 4) {
            System.out.println("Incorrect number of arguments for register");
            return false;
        }

        ServerFacade serverFacade = new ServerFacade();
        try {
            LoginResult response =  serverFacade.register(request[1], request[2], request[3]);
            setUsername(response.username());
            setAuthToken(response.authToken());
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public boolean login (String[] request) throws Exception {
        if (request.length != 3) {
            System.out.println("Incorrect number of arguments for login");
            return false;
        }

        ServerFacade serverFacade = new ServerFacade();
        LoginResult response = serverFacade.login(request[1], request[2]);

        if (response == null) {
            System.out.println("Unauthorized");
            return false;
        }

        setUsername(response.username());
        setAuthToken(response.authToken());

        return true;
    }

    public boolean logout () throws Exception {
        ServerFacade serverFacade = new ServerFacade();
        if (serverFacade.logout(getAuthToken())) {
            setAuthToken(null);
            setUsername(null);
            return true;
        }
        return false;
    }

    public void createGame (String[] request) throws Exception {
        if (request.length != 2) {
            System.out.println("Incorrect number of arguments for create");
            return;
        }

        ServerFacade serverFacade = new ServerFacade();
        CreateGameResult response = serverFacade.createGame(getAuthToken(), request[1]);

        if (response == null) {
            System.out.println("Cannot create game");
            return;
        }

        System.out.println(String.format("Created game id: %d", response.gameID()));
    }

    public void listGames () throws Exception {
        ServerFacade serverFacade = new ServerFacade();
        ListGameResult result = serverFacade.listGames(getAuthToken());

        if (result == null) {
            return;
        }

        GameData[] allGames = result.games();
        for (int i = 0; i < allGames.length; i++) {
            String whiteUsername = allGames[i].whiteUsername() == null ? "Empty" : allGames[i].whiteUsername();
            String blackUsername = allGames[i].blackUsername() == null ? "Empty" : allGames[i].blackUsername();
            System.out.println(String.format("%d.\t Game: %s\t ID: %d\t White Player: %s\t Black Player: %s", (i + 1), allGames[i].gameName(), allGames[i].gameID(), whiteUsername, blackUsername));
        }
    }

    public void joinGame (String[] request) throws Exception {
        if (request.length != 3) {
            System.out.println("Incorrect number of arguments for join");
            return;
        }

        ServerFacade serverFacade = new ServerFacade();
        boolean response = serverFacade.joinGame(getAuthToken(), Integer.parseInt(request[1]), request[2]);

        if (response) {
            setCurrentGameID(Integer.parseInt(request[1]));
            ChessGame game = new ChessGame();
            ChessBoard board = new ChessBoard();
            board.resetBoard();
            game.setBoard(board);

            printBlackPerspective(game);
            System.out.print("\n");
            printWhitePerspective(game);
        }
    }

    public void observeGame (String[] request) throws Exception {
        if (request.length != 2) {
            System.out.println("Incorrect number of arguments for observe");
            return;
        }

        ServerFacade serverFacade = new ServerFacade();
        boolean response = serverFacade.observeGame(getAuthToken(), Integer.parseInt(request[1]));

        if (response) {
            setCurrentGameID(Integer.parseInt(request[1]));
            ChessGame game = new ChessGame();
            ChessBoard board = new ChessBoard();
            board.resetBoard();
            game.setBoard(board);

            printBlackPerspective(game);
            System.out.print("\n");
            printWhitePerspective(game);
        }
    }

    public void postLoginUI () throws Exception {
        System.out.print(String.format("Logged in as: %s\n", getUsername()));
        while (true) {
            System.out.print("[LOGGED IN] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] parsed = line.split(" ");

            switch (parsed[0]) {
                case "help":
                    printHelp(true);
                    break;
                case "logout":
                    if (logout()) {
                        return;
                    }
                    break;
                case "create":
                    createGame(parsed);
                    break;
                case "list":
                    listGames();
                    break;
                case "join":
                    joinGame(parsed);
                    break;
                case "observe":
                    observeGame(parsed);
                    break;
            }

        }


    }

    public void preLoginUI () throws Exception{
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.println("♕ Welcome to 240 Chess. Type Help to get started. ♕");
        while (true) {
            System.out.print("[LOGGED OUT] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] parsed = line.split(" ");

            switch (parsed[0]) {
                case "help":
                    printHelp(false);
                    break;
                case "register":
                    if (register(parsed)) {
                        postLoginUI();
                    }
                    break;
                case "login":
                    if(login(parsed)) {
                        postLoginUI();
                    }
                    break;
                case "clear":
                    clear();
                    break;
                case "quit":
                    System.exit(0);
            }
        }
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setCurrentGameID(int gameID) {
        this.currentGameID = gameID;
    }

    public String getUsername() {
        return this.username;
    }

    public String getAuthToken() {
        return this.authToken;
    }

    public int getCurrentGameID() {
        return this.currentGameID;
    }


    public static void printWhitePerspective (ChessGame game) {
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
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        } else {
                            System.out.print(SET_BG_COLOR_BLACK);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
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
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        } else {
                            System.out.print(SET_BG_COLOR_WHITE);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
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

    public static void printBlackPerspective (ChessGame game) {
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
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        } else {
                            System.out.print(SET_BG_COLOR_BLACK);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
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
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
                                }
                                System.out.printf(" %s ", currentPiece.pieceTypeToString());
                            }
                        } else {
                            System.out.print(SET_BG_COLOR_WHITE);
                            System.out.print(SET_TEXT_COLOR_BLACK);
                            if (currentPiece == null) {
                                System.out.print("   ");
                            } else {
                                if (currentPiece.getTeamColor() == ChessGame.TeamColor.WHITE) {
                                    System.out.print(SET_TEXT_COLOR_RED);
                                } else {
                                    System.out.print(SET_TEXT_COLOR_BLUE);
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



    private static void printHelp(boolean isLoggedIn) {
        if (isLoggedIn) {
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
        } else {
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
        }
        System.out.print(SET_TEXT_COLOR_BLUE);
        System.out.print("help");
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(" - to see possible commands\n");
    }
}