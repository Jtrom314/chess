import com.google.gson.Gson;
import result.LoginResult;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.Map;

public class ServerFacade {

    public ServerFacade () {}
    public LoginResult register (String username, String password, String email) throws Exception{
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
        }

        System.out.println("ERROR");
        return null;
    }

    public void clear () throws Exception {
        URI uri = new URI("http://localhost:8080/db");
        HttpURLConnection http = (HttpURLConnection) uri.toURL().openConnection();
        http.setRequestMethod("DELETE");

        http.connect();

        if (http.getResponseCode() == HttpURLConnection.HTTP_OK) {
            System.out.println("DB CLEARED");
        } else {
            System.out.println("ERROR");
        }
    }
}
