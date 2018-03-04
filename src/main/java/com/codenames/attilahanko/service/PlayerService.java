package com.codenames.attilahanko.service;

import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;

public interface PlayerService {

    Player getPlayerByUser(User user);

}

