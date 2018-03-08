package com.codenames.attilahanko.model.game;

import com.codenames.attilahanko.model.player.Player;
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

    private String currentTeamName;

    private boolean isGameActive = false;

    public Game() {
    }

    public Game(String name, Team blue, Team red, Board board) {
        this.name = name;
        addTeam(blue);
        addTeam(red);
        setBoard(board);
        currentTeamName = blue.getName();
    }


    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public void setGameActive() {
        isGameActive = true;
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

    public void setSelected(int index, Player player) {
        if (player.getSelected() != null){
            board.getCards().get(player.getSelected()).setSelected(false);
        }
        board.getCards().get(index).setSelected(true);
        player.setSelected(index);
    }
}
