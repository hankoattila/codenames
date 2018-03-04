package com.codenames.attilahanko.repository;

import com.codenames.attilahanko.model.player.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends CrudRepository<Player,Long> {


    Player findById(Long id);
}
