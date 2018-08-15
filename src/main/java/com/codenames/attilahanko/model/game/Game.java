package com.codenames.attilahanko.model.game;

import com.codenames.attilahanko.model.player.UserAccount;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "games")
public final class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount host;


    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private Board board;

    @ElementCollection
    private List<String> roles = new ArrayList<>();

    @ElementCollection
    private Set<Integer> flipped;

    private boolean gameOver = false;

    private String currentTeamName;

    private boolean isGameActive = false;

    public Game() {
    }

    public Game(UserAccount host, String name, Team blue, Team red, Board board) {
        this.name = name;
        addTeam(blue);
        addTeam(red);
        setBoard(board);
        setHost(host);
        currentTeamName = red.getName();
        red.setCardLeft(8);
        blue.setCardLeft(7);
        int grey = 24 - red.getCardLeft() - blue.getCardLeft();
        for (int i = 0; i < red.getCardLeft(); i++) {
            roles.add("red");

        }
        for (int i = 0; i < blue.getCardLeft(); i++) {
            roles.add("blue");

        }
        for (int i = 0; i < grey; i++) {
            roles.add("grey");

        }
        roles.add("black");
        Collections.shuffle(roles);

    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    private void addTeam(Team team) {
        teams.add(team);
        team.setGame(this);
    }

    public UserAccount getHost() {
        return host;
    }

    private void setHost(UserAccount host) {
        this.host = host;
        host.setGame(this);
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
        board.setGame(this);
    }

    public List<String> getRoles() {
        return roles;
    }

    private void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive() {
        isGameActive = true;
    }

    public Set<Integer> getFlipped() {
        return flipped;
    }

    private void setFlipped(Set<Integer> flipped) {
        this.flipped = flipped;
    }

    public void addFipped(int cardIndex) {
        flipped.add(cardIndex);
    }

    public String getCurrentTeamName() {
        return currentTeamName;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    public void nextTeam() {
        for (int i = 0; i < teams.size() - 1; i++) {
            if (teams.get(i).getName().equals(currentTeamName)) {
                currentTeamName = teams.get(i + 1).getName();
                return;
            }
        }
        currentTeamName = teams.get(0).getName();
    }

}
