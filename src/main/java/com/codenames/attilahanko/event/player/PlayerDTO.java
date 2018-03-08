package com.codenames.attilahanko.event.player;

import java.util.List;

public class PlayerDTO {

    private boolean isYourTurn;

    private List<CardDTO> cards;

    public PlayerDTO() {
    }

    public PlayerDTO(List<CardDTO> cards) {
        this.cards = cards;
    }

    public List<CardDTO> getCards() {
        return cards;
    }

    public void setCards(List<CardDTO> cards) {
        this.cards = cards;
    }

    public boolean isYourTurn() {
        return isYourTurn;
    }

    public void setYourTurn(boolean yourTurn) {
        isYourTurn = yourTurn;
    }
}