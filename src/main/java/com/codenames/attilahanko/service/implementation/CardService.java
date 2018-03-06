package com.codenames.attilahanko.service.implementation;

import com.codenames.attilahanko.model.game.Card;
import com.codenames.attilahanko.repository.CardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardService {

    private CardRepository cardRepository;

    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public Card findOne(Long id) {
        return cardRepository.getOne(id);
    }

    public List<Card> findAll() {
        return cardRepository.findAll();
    }
}
