package com.codenames.attilahanko.service;

import com.codenames.attilahanko.model.game.Game;

public interface GameService {

    Game findByName(String gameName);

    Game findOne(Long id);

    void save(Game game);

}
