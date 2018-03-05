package com.codenames.attilahanko.service.implementation;

import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Boss;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CreateGameService {

    private GameService gameService;
    private UserService userService;

    public CreateGameService(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    public void createGame(Game game) {
        gameService.save(game);
    }

    public Game selectGame(String gameName) {
        Game game = gameService.findByName(gameName);
        if (game == null) {
            throw new UsernameNotFoundException("Invalid");
        }
        return game;
    }

    public User createUser(String userName, String gameName) {
        Game game = gameService.findByName(gameName);
        User user = userService.getUserByUserNameAndGameId(userName, game.getId());
        if (user == null) {
            user = new User(userName);
            user.setGame(game);
        } else {
            return user;
        }
        return userService.save(user);
    }

    public String addUser(User user, String gameName) {
        String role;
        Game game = gameService.findByName(gameName);
        Team teamA = game.getTeams().get(0);
        Team teamB = game.getTeams().get(1);
        if (teamA.getBoss() == null) {
            teamA.setBoss(new Boss(user));
            role = "boss";
        } else if (teamB.getBoss() == null) {
            teamB.setBoss(new Boss(user));
            role = "boss";
        } else if (teamA.getSize() >= teamB.getSize()) {
            teamB.addPlayer(new Player(user));
            role = "player";
        } else {
            teamA.addPlayer(new Player(user));
            role = "player";
        }

        gameService.save(game);
        return role;
    }
}
