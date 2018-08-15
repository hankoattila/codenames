package com.codenames.attilahanko.controller;


import com.codenames.attilahanko.event.StartGame;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.CreateGameService;
import com.codenames.attilahanko.service.implementation.PlayerService;
import com.codenames.attilahanko.utils.Path;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class GameController {

    private GameService gameService;
    private CreateGameService createGameService;
    private PlayerService playerService;
    private ApplicationEventPublisher publisher;

    public GameController(GameService gameService,
                          CreateGameService createGameService,
                          PlayerService playerService,
                          ApplicationEventPublisher publisher) {
        this.gameService = gameService;
        this.createGameService = createGameService;
        this.playerService = playerService;
        this.publisher = publisher;
    }

    @PostMapping(Path.Web.START)
    public String startGame(HttpServletRequest request) {
        String gameName = (String) request.getSession().getAttribute("gameName");
        String role = request.getSession().getAttribute("player") == null ? "boss" : "player";
        Game game = gameService.findByName(gameName);
        createGameService.start(game);
        gameService.save(game);
        // TODO: 2018.03.06. Should start game on player side without refresh
        publisher.publishEvent(new StartGame(game.isGameActive()));
        return role.equals("boss") ? "redirect:" + Path.Web.BOSS : "redirect:" + Path.Web.PLAYER;
    }

    @GetMapping(Path.Web.BOSS)
    public String serveBossPage(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("boss") == null) {
            if (httpServletRequest.getSession().getAttribute("player") == null) {
                return "redirect:" + Path.Web.INDEX;
            }
            return "redirect:" + Path.Web.PLAYER;
        }

        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        Game game = gameService.findByName(gameName);
        model.addAttribute("board", game.getBoard().getCards());
        return Path.Template.BOSS;
    }

    @GetMapping(Path.Web.PLAYER)
    public String servePlayerPage(Model model, HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (httpServletRequest.getSession().getAttribute("player") == null) {
            if (httpServletRequest.getSession().getAttribute("boss") == null) {
                return "redirect:" + Path.Web.INDEX;
            }
            return "redirect:" + Path.Web.BOSS;
        }
        Game game = gameService.findByName(gameName);
        Player player = playerService.getPlayerByUserId(user.getId());
        model.addAttribute("board", game.getBoard().getCards());
        model.addAttribute("team", game.getCurrentTeamName());
        model.addAttribute("color", player.getTeam().getColor());

        return Path.Template.PLAYER;
    }

}
