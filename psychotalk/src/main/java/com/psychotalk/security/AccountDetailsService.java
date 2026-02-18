package com.psychotalk.security;

import com.psychotalk.model.account.Account;
import com.psychotalk.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class AccountDetailsService implements UserDetailsService {

    @Autowired
    AccountRepo accountRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account user = accountRepo.findByUsername(username);
        if(user != null){
            return new AccountDetails(user);
        }

       throw new UsernameNotFoundException("username not found");

    }
}
