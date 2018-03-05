package com.codenames.attilahanko.service.implementation;

import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.repository.PlayerRepository;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    private PlayerRepository playerRepository;

    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player findOne(Long id){
        return playerRepository.findOne(id);
    }

    public Player getPlayerByUser(User user) {
        return playerRepository.findByUserId(user.getId());
    }
}
