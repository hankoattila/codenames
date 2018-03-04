package com.codenames.attilahanko.repository;

import com.codenames.attilahanko.model.Game;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    Game findByName(String name);

}
