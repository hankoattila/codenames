package com.codenames.attilahanko.service;

import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface GameService {

    String handleSelectName(User user, Model model, HttpServletRequest httpServletRequest);

    void addUserToGame(User user, Game game, HttpServletRequest httpServletRequest);

    Game getGameByName(String gameName);

    Game handleSelectGame(String gameName);

    String handleEnterPage(String gameName, HttpServletRequest httpServletRequest);

    String serveQueue(Model model, HttpServletRequest httpServletRequest);
}
