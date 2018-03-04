package com.codenames.attilahanko.controller;


import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.game.InGameService;
import com.codenames.attilahanko.utils.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GameController {

    private GameService gameService;
    private InGameService inGameService;

    public GameController(GameService gameService, InGameService inGameService) {
        this.gameService = gameService;
        this.inGameService = inGameService;
    }

    @PostMapping(Path.Web.START)
    public String startGame(HttpServletRequest httpServletRequest) {
        return inGameService.handleGameStart(httpServletRequest);
    }

    @GetMapping(Path.Web.BOSS)
    public String serveBossPage(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("boss") == null) {
            return "redirect:" + Path.Web.PLAYER;
        }

        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        Game game = gameService.getGameByName(gameName);
        model.addAttribute("board", game.getBoard().getCards());
        return Path.Template.BOSS;
    }

    @GetMapping(Path.Web.PLAYER)
    public String servePlayerPage(Model model, HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        if (httpServletRequest.getSession().getAttribute("player") == null) {
            return "redirect:" + Path.Web.BOSS;
        }
        Game game = gameService.getGameByName(gameName);
        model.addAttribute("board", game.getBoard().getCards());

        return Path.Template.PLAYER;
    }

}
