package com.codenames.attilahanko.service.implementation;

import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.repository.GameRepository;
import com.codenames.attilahanko.service.GameService;
import org.springframework.stereotype.Service;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;


    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    public Game findByName(String gameName) {
        return gameRepository.findByName(gameName);
    }

    public Game findOne(Long id) {
        return gameRepository.findOne(id);
    }

    public void save(Game game) {
        gameRepository.save(game);
    }
}
