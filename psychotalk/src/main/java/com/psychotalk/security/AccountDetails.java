package com.psychotalk.security;

import com.psychotalk.model.account.Account;
import com.psychotalk.model.account.Admin;
import com.psychotalk.model.account.Expert;
import com.psychotalk.model.account.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AccountDetails implements UserDetails {

    private Account account;

    public AccountDetails(Account account){
        this.account = account;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(account instanceof User) {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }else if(account instanceof Expert){
            return List.of(new SimpleGrantedAuthority("ROLE_EXPERT"));
        }else if(account instanceof Admin){
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }
        return List.of();
    }

    @Override
    public String getPassword() {
        return account.getPassword();
    }

    @Override
    public String getUsername() {
        return account.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
