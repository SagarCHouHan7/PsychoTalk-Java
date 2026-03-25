package com.psychotalk.security;


import com.psychotalk.exception.customExceptions.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static String getCurrentUsername(){
        Authentication auth = getAuthentication();
        if(auth == null) throw new UserNotFoundException();
        return auth.getName();
    }


}
