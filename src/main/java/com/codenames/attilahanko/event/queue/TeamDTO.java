package com.codenames.attilahanko.event.queue;

import java.util.ArrayList;
import java.util.List;

public class TeamDTO {

    private String name;
    private List<String> player = new ArrayList<>();
    private String boss;

    public TeamDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPlayer() {
        return player;
    }

    public void setPlayer(List<String> player) {
        this.player = player;
    }

    public void addPlayer(String name){
        player.add(name);
    }

    public String getBoss() {
        return boss;
    }

    public void setBoss(String boss) {
        this.boss = boss;
    }
}
