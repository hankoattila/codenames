package com.codenames.attilahanko.model.game;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @OrderColumn
    private List<Card> cards = new ArrayList<>();


    @ElementCollection
    private List<String> roles;

    public Board() {
        roles = Arrays.asList(
                "blue", "red", "blue", "grey", "grey",
                "blue", "red", "grey", "grey", "grey",
                "blue", "red", "grey", "grey", "grey",
                "blue", "red", "black", "grey", "grey",
                "blue", "red", "grey", "grey", "grey"
        );
        Collections.shuffle(roles);
    }

    @JsonIgnore
    @OneToOne
    private Game game;

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    @Override
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (Card card : cards) {
            board.append(card.getValue() + " ");
        }
        return board.toString();
    }
}
