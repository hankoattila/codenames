package com.codenames.attilahanko.model;

import java.util.List;

public class PlayerDTO {

    private boolean isYourTurn;

    private List<List<String>> cards;

    public PlayerDTO() {
    }


    public List<List<String>> getCards() {
        return cards;
    }

    public void setCards(List<List<String>> cards) {
        this.cards = cards;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }
}
