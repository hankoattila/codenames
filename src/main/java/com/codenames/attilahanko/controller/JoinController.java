package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.event.queue.QueueDTO;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.CreateGameService;
import com.codenames.attilahanko.service.implementation.UserService;
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
    private UserService userService;
    private CreateGameService createGameService;
    private ApplicationEventPublisher publisher;


    public JoinController(GameService gameService,
                          UserService userService,
                          CreateGameService createGameService,
                          ApplicationEventPublisher publisher) {
        this.gameService = gameService;
        this.userService = userService;
        this.createGameService = createGameService;
        this.publisher = publisher;
    }

    @GetMapping({Path.Web.INDEX, Path.Web.ENTER})
    public String serveEnterPage(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getAttribute("game-name") == null ?
                Path.Template.ENTER : "redirect:" + Path.Web.NICKNAME;
    }

    @PostMapping(Path.Web.ENTER)
    public String handleEnterPage(@ModelAttribute("game-name") String gameName, HttpServletRequest httpServletRequest) {
        Game game = gameService.findByName(gameName);
        if (game == null) {
            return "redirect:" + Path.Web.ENTER + "?error";
        } else {
            httpServletRequest.getSession().setAttribute("game-name", game.getName());
        }
        return "redirect:" + Path.Web.NICKNAME;
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
    public String handleSelectName(@ModelAttribute User modelUser, HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        Game game = gameService.findByName(gameName);
        User user = userService.findByName(modelUser.getName());
        if (user == null) {
            user = createGameService.createUser(modelUser.getName(), gameName);
        }
        String role = createGameService.addUser(user, game);
        httpServletRequest.getSession().setAttribute("user", user);
        httpServletRequest.getSession().setAttribute(role, user);
        publisher.publishEvent(new QueueDTO(game.getTeams()));
        return "redirect:" + Path.Web.QUEUE;

    }

    @GetMapping(Path.Web.QUEUE)
    public String serveQueue(Model model, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        User boss = (User) httpServletRequest.getSession().getAttribute("boss");
        User player = (User) httpServletRequest.getSession().getAttribute("player");
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");

        if (user == null) {
            return "redirect:" + Path.Web.NICKNAME;
        }
        Game game = gameService.findByName(gameName);

        if (game.isGameActive()) {
            if (boss == null) {
                return "redirect:" + Path.Web.BOSS;
            } else if (player == null) {
                return "redirect:" + Path.Web.PLAYER;
            }
        }
        model.addAttribute("game", game);
        return Path.Template.QUEUE;
    }


}
