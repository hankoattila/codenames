package com.codenames.attilahanko.service;

import com.codenames.attilahanko.model.Game;
import com.codenames.attilahanko.model.PlayerDTO;
import com.codenames.attilahanko.model.User;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface GameService {

    Game findByName(String name);

    void saveGame(Game game);

    String handleSelectName(User user, Model model, HttpServletRequest httpServletRequest);

    Game getGameByName(String gameName);

    PlayerDTO handlePlayerPoll(String gameName, User user);
}
