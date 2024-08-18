package me.wuzzyxy.playerlocator.configs;

public enum ConfigPaths {
    INTERVAL("interval"),
    WORLDS("worlds"),
    MINIMUM_DISTANCE("minimum-distance"),
    NO_PLAYERS_MESSAGE("no-players-message"),
    PLAYER_NEARBY_MESSAGE("player-nearby-message");

    private final String path;

    ConfigPaths(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}