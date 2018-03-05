package com.codenames.attilahanko.repository;

import com.codenames.attilahanko.model.player.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByName(String name);

    User findByNameAndGameId(String userName, Long id);
}
