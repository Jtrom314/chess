import chess.*;
import java.util.Scanner;

import static ui.EscapeSequences.*;

public class Main {
    public static void main(String[] args) {
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.println("♕ Welcome to 240 Chess. Type Help to get started. ♕");
        while (true) {
            System.out.print("[LOGGED OUT] >>> ");
            Scanner scanner = new Scanner(System.in);
            String line = scanner.nextLine();
            String[] parsed = line.split(" ");

            switch (parsed[0]) {
                case "help":
                    printHelp();
                    break;
                case "register":
                    register(parsed);
                    break;
                case "login":
                    login(parsed);
                    break;
                case "quit":
                    System.exit(0);
            }
        }
    }

    private static void register (String[] request) {

    }

    private static void login (String[] request) {

    }

    private static void postLoginUI () {

    }


    private static void printHelp() {
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

        System.out.print(SET_TEXT_COLOR_BLUE);
        System.out.print("help");
        System.out.print(SET_TEXT_COLOR_WHITE);
        System.out.print(" - to see possible commands\n");
    }
}