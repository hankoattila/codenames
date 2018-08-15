package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.PlayerDTO;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.SelectCardService;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Set;

@Controller
public class AjaxRequestController {


    private GameService gameService;
    private SelectCardService selectCardService;

    public AjaxRequestController(GameService gameService, SelectCardService selectCardService) {
        this.gameService = gameService;
        this.selectCardService = selectCardService;
    }

    @PostMapping("/bossEdit")
    @ResponseBody
    public List<String> bossEdit(HttpServletRequest httpServletRequest) {
        Enumeration e = (Enumeration) (httpServletRequest.getSession().getAttributeNames());
// TODO: 2018.03.13. Refactor
        while (e.hasMoreElements()) {
            Object tring;
            if ((tring = e.nextElement()) != null) {
                System.out.println(httpServletRequest.getSession().getAttribute((String) tring));

            }

        }
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        Game game = gameService.findByName(gameName);
        return game.getRoles();
    }

    @PostMapping("/playerEdit")
    @ResponseBody
    public String playerEdit(HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        Game game = gameService.findByName(gameName);
        Gson gson = new Gson();
        Set<Integer> flipped = game.getFlipped();
        List<String> colors = new ArrayList<>();
        for (int i = 0; i < game.getRoles().size(); i++) {
            if (flipped.contains(i)) {
                colors.add(game.getRoles().get(i));
            } else {
                colors.add("");
            }
        }
        PlayerDTO playerDTO = new PlayerDTO(game.getBoard().getCards(), colors);

        return gson.toJson(playerDTO);
    }

    @PostMapping("/selectCard")
    @ResponseBody
    public String selectCard(HttpServletRequest httpServletRequest, @ModelAttribute("id") int selectedCardIndex) {
        User user = (User) httpServletRequest.getSession().getAttribute("player");
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        if (user != null) {
            selectCardService.handleSelectCard(gameName, user, selectedCardIndex);
        }
        return HttpStatus.OK.toString();
    }


}
