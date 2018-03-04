package com.codenames.attilahanko.service.implementation.game;

import com.codenames.attilahanko.event.queue.NewPlayerAdded;
import com.codenames.attilahanko.model.*;
import com.codenames.attilahanko.repository.GameRepository;
import com.codenames.attilahanko.repository.PlayerRepository;
import com.codenames.attilahanko.repository.UserRepository;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.utils.Path;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private GameRepository gameRepository;
    private UserRepository userRepository;
    private PlayerRepository playerRepository;
    private ApplicationEventPublisher publisher;

    public GameServiceImpl(GameRepository gameRepository,
                           UserRepository userRepository,
                           PlayerRepository playerRepository,
                           ApplicationEventPublisher publisher) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.playerRepository = playerRepository;
        this.publisher = publisher;
    }

    @Override
    public String handleSelectName(User user, Model model, HttpServletRequest httpServletRequest) {
        String gameName = (String) httpServletRequest.getSession().getAttribute("game-name");
        Game game = gameRepository.findByName(gameName);
        User savedUser = userRepository.findByNameAndGameId(user.getName(), game.getId());
        if (savedUser != null) {
            setSessionAttributeFromSavedUser(savedUser, game, httpServletRequest);
        } else {
            addUserToGame(game, user, httpServletRequest);
            gameRepository.save(game);
            httpServletRequest.getSession().setAttribute("user", user);
        }
        return "redirect:" + Path.Web.QUEUE;
    }


    @Override
    public void addUserToGame(Game game, User user, HttpServletRequest httpServletRequest) {
        user.setGame(game);
        for (Team team : game.getTeams()) {
            if (team.getBoss() == null) {
                Boss boss = new Boss(user);
                team.setBoss(boss);
                httpServletRequest.getSession().setAttribute("boss", boss);
                return;
            }
        }
        Player player = new Player(user);
        int teamOne = game.getTeams().get(0).getSize();
        int teamTwo = game.getTeams().get(1).getSize();
        if (teamOne >= teamTwo) {
            game.getTeams().get(1).addPlayer(player);
            player.setTeam(game.getTeams().get(1));
        } else {
            game.getTeams().get(0).addPlayer(player);
            player.setTeam(game.getTeams().get(0));
        }
        httpServletRequest.getSession().setAttribute("player", player);
        publisher.publishEvent(new NewPlayerAdded(user.getName()));


    }

    @Override
    public Game getGameByName(String gameName) {
        return gameRepository.findByName(gameName);
    }


    @Override
    public Game findByName(String name) {
        return gameRepository.findByName(name);
    }


    @Override
    public void saveGame(Game gameToSave) {
        if (gameToSave == null) {
            throw new UsernameNotFoundException("Not Found");
        }
        gameRepository.save(gameToSave);
    }

    public void setSessionAttributeFromSavedUser(User savedUser, Game game, HttpServletRequest session) {

        User user = savedUser;
        for (Team team : game.getTeams()) {
            if (team.getBoss() != null) {
                if (team.getBoss().getUser().getName().equals(user.getName())) {
                    session.getSession().setAttribute("boss", team.getBoss());
                    break;
                }
            }
            for (Player player : team.getPlayers()) {
                if (player.getUser().getName().equals(savedUser.getName())) {
                    session.getSession().setAttribute("player", player);
                    break;
                }
            }
        }
        session.getSession().setAttribute("user", user);
    }

    @Override
    public PlayerDTO handlePlayerPoll(String gameName, User user) {
        PlayerDTO playerDTO = new PlayerDTO();
        Game game = gameRepository.findByName(gameName);
        Player player = playerRepository.findById(user.getId());
        playerDTO.setYourTurn(isYourTurn(game, player));
        playerDTO.setCards(getCards(game));
        publisher.publishEvent(playerDTO);
        return playerDTO;
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

    public boolean isYourTurn(Game game, Player player) {
        return game.getCurrentTeam().equals(player.getTeam().getName());
    }
}
