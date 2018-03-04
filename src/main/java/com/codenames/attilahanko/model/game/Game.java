package com.codenames.attilahanko.model.game;

import com.codenames.attilahanko.model.player.UserAccount;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Team> teams = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private UserAccount host;

    @OneToOne(cascade = CascadeType.ALL)
    private Board board;

    private String currentTeam;


    public Game() {
        Team blue = new Team("Blue");
        Team red = new Team("Red");
        blue.setPicture("/pictures/panda.jpg");
        red.setPicture("/pictures/unicorn.jpg");
        addTeam(blue);
        addTeam(red);
        Board board = new Board();
        this.board = board;
        board.setGame(this);
        currentTeam = blue.getName();
    }

    private boolean isGameActive = false;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<Team> getTeams() {
        return teams;
    }


    public void addTeam(Team team) {
        teams.add(team);
        team.setGame(this);
    }

    public UserAccount getHost() {
        return host;
    }

    public void setHost(UserAccount host) {
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

    public boolean isGameActive() {
        return isGameActive;
    }

    public void setGameActive(boolean gameActive) {
        isGameActive = gameActive;
    }

    public String getCurrentTeam() {
        return currentTeam;
    }

    public void setCurrentTeam(String currentTeam) {
        this.currentTeam = currentTeam;
    }
}
