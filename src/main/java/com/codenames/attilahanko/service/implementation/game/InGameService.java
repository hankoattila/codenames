package com.codenames.attilahanko.service.implementation.game;

import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.PlayerDTO;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.repository.CardRepository;
import com.codenames.attilahanko.repository.GameRepository;
import com.codenames.attilahanko.repository.PlayerRepository;
import com.codenames.attilahanko.repository.UserRepository;
import com.codenames.attilahanko.service.HandleGameRepository;
import com.codenames.attilahanko.utils.Path;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class InGameService implements HandleGameRepository {

    private GameRepository gameRepository;
    private CardRepository cardRepository;
    private UserRepository userRepository;
    private PlayerRepository playerRepository;
    private ApplicationEventPublisher publisher;

    public InGameService(GameRepository gameRepository, PlayerRepository playerRepository,
                         ApplicationEventPublisher publisher, CardRepository cardRepository,
                         UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.playerRepository = playerRepository;
        this.publisher = publisher;
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void saveGame(Game game) {
        if (game == null) {
            throw new UsernameNotFoundException("Not Found");
        }
        gameRepository.save(game);
    }

    @Override
    public Game findByName(String name) {
        return gameRepository.findByName(name);
    }

    @Override
    public Game getGameByName(String gameName) {
        return gameRepository.findByName(gameName);
    }

    @Override
    public User getUserByUserNameAndGameId(String userName, Long id) {
        return userRepository.findByNameAndGameId(userName, id);
    }

    public PlayerDTO handlePlayerPoll(String gameName, User user) {
        PlayerDTO playerDTO = new PlayerDTO();
        Game game = gameRepository.findByName(gameName);
        Player player = playerRepository.findByUserId(user.getId());
        playerDTO.setYourTurn(isYourTurn(game, player));
        playerDTO.setCards(getCards(game));
        publisher.publishEvent(playerDTO);
        return playerDTO;
    }

    public String handleGameStart(HttpServletRequest request) {
        String gameName = getGameName(request);
        String role = request.getSession().getAttribute("player") == null ? "boss" : "player";
        Game game = getGameByName(gameName);
        game.setGameActive(true);
        List<Card> cards = cardRepository.findAll();
        for (Card card : cards) {
            game.getBoard().addCard(card);
        }
        saveGame(game);
        return role.equals("boss") ? "redirect:" + Path.Web.BOSS : "redirect:" + Path.Web.PLAYER;
    }

    private List<List<String>> getCards(Game game) {
        List<List<String>> cards = new ArrayList<>();
        for (Card card : game.getBoard().getCards()) {
            List<String> cardPair = new ArrayList<>();
            cardPair.add(card.getValue());
            if (Integer.parseInt(card.getValue().replaceAll("card", "")) % 2 == 0) {

            } else {
                cardPair.add("");
            }
            cards.add(cardPair);
        }
        return cards;
    }

    private boolean isYourTurn(Game game, Player player) {
        System.out.println(player);
        System.out.println(player.getTeam());
        return game.getCurrentTeam().equals(player.getTeam().getName());
    }

    private String getGameName(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("game-name");
    }


    private void addToSession(String key, Object object, HttpServletRequest request) {
        request.getSession().setAttribute(key, object);
    }
}
