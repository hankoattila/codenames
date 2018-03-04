package com.codenames.attilahanko.service.implementation.useraccount;

import com.codenames.attilahanko.model.UserAccount;
import com.codenames.attilahanko.repository.UserAccountRepository;
import com.codenames.attilahanko.service.UserAccountService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserAccountServiceImpl implements UserAccountService, UserDetailsService {


    private UserAccountRepository userAccountRepository;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository) {
        this.userAccountRepository = userAccountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserAccount userAccount = userAccountRepository.findByName(username);
        if (userAccount == null) {
            throw new UsernameNotFoundException("Invalid");
        }
        return new UserAccountDetailServiceImpl(userAccount);
    }

    @Override
    public void save(UserAccount userAccount) {
        userAccountRepository.save(userAccount);
    }
}
