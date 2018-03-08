package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.event.player.CardDTO;
import com.codenames.attilahanko.event.player.PlayerDTO;
import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
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
import java.util.List;

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
        if (user != null) {
            String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
            saveSelect(id, user, gameName);

        }
        return "ok";
    }

    private void saveSelect(@ModelAttribute("id") int index, User user, String gameName) {
        Player player = playerService.getPlayerByUserId(user.getId());
        Game game = gameService.findByName(gameName);
        game.setSelected(index, player);
        for (Team team : game.getTeams()) {
            for (Player player1 : team.getPlayers()) {
                if (player1.getSelected() != null) {
                    game.getBoard().getCards().get(player1.getSelected()).setSelected(true);
                }
            }
        }
        List<CardDTO> cardDTOS = new ArrayList<>();
        for (Card card : game.getBoard().getCards()) {
            cardDTOS.add(new CardDTO(card.getValue(), card.isFlopped(), card.isSelected()));
        }

        publisher.publishEvent(new PlayerDTO(cardDTOS));
        gameService.save(gameService.findByName(gameName));
    }

}
