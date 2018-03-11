package com.codenames.attilahanko.event.queue;

import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Player;

import java.util.ArrayList;
import java.util.List;

public class newUserJoined {
    private List<TeamDTO> teams = new ArrayList<>();

    public newUserJoined(List<Team> teams) {
        createTeamDTO(teams, 0);
        createTeamDTO(teams, 1);

    }

    private void createTeamDTO(List<Team> teams, int i) {
        TeamDTO teamDTO = new TeamDTO();
        teamDTO.setBoss(teams.get(i).getBoss().getUser().getName());
        teamDTO.setName(teams.get(i).getName());
        for (Player player : teams.get(i).getPlayers()) {
            teamDTO.addPlayer(player.getUser().getName());
        }
        this.teams.add(teamDTO);
    }

    public List<TeamDTO> getTeamList() {
        return teams;
    }
}