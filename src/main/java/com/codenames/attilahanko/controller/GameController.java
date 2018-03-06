package com.codenames.attilahanko.controller;


import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.CreateGameService;
import com.codenames.attilahanko.service.implementation.game.InGameService;
import com.codenames.attilahanko.utils.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GameController {

    private GameService gameService;
    private CreateGameService createGameService;

    public GameController(GameService gameService, CreateGameService createGameService) {
        this.gameService = gameService;
        this.createGameService = createGameService;
    }

    @PostMapping(Path.Web.START)
    public String startGame(HttpServletRequest request) {
        String gameName = (String) request.getSession().getAttribute("game-name");
        String role = request.getSession().getAttribute("player") == null ? "boss" : "player";
        Game game = gameService.findByName(gameName);
        createGameService.start(game);
        gameService.save(game);
        return role.equals("boss") ? "redirect:" + Path.Web.BOSS : "redirect:" + Path.Web.PLAYER;
    }

    @GetMapping(Path.Web.BOSS)
    public String serveBossPage(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("boss") == null) {
            return "redirect:" + Path.Web.PLAYER;
        }

        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        Game game = gameService.findByName(gameName);
        model.addAttribute("board", game.getBoard().getCards());
        return Path.Template.BOSS;
    }

    @GetMapping(Path.Web.PLAYER)
    public String servePlayerPage(Model model, HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        if (httpServletRequest.getSession().getAttribute("player") == null) {
            return "redirect:" + Path.Web.BOSS;
        }
        Game game = gameService.findByName(gameName);
        model.addAttribute("board", game.getBoard().getCards());

        return Path.Template.PLAYER;
    }

}
