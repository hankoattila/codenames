package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.utils.Path;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class JoinController {
    private GameService gameService;
    private ApplicationEventPublisher publisher;

    public JoinController(GameService gameService,
                          ApplicationEventPublisher publisher) {
        this.gameService = gameService;
        this.publisher = publisher;
    }

    @GetMapping({Path.Web.INDEX, Path.Web.ENTER})
    public String serveEnterPage(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getAttribute("game-name") == null ?
                Path.Template.ENTER : "redirect:" + Path.Web.NICKNAME;
    }

    @PostMapping(Path.Web.ENTER)
    public String handleEnterPage(@ModelAttribute("game-name") String gameName, HttpServletRequest httpServletRequest) {
        return gameService.handleEnterPage(gameName, httpServletRequest);
    }


    @GetMapping(Path.Web.NICKNAME)
    public String serveSelectNamePage(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("game-name") == null) {
            return "redirect:" + Path.Web.INDEX;
        }
        if (httpServletRequest.getSession().getAttribute("user") != null) {
            return "redirect:" + Path.Web.QUEUE;
        }

        model.addAttribute("user", new User());
        return Path.Template.NICKNAME;
    }

    @PostMapping(Path.Web.NICKNAME)
    public String handleSelectName(@ModelAttribute User user, Model model, HttpServletRequest httpServletRequest) {
        return gameService.handleSelectName(user, model, httpServletRequest);
    }

    @GetMapping(Path.Web.QUEUE)
    public String serveQueue(Model model, HttpServletRequest httpServletRequest) {
        return gameService.serveQueue(model, httpServletRequest);
    }

}
