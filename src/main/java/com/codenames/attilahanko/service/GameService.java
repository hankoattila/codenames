package com.codenames.attilahanko.service;

import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.repository.GameRepository;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

public interface GameService {

    Game findByName(String gameName);

    Game findOne(Long id);

    void save(Game game);
}
