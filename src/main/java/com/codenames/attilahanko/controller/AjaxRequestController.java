package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.service.GameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class InGameController {


    private GameService gameService;


    public InGameController(GameService gameService) {
        this.gameService = gameService;

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
