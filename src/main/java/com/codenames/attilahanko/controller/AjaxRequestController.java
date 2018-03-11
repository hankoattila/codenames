package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.event.player.CardSelected;
import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.PlayerService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AjaxRequestController {


    private GameService gameService;
    private PlayerService playerService;
    private ApplicationEventPublisher publisher;

    public AjaxRequestController(GameService gameService,
                                 PlayerService playerService,
                                 ApplicationEventPublisher publisher) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.publisher = publisher;
    }

    @PostMapping("/bossEdit")
    @ResponseBody
    public List<String> bossEdit(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        Game game = gameService.findByName(gameName);
        return game.getBoard().getRoles();
    }

    @PostMapping("/playerEdit")
    @ResponseBody
    public List<Card> playerEdit(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        Game game = gameService.findByName(gameName);
        return game.getBoard().getCards();
    }

    @GetMapping("/testCards")
    @ResponseBody
    public String testCard(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        Game game = gameService.findByName(gameName);
        return game.getBoard().toString();

    }

    @PostMapping("/selectCard")
    @ResponseBody
    public String selectCard(HttpServletRequest httpServletRequest, @ModelAttribute("id") int id) {
        User user = (User) httpServletRequest.getSession().getAttribute("player");
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        if (user != null) {
            Game game = gameService.findByName(gameName);
            Player player = playerService.getPlayerByUserId(user.getId());
            game.setSelected(id, player);
            List<Player> players = player.getTeam().getPlayers();
            List<Integer> listOfIndex = new ArrayList<>();
            Map<Integer, String> colors = new HashMap<>();
            for (Player player1 : players) {
                if (player.getSelected() != null) {
                    listOfIndex.add(player1.getSelected());
                }
            }
            gameService.save(game);
            if (player.getTeam().isAllPlayerSelect()) {
                colors.put(id, game.getBoard().getRoles().get(id));
            }
            publisher.publishEvent(new CardSelected(listOfIndex, colors));
        }
        return "ok";
    }


}
