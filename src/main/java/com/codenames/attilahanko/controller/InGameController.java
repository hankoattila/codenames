package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.PlayerDTO;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.game.InGameService;
import com.codenames.attilahanko.service.implementation.GameServiceImpl;
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

    @PostMapping("/bossEdit")
    @ResponseBody
    public List<String> bossEdit(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        Game game = gameService.findByName(gameName);
        return game.getBoard().getRoles();
    }

    @PostMapping("/playerEdit")
    @ResponseBody
    public List<Card> playerEdit(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        Game game = gameService.findByName(gameName);
        return game.getBoard().getCards();
    }

}
