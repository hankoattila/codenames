package com.codenames.attilahanko.repository;

import com.codenames.attilahanko.model.player.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
    UserAccount findByName(String name);
}
