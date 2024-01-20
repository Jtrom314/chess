import chess.*;
import result.CreateGameResult;
import result.LoginResult;

import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {
    public String username;
    public String authToken;

    public Main () {}

    public static void main(String[] args) throws Exception {
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
        LoginResult response =  serverFacade.register(request[1], request[2], request[3]);

        if (response == null) {
            System.out.println("User already taken");
            return false;
        }

        setUsername(response.username());
        setAuthToken(response.authToken());

        return true;
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

    public String getUsername() {
        return this.username;
    }

    public String getAuthToken() {
        return this.authToken;
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