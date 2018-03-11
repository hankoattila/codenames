package com.codenames.attilahanko.model.game;

import com.codenames.attilahanko.model.player.UserAccount;

import javax.persistence.*;
import java.util.*;

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
    private List<String> roles;

    @ElementCollection
    private Set<Integer> flipped;

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
        currentTeamName = blue.getName();
        roles = Arrays.asList(
                "blue", "blue", "red", "red", "grey",
                "blue", "blue", "red", "black", "grey",
                "blue", "blue", "red", "grey", "grey",
                "blue", "red", "red", "grey", "grey",
                "blue", "red", "red", "grey", "blue"
        );
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
