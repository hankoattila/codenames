package com.codenames.attilahanko.configuration;

import com.codenames.attilahanko.model.game.Board;
import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Boss;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.model.player.UserAccount;
import com.codenames.attilahanko.repository.CardRepository;
import com.codenames.attilahanko.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Init {

    public Init(GameRepository gameRepository, CardRepository cardRepository) {
        Team blue = new Team("Blue");
        Team red = new Team("Red");
        blue.setPicture("/pictures/panda.jpg");
        red.setPicture("/pictures/unicorn.jpg");
        Board board = new Board();
        Game game = new Game("TestRoom",blue, red,board);
        List<User> users = createUsers();
        int index = 0;

        UserAccount userAccount = new UserAccount();
        userAccount.setName("Attila");
        userAccount.setName("1234");
        game.setHost(userAccount);
        for (User user : users) {
            index = index == 0 ? 1 : 0;
            Team team = game.getTeams().get(index);
            Player player = new Player(user);
            player.setTeam(team);
            team.addPlayer(player);
            user.setGame(game);
        }
        User user = new User("BossTwo");
        user.setGame(game);
        Boss boss = new Boss(user);
        game.getTeams().get(1).setBoss(boss);
        gameRepository.save(game);
        generateCards(cardRepository);
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setName("Attila");
        User user2 = new User();
        user2.setName("user2");
        users.add(user1);
        users.add(user2);
        return users;
    }

    private void generateCards(CardRepository cardRepository) {
        for (int i = 0; i < 25; i++) {
            Card card = new Card("card" + i);
            cardRepository.save(card);
        }
    }
}
