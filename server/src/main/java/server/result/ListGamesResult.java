package server.result;

import model.GameData;

public record ListGamesResult(GameData[] games) {
}
