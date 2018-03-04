package com.codenames.attilahanko.controller;


import com.codenames.attilahanko.model.*;
import com.codenames.attilahanko.repository.CardRepository;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.PlayerService;
import com.codenames.attilahanko.utils.Path;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GameController {

    private PlayerService playerService;
    private GameService gameService;
    private CardRepository cardRepository;

    public GameController(GameService gameService, CardRepository cardRepository, PlayerService playerService) {
        this.gameService = gameService;
        this.cardRepository = cardRepository;
        this.playerService = playerService;
    }

    @PostMapping(Path.Web.START)
    public String startGame(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        String role = httpServletRequest.getSession().getAttribute("player") == null ? "boss" : "player";
        Game game = gameService.getGameByName(gameName);
        game.setGameActive(true);
        List<Card> cards = cardRepository.findAll();
        for (Card card : cards) {
            game.getBoard().addCard(card);
        }
        gameService.saveGame(game);
        return role.equals("boss") ? "redirect:" + Path.Web.BOSS : "redirect:" + Path.Web.PLAYER;
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
        return gameService.handlePlayerPoll(gameName, user);
    }


}
