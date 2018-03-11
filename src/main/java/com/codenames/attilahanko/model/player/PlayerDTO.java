package com.codenames.attilahanko.model.player;

import com.codenames.attilahanko.model.game.Card;

import java.util.List;

public class PlayerDTO {
    private List<Card> cards;
    private List<String> colors;

    public PlayerDTO(List<Card> cards, List<String> colors) {
        this.cards = cards;
        this.colors = colors;
    }

    public List<Card> getCards() {
        return cards;
    }

    public List<String> getColors() {
        return colors;
    }
}
