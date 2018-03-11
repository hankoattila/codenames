package com.codenames.attilahanko.service.implementation;

import com.codenames.attilahanko.event.queue.newUserJoined;
import com.codenames.attilahanko.model.game.Board;
import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Boss;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class CreateGameService {

    private GameService gameService;
    private UserService userService;
    private PlayerService playerService;
    private CardService cardService;
    private ApplicationEventPublisher publisher;

    public CreateGameService(GameService gameService,
                             UserService userService,
                             PlayerService playerService,
                             CardService cardService,
                             ApplicationEventPublisher publisher) {
        this.gameService = gameService;
        this.userService = userService;
        this.playerService = playerService;
        this.cardService = cardService;
        this.publisher = publisher;
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

    public String addUser(User user, Game game) {
        String role;
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
        publisher.publishEvent(new newUserJoined(game.getTeams()));
        return role;
    }

    public void start(Game game) {
        List<Card> cards = cardService.findAll();
        Collections.shuffle(cards);
        Board board = game.getBoard();
        for (Card card : cards) {
            board.addCard(card);
        }
        game.setGameActive();
    }

    public String getTeamName(User user, String role) {
        if (role.equals("player")) {
            return playerService.getPlayerByUserId(user.getId()).getTeam().getName();
        }
        // TODO: 2018.03.09. Get team name of boss;
        return "teamName";
    }
}

