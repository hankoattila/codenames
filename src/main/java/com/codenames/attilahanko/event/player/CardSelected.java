package com.codenames.attilahanko.event.player;

import java.util.List;

public class CardSelected {

    private List<Integer> selected;
    private String color;
    private boolean isYourTurn;
    private String currentTeam;

    public CardSelected(List<Integer> selected, String color, boolean isYourTurn, String currentTeam) {
        this.selected = selected;
        this.color = color;
        this.isYourTurn = isYourTurn;
        this.currentTeam = currentTeam;
    }

    public List<Integer> getSelected() {
        return selected;
    }

    public String getColor() {
        return color;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public String getCurrentTeam() {
        return currentTeam;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }
}