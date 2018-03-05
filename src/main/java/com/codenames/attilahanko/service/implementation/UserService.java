package com.codenames.attilahanko.service.implementation;

import com.codenames.attilahanko.model.player.User;
import com.codenames.attilahanko.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    public User getUserByUserNameAndGameId(String userName, Long id) {
        return userRepository.findByNameAndGameId(userName, id);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
