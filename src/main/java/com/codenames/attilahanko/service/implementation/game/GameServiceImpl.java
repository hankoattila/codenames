package com.codenames.attilahanko.service.implementation.game;

import com.codenames.attilahanko.event.queue.NewPlayerAdded;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Boss;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.repository.GameRepository;
import com.codenames.attilahanko.repository.UserRepository;
import com.codenames.attilahanko.service.GameService;
import com.codenames.attilahanko.service.HandleGameRepository;
import com.codenames.attilahanko.utils.Path;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Service
public class GameServiceImpl implements GameService, HandleGameRepository {

    private GameRepository gameRepository;
    private UserRepository userRepository;
    private ApplicationEventPublisher publisher;

    public GameServiceImpl(GameRepository gameRepository,
                           UserRepository userRepository,
                           ApplicationEventPublisher publisher) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    @Override
    public String handleSelectName(User newUser, Model model, HttpServletRequest httpServletRequest) {
        String gameName = getGameName(httpServletRequest);
        Game game = getGameByName(gameName);
        User user = getUserByUserNameAndGameId(newUser.getName(), game.getId());


        if (user != null) {
            setSessionAttributeFromSavedUser(user, game, httpServletRequest);
        } else {
            addUserToGame(newUser, game, httpServletRequest);
            gameRepository.save(game);
            addToSession("user", newUser, httpServletRequest);
        }
        return "redirect:" + Path.Web.QUEUE;
    }

    @Override
    public void addUserToGame(User user, Game game, HttpServletRequest request) {
        for (Team team : game.getTeams()) {
            if (team.getBoss() == null) {
                Boss boss = new Boss(user);
                team.setBoss(boss);
                addToSession("boss", boss, request);
                return;
            }
        }

        Player player = new Player(user);
        Team teamOne = game.getTeams().get(0);
        Team teamTwo = game.getTeams().get(1);
        if (teamOne.getSize() >= teamTwo.getSize()) {
            teamOne.addPlayer(player);
            player.setTeam(teamOne);
        } else {
            teamTwo.addPlayer(player);
            player.setTeam(teamTwo);
        }

        user.setGame(game);
        addToSession("player", player, request);
        publisher.publishEvent(new NewPlayerAdded(user.getName()));


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

    @Override
    public Game getGameByName(String gameName) {
        return gameRepository.findByName(gameName);
    }

    @Override
    public Game handleSelectGame(String gameName) {
        return gameRepository.findByName(gameName);
    }

    @Override
    public String handleEnterPage(String gameName, HttpServletRequest httpServletRequest) {
        Game game = handleSelectGame(gameName);
        if (game == null) {
            throw new UsernameNotFoundException("Invalid");
        } else {
            addToSession("game-name", game.getName(), httpServletRequest);
        }

        return "redirect:" + Path.Web.NICKNAME;
    }

    @Override
    public String serveQueue(Model model, HttpServletRequest httpServletRequest) {
        if (httpServletRequest.getSession().getAttribute("user") == null) {
            return "redirect:" + Path.Web.NICKNAME;
        }

        String gameName = getGameName(httpServletRequest);
        Game game = getGameByName(gameName);

        if (game.isGameActive()) {
            if (httpServletRequest.getSession().getAttribute("boss") == null) {
                return "redirect:" + Path.Web.BOSS;
            } else if (httpServletRequest.getSession().getAttribute("player") == null) {
                return "redirect:" + Path.Web.PLAYER;
            }
        }

        model.addAttribute("game", game);
        return Path.Template.QUEUE;
    }

    @Override
    public User getUserByUserNameAndGameId(String userName, Long id) {
        return userRepository.findByNameAndGameId(userName, id);
    }

    private String getGameName(HttpServletRequest request) {
        return (String) request.getSession().getAttribute("game-name");
    }

    private void addToSession(String key, Object object, HttpServletRequest request) {
        request.getSession().setAttribute(key, object);
    }


    private void setSessionAttributeFromSavedUser(User user, Game game, HttpServletRequest session) {
        for (Team team : game.getTeams()) {
            if (team.getBoss() != null) {
                if (team.getBoss().getUser().getName().equals(user.getName())) {
                    session.getSession().setAttribute("boss", team.getBoss());
                    break;
                }
            }
            for (Player player : team.getPlayers()) {
                if (player.getUser().getName().equals(user.getName())) {
                    session.getSession().setAttribute("player", player);
                    break;
                }
            }
        }
        addToSession("user", user, session);
    }
}
