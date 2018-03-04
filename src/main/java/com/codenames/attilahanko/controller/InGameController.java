package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.PlayerDTO;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.game.InGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class InGameController {



    private GameService gameService;
    private InGameService inGameService;

    public InGameController(GameService gameService, InGameService inGameService) {
        this.gameService = gameService;
        this.inGameService = inGameService;
    }

    @PostMapping("/queueEdit")
    @ResponseBody
    public List<List<String>> queueEdit(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        Game game = gameService.getGameByName(gameName);
        List<List<String>> players = new ArrayList<>();
        for (Team team : game.getTeams()) {
            List<String> playersName = new ArrayList<>();
            for (Player player : team.getPlayers()) {
                playersName.add(player.getUser().getName());
            }
            players.add(playersName);
        }
        return players;
    }

    @PostMapping("/bossEdit")
    @ResponseBody
    public List<String> bossEdit(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        Game game = gameService.getGameByName(gameName);
        return game.getBoard().getRoles();
    }

    @PostMapping("/playerEdit")
    @ResponseBody
    public PlayerDTO playerEdit(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        return inGameService.handlePlayerPoll(gameName, user);
    }

}
