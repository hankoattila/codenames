package com.codenames.attilahanko.repository;

import com.codenames.attilahanko.model.game.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

}
