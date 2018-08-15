package com.codenames.attilahanko.model.game;


import com.codenames.attilahanko.model.player.Boss;
import com.codenames.attilahanko.model.player.Player;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public final class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    private String name;

    private String color;

    private String picture;

    private int cardLeft;

    @OneToMany(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Player> players = new ArrayList<>();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne(mappedBy = "team", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Boss boss;


    public Team() {
    }

    public Team(String teamName, String color, String picture) {
        this.name = teamName;
        this.color = color;
        this.picture = picture;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void addPlayer(Player player) {
        players.add(player);
        player.setTeam(this);
    }

    public Boss getBoss() {
        return boss;
    }

    public void setBoss(Boss boss) {
        this.boss = boss;
        boss.setTeam(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCardLeft() {
        return cardLeft;
    }

    public void setCardLeft(int cardLeft) {
        this.cardLeft = cardLeft;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public int getSize() {
        return boss == null ? players.size() : players.size() + 1;
    }

    public boolean isDecision() {
        int selected = -1;
        for (Player player : players) {
            if (player.getSelected() != null) {
                selected = player.getSelected();
                break;
            }
        }
        for (Player player : players) {
            if (player.getSelected() == null) {
                return false;
            }
            if (player.getSelected() != selected) {
                return false;
            }
        }
        return true;
    }

    public int getSelected() {
        return players.get(0).getSelected();
    }
}
