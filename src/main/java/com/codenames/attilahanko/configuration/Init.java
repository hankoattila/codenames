package com.codenames.attilahanko.configuration;

import com.codenames.attilahanko.model.*;
import com.codenames.attilahanko.repository.CardRepository;
import com.codenames.attilahanko.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Init {

    public Init(GameRepository gameRepository, CardRepository cardRepository) {
        Game game = new Game();
        game.setName("TestRoom");
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
        User user = new User();
        user.setName("BossTwo");
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
        User user3 = new User();
        user3.setName("user3");
        User user4 = new User();
        user4.setName("user4");
        User user5 = new User();
        user5.setName("user5");
        User user6 = new User();
        user6.setName("user6");
        User user7 = new User();
        user7.setName("user7");
        User user8 = new User();
        user8.setName("user8");
        User user9 = new User();
        user9.setName("user9");
        User user10 = new User();
        user10.setName("user10");
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);
        users.add(user7);
        users.add(user8);
        users.add(user9);
        users.add(user10);
        return users;
    }

    private void generateCards(CardRepository cardRepository) {
        for (int i = 0; i < 24; i++) {
            Card card = new Card("card" + i);
            cardRepository.save(card);
        }
    }
}
