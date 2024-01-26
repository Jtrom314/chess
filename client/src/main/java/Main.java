import chess.*;
import facade.ServerFacade;
import facade.WebsocketFacade;
import model.GameData;
import result.CreateGameResult;
import result.ListGameResult;
import result.LoginResult;
import ui.BoardUI;
import ui.BoardUI.STATE;

import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {
    public String username;
    public String authToken;
    public int currentGameID;
    ChessGame.TeamColor currentTeamColor;
    GameData[] gameList;
    ChessGame currentGame;
    WebsocketFacade ws;
    BoardUI boardUI = new BoardUI();


    public Main () throws Exception{
        this.ws = new WebsocketFacade();
    }


    public static void main(String[] args) throws Exception {
        System.out.print(RESET_BG_COLOR);
        System.out.print(SET_TEXT_COLOR_WHITE);
        new Main().preLoginUI();
    }

    private static void clear () throws Exception {
        ServerFacade serverFacade = new ServerFacade();
        serverFacade.clear();
    }
    public boolean register (String[] request) {
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

    public boolean login (String[] request) {
        if (request.length != 3) {
            System.out.println("Incorrect number of arguments for login");
            return false;
        }

        ServerFacade serverFacade = new ServerFacade();
        try {
            LoginResult response = serverFacade.login(request[1], request[2]);
            setUsername(response.username());
            setAuthToken(response.authToken());
            return true;
        } catch (Exception exception) {
            System.out.print(exception.getMessage());
            return false;
        }
    }

    public boolean logout () {
        ServerFacade serverFacade = new ServerFacade();
        try {
            serverFacade.logout(getAuthToken());
            setAuthToken(null);
            setUsername(null);
            return true;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return false;
        }
    }

    public void createGame (String[] request) {
        if (request.length != 2) {
            System.out.println("Incorrect number of arguments for create");
            return;
        }

        ServerFacade serverFacade = new ServerFacade();
        try {
            CreateGameResult response = serverFacade.createGame(getAuthToken(), request[1]);
            System.out.println(String.format("Created game id: %d", response.gameID()));
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void listGames () {
        ServerFacade serverFacade = new ServerFacade();
        try {
            ListGameResult result = serverFacade.listGames(getAuthToken());

            GameData[] allGames = result.games();
            for (int i = 0; i < allGames.length; i++) {
                String whiteUsername = allGames[i].whiteUsername() == null ? "Empty" : allGames[i].whiteUsername();
                String blackUsername = allGames[i].blackUsername() == null ? "Empty" : allGames[i].blackUsername();
                System.out.println(String.format("%d.\t Game: %s\t ID: %d\t White Player: %s\t Black Player: %s", (i + 1), allGames[i].gameName(), allGames[i].gameID(), whiteUsername, blackUsername));
            }
            gameList = allGames;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void joinGame (String[] request) {
        if (request.length != 3) {
            System.out.println("Incorrect number of arguments for join");
            return;
        }

        int gameID;
        try {
            int gameIndex = Integer.parseInt(request[1]);
            GameData game = gameList[gameIndex -1];
            gameID = game.gameID();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        ServerFacade serverFacade = new ServerFacade();
        try {
            serverFacade.joinGame(getAuthToken(), gameID, request[2]);
            setCurrentGameID(gameID);
            ChessGame game = new ChessGame();
            ChessBoard board = new ChessBoard();
            board.resetBoard();
            game.setBoard(board);

            boardUI.printBlackPerspective(game, false, null, null);
            System.out.print("\n");
            boardUI.printWhitePerspective(game, false, null, null);
            setCurrentTeamColor(request[2]);

            setCurrentGame(game);

            gameplayUI();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void observeGame (String[] request) {
        if (request.length != 2) {
            System.out.println("Incorrect number of arguments for observe");
            return;
        }

        ServerFacade serverFacade = new ServerFacade();
        try {
            serverFacade.observeGame(getAuthToken(), Integer.parseInt(request[1]));
            setCurrentGameID(Integer.parseInt(request[1]));
            ChessGame game = new ChessGame();
            ChessBoard board = new ChessBoard();
            board.resetBoard();
            game.setBoard(board);

            boardUI.printBlackPerspective(game, false, null, null);
            System.out.print("\n");
            boardUI.printWhitePerspective(game, false, null, null);
            setCurrentTeamColor(null);

            setCurrentGame(game);

            gameplayUI();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
    }

    public void redrawBoard () {
        if (currentTeamColor == ChessGame.TeamColor.BLACK) {
            boardUI.printBlackPerspective(getCurrentGame(), false, null, null);
        } else {
            boardUI.printWhitePerspective(getCurrentGame(), false, null, null);
        }
    }

    public void makeMove(String[] parsed) {
        String userMove = parsed[2];
        userMove = userMove.toLowerCase();

        if (userMove.length() < 4) {
            System.out.println("Incorrect number of characters in move request");
            return;
        }

        String startingPostitionString = userMove.substring(0, 2);
        String endingPostitionString = userMove.substring(2, 4);

        System.out.printf("SPS: %s\n", startingPostitionString);
        System.out.printf("EPS: %s\n", endingPostitionString);

        ChessPosition startingPosition;
        ChessPosition endingPosition;

        ChessPiece.PieceType promotion = null;
        if (parsed.length == 4) {
            switch (parsed[3]) {
                case "QUEEN":
                    promotion = ChessPiece.PieceType.QUEEN;
                    break;
                case "BISHOP":
                    promotion = ChessPiece.PieceType.BISHOP;
                    break;
                case "ROOK":
                    promotion = ChessPiece.PieceType.ROOK;
                    break;
                case "KNIGHT":
                    promotion = ChessPiece.PieceType.KNIGHT;
                    break;
                default:
                    promotion = null;
                    break;
            }
        }

        try {
            startingPosition = getPositionByString(startingPostitionString);
            endingPosition = getPositionByString(endingPostitionString);
        } catch (Exception exception) {
            System.out.println("Could not convert position to move");
            return;
        }

        ChessMove move = new ChessMove(startingPosition, endingPosition, promotion);
    }

    public ChessPosition getPositionByString(String stringPosition) throws Exception {
        String letter = stringPosition.substring(0,1);
        String number = stringPosition.substring(1,2);

        int row = Integer.parseInt(number);
        int col = 0;

        switch (letter) {
            case "a":
                col = 1;
                break;
            case "b":
                col = 2;
                break;
            case "c":
                col = 3;
                break;
            case "d":
                col = 4;
                break;
            case "e":
                col = 5;
                break;
            case "f":
                col = 6;
                break;
            case "g":
                col = 7;
                break;
            case "h":
                col = 8;
                break;
            default:
                throw new Exception();
        }

        return new ChessPosition(row, col);
    }

    public void highlight(String[] parsed) {
        if (parsed.length != 2) {
            System.out.println("Incorrect number of arguments for highlight");
            return;
        }

        String userPosition = parsed[1];
        userPosition = userPosition.toLowerCase();
        if (userPosition.length() != 2) {
            System.out.println("Requested position is not a valid position");
            return;
        }

        ChessPosition startingPosition;
        try {
            startingPosition = getPositionByString(userPosition);
        } catch (Exception exception) {
            System.out.println("Requested position is not a valid position");
            return;
        }

        ChessGame currentGame = getCurrentGame();
        Collection<ChessMove> validMoves = currentGame.validMoves(startingPosition);

        if (validMoves == null) {
            validMoves = new HashSet<>();

        }

        if (getCurrentTeamColor() == ChessGame.TeamColor.BLACK) {
            boardUI.printBlackPerspective(getCurrentGame(), true, startingPosition, validMoves);
        } else {
            boardUI.printWhitePerspective(getCurrentGame(), true, startingPosition, validMoves);
        }
    }

    public void resign() {
        while (true) {
            System.out.println("[DO YOU REALLY WANT TO RESIGN? (Y/N)] >>> ");
            Scanner scanner = new Scanner(System.in);
            String word = scanner.next();
            switch (word) {
                case "Y":
                case "y":
                    return;
                case "N":
                case "n":
                    return;
                default:
            }
        }
    }

    public void leave() {
        return;
    }

    public void gameplayUI () throws Exception {
        while (true) {
            System.out.print("[GAMEPLAY] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] parsed = line.split( " ");

            switch (parsed[0]) {
                case "help":
                    boardUI.printHelp(STATE.GAMEPLAY);
                    break;
                case "redraw":
                    redrawBoard();
                    break;
                case "leave":
                    leave();
                    return;
                case "make":
                    makeMove(parsed);
                    break;
                case "resign":
                    resign();
                    break;
                case "highlight":
                    highlight(parsed);
                    break;
            }
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
                    boardUI.printHelp(STATE.LOGGED_IN);
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

    public void preLoginUI () throws Exception {
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.println("♕ Welcome to 240 Chess. Type Help to get started. ♕");
        while (true) {
            System.out.print("[LOGGED OUT] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] parsed = line.split(" ");

            switch (parsed[0]) {
                case "help":
                    boardUI.printHelp(STATE.LOGGED_OUT);
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

    public void setCurrentGame(ChessGame game) {
        this.currentGame = game;
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

    public ChessGame.TeamColor getCurrentTeamColor() {
        return this.currentTeamColor;
    }

    public ChessGame getCurrentGame () {
        return this.currentGame;
    }
}