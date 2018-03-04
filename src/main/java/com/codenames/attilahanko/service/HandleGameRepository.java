package com.codenames.attilahanko.service;

import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.User;


public interface HandleGameRepository {

    void saveGame(Game game);

    Game findByName(String name);

    Game getGameByName(String gameName);

    User getUserByUserNameAndGameId(String userName, Long id);
}
