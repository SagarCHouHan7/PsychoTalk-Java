package com.psychotalk.service.Utils;

import com.psychotalk.exception.customExceptions.ExpertNotFoundException;
import com.psychotalk.exception.customExceptions.UserNotFoundException;
import com.psychotalk.model.account.Account;
import com.psychotalk.model.account.Expert;
import com.psychotalk.model.account.User;
import com.psychotalk.repository.AccountRepo;
import com.psychotalk.repository.ExpertRepo;
import com.psychotalk.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CurrentRoleService {

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ExpertRepo expertRepo;
    @Autowired
    private AccountRepo accountRepo;

    public User getCurrentUser(){
        return userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(UserNotFoundException::new);
    }

    public Expert getCurrentExpert(){
        return expertRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(ExpertNotFoundException::new);
    }

    public Account getCurrentAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account =  accountRepo.findByUsername(username);
        if(account == null) throw new RuntimeException("username not found");
        return account;
    }
}
