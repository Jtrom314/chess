package facade;

import com.google.gson.Gson;
import result.CreateGameResult;
import result.ListGameResult;
import result.LoginResult;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class ServerFacade {

    public ServerFacade () {}
    public LoginResult register (String username, String password, String email) throws Exception {
        URI uri = new URI("http://localhost:8080/user");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");

        http.setDoOutput(true);
        http.addRequestProperty("Content-Type", "application/json");

        http.connect();

        var body = Map.of("username", username, "password", password, "email", email);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStream response = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(response);
                return new Gson().fromJson(inputStreamReader, LoginResult.class);
            }
        } else {
            throw new Exception(http.getResponseMessage());
        }
    }

    public LoginResult login (String username, String password) throws Exception {
        URI uri = new URI("http://localhost:8080/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");

        http.setDoOutput(true);
        http.addRequestProperty("Content-Type", "application/json");

        http.connect();

        var body = Map.of("username", username, "password", password);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStream response = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(response);
                return new Gson().fromJson(inputStreamReader, LoginResult.class);
            }
        } else {
            throw new Exception(http.getResponseMessage());
        }
    }

    public void logout (String authToken) throws Exception {
        URI uri = new URI("http://localhost:8080/session");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");

        http.addRequestProperty("authorization", authToken);

        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return;
        } else {
            throw new Exception(http.getResponseMessage());
        }
    }

    public CreateGameResult createGame(String authToken, String gameName) throws Exception {
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("POST");

        http.setDoOutput(true);
        http.addRequestProperty("authorization", authToken);
        http.addRequestProperty("Content-Type", "application/json");

        http.connect();

        var body = Map.of("gameName", gameName);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStream response = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(response);
                return new Gson().fromJson(inputStreamReader, CreateGameResult.class);
            }
        } else {
            throw new Exception(http.getResponseMessage());
        }
    }

    public ListGameResult listGames(String authToken) throws Exception {
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("GET");

        http.addRequestProperty("authorization", authToken);

        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            try (InputStream response = http.getInputStream()) {
                InputStreamReader inputStreamReader = new InputStreamReader(response);
                return new Gson().fromJson(inputStreamReader, ListGameResult.class);
            }
        } else {
            throw new Exception(http.getResponseMessage());
        }
    }

    public void joinGame(String authToken, int gameID, String playerColor) throws Exception {
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("PUT");

        http.setDoOutput(true);
        http.addRequestProperty("authorization", authToken);

        http.connect();

        var body = Map.of("playerColor", playerColor, "gameID", gameID);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }
        if (http.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new Exception(http.getResponseMessage());
        }
    }

    public void observeGame(String authToken, int gameID) throws Exception {
        URI uri = new URI("http://localhost:8080/game");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("PUT");

        http.setDoOutput(true);
        http.addRequestProperty("authorization", authToken);

        http.connect();

        var body = Map.of("gameID", gameID);
        try (var outputStream = http.getOutputStream()) {
            var jsonBody = new Gson().toJson(body);
            outputStream.write(jsonBody.getBytes());
        }
        if (http.getResponseCode() != HttpURLConnection.HTTP_OK) {
            throw new Exception(http.getResponseMessage());
        }
    }

    public void clear () throws Exception {
        URI uri = new URI("http://localhost:8080/db");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");

        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            return;
        } else {
            throw new Exception(http.getResponseMessage());
        }
    }
}
