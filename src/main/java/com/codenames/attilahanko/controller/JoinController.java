package com.codenames.attilahanko.controller;

import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.implementation.CreateGameService;
import com.codenames.attilahanko.service.implementation.UserService;
import com.codenames.attilahanko.utils.Path;
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


    public JoinController(GameService gameService, UserService userService, CreateGameService createGameService) {
        this.gameService = gameService;
        this.userService = userService;
        this.createGameService = createGameService;
    }

    @GetMapping({Path.Web.INDEX, Path.Web.ENTER})
    public String serveEnterPage(HttpServletRequest httpServletRequest) {
        return httpServletRequest.getSession().getAttribute("gameName") == null ?
                Path.Template.ENTER : "redirect:" + Path.Web.NICKNAME;
    }

    @PostMapping(Path.Web.ENTER)
    public String handleEnterPage(@ModelAttribute("gameName") String gameName, HttpServletRequest httpServletRequest) {
        Game game = gameService.findByName(gameName);
        if (game == null || game.isGameActive()) {
            return "redirect:" + Path.Web.ENTER + "?error";
        } else {
            httpServletRequest.getSession().setAttribute("gameName", game.getName());
        }
        return "redirect:" + Path.Web.NICKNAME;
    }


    @GetMapping(Path.Web.NICKNAME)
    public String serveSelectNamePage(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("gameName") == null) {
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
        String role;
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");
        Game game = gameService.findByName(gameName);
        User user = userService.findByName(modelUser.getName());
        if (user == null) {
            user = createGameService.createUser(modelUser.getName(), gameName);
            role = createGameService.addUser(user, game);
        } else {
            role = createGameService.getRole(user.getId());
        }
        String teamName = createGameService.getTeamName(user, role);
        httpServletRequest.getSession().setAttribute(role, user);
        httpServletRequest.getSession().setAttribute("user", user);
        httpServletRequest.getSession().setAttribute("team", teamName);
        return "redirect:" + Path.Web.QUEUE;

    }

    @GetMapping(Path.Web.QUEUE)
    public String serveQueue(Model model, HttpServletRequest httpServletRequest) {
        User user = (User) httpServletRequest.getSession().getAttribute("user");
        User boss = (User) httpServletRequest.getSession().getAttribute("boss");
        User player = (User) httpServletRequest.getSession().getAttribute("player");
        String gameName = (String) httpServletRequest.getSession().getAttribute("gameName");

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
