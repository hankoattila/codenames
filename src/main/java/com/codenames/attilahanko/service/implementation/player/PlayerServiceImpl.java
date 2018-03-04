package com.codenames.attilahanko.service.implementation.player;

import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.repository.PlayerRepository;
import com.codenames.attilahanko.service.PlayerService;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService {

    private PlayerRepository playerRepository;

    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    @Override
    public Player getPlayerByUser(User user) {
        return playerRepository.findById(user.getId());
    }
}
