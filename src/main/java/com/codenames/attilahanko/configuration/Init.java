package com.codenames.attilahanko.configuration;

import com.codenames.attilahanko.model.game.Board;
import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Boss;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.model.player.UserAccount;
import com.codenames.attilahanko.repository.CardRepository;
import com.codenames.attilahanko.repository.GameRepository;
import org.springframework.stereotype.Component;

@Component
public class Init {

    public Init(GameRepository gameRepository, CardRepository cardRepository) {
        Team blue = new Team("Blue");
        Team red = new Team("Red");
        blue.setPicture("/pictures/panda.jpg");
        red.setPicture("/pictures/unicorn.jpg");
        Board board = new Board();
        Game game = new Game("TestRoom", blue, red, board);

        UserAccount userAccount = new UserAccount();
        userAccount.setName("Attila");
        userAccount.setName("1234");
        game.setHost(userAccount);
        User user = new User("BossTwo");
        user.setGame(game);
        Boss boss = new Boss(user);
        game.getTeams().get(1).setBoss(boss);
        gameRepository.save(game);
        generateCards(cardRepository);
    }


    private void generateCards(CardRepository cardRepository) {
        cardRepository.save(new Card("MŰ"));
        cardRepository.save(new Card("KERESZT"));
        cardRepository.save(new Card("KÁRPÁTOK"));
        cardRepository.save(new Card("SAJT"));
        cardRepository.save(new Card("PISZTOLY"));
        cardRepository.save(new Card("GITÁR"));
        cardRepository.save(new Card("FÜGG"));
        cardRepository.save(new Card("SZÜREK"));
        cardRepository.save(new Card("HÍD"));
        cardRepository.save(new Card("HAJÓ"));
        cardRepository.save(new Card("KIRÁLY"));
        cardRepository.save(new Card("LAP"));
        cardRepository.save(new Card("ÓRIÁS"));
        cardRepository.save(new Card("ADÓ"));
        cardRepository.save(new Card("MÉREG"));
        cardRepository.save(new Card("MALAC"));
        cardRepository.save(new Card("MOSZKVA"));
        cardRepository.save(new Card("MOSZKVA"));
        cardRepository.save(new Card("NEW YORK"));
        cardRepository.save(new Card("KORONA"));
        cardRepository.save(new Card("NAPÓLEON"));
        cardRepository.save(new Card("CSÖPP"));
        cardRepository.save(new Card("KOR"));
        cardRepository.save(new Card("PÉNZ"));
        cardRepository.save(new Card("MŰHOLD"));

    }
}
