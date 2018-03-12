package com.codenames.attilahanko.event;

public class StartGame {

    private boolean isGameActive;

    public StartGame(boolean isGameActive) {
        this.isGameActive = isGameActive;
    }

    public boolean isGameActive() {
        return isGameActive;
    }
}
