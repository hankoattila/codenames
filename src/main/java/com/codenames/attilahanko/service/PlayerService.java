package com.codenames.attilahanko.service;

import com.codenames.attilahanko.model.Player;
import com.codenames.attilahanko.model.User;

public interface PlayerService {

    Player getPlayerByUser(User user);

}

