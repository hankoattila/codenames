package com.codenames.attilahanko.service.implementation;

import com.codenames.attilahanko.event.EventPublisher;
import com.codenames.attilahanko.event.player.CardSelected;
import com.codenames.attilahanko.model.game.Game;
import com.codenames.attilahanko.model.game.Team;
import com.codenames.attilahanko.model.player.Player;
import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.service.GameService;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SelectCardService {

    private GameService gameService;
    private PlayerService playerService;
    private EventPublisher publisher;

    public SelectCardService(GameService gameService, PlayerService playerService, EventPublisher publisher) {
        this.gameService = gameService;
        this.playerService = playerService;
        this.publisher = publisher;
    }

    public void handleSelectCard(String gameName, User user, int selectedCardIndex) {
        Game game = gameService.findByName(gameName);
        Player player = playerService.getPlayerByUserId(user.getId());
        if (player != null) {
            Team team = player.getTeam();
            player.setSelected(selectedCardIndex);
            String color = "";
            if (team.isDecision()) {
                game.addFipped(selectedCardIndex);
                color = game.getRoles().get(selectedCardIndex);
            }
            List<Integer> selectedCards = new ArrayList<>();
            for (Player player1 : team.getPlayers()) {
                if (player1.getSelected() != null) {
                    selectedCards.add(player1.getSelected());
                }
            }
            if (!color.equals("")) {
                if (!color.equals(team.getColor())){
                    game.nextTeam();
                }
            }
            publisher.publishEvent(new CardSelected(selectedCards, color, true, game.getCurrentTeamName()));

            gameService.save(game);

        }
    }

    public void getCurrentBoard(Game game) {
        List<Integer> selected = new ArrayList<>();
        Set<Integer> flipped = game.getFlipped();
        Map<Integer, String> colors = new HashMap<>();
        for (int i : flipped) {
            colors.put(i, game.getRoles().get(i));
        }
    }
}
