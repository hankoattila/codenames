package com.codenames.attilahanko.model.player;

import com.codenames.attilahanko.model.game.Team;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name = "bosses")
public class Boss {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne
    private Team team;


    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Boss() {
    }

    public Boss(User user) {
        this.user = user;
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
