package com.psychotalk.service.publicService;

import com.psychotalk.dto.AccountDto.LoginRequest;
import com.psychotalk.dto.AccountDto.RegisterResponse;
import com.psychotalk.model.account.*;
import com.psychotalk.repository.AccountRepo;
import com.psychotalk.repository.ExpertRepo;
import com.psychotalk.repository.UserRepo;
import com.psychotalk.security.AccountDetailsService;
import com.psychotalk.security.jwt.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;


@Service
public class AccountService {


    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ExpertRepo expertRepo;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AccountDetailsService accountDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisterResponse registerUser(User user) {
        Account account = accountRepo.findByUsername(user.getUsername().trim());
        if(account != null) return null;
        user.setUsername(user.getUsername().trim());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepo.save(user);
        return new RegisterResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                "USER"
        );
    }

    public RegisterResponse registerExpert(Expert expert) {
        Account account = accountRepo.findByUsername(expert.getUsername().trim());
        if(account != null) return null;
        expert.setUsername(expert.getUsername().trim());
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setJoiningDate(LocalDate.now());
        Expert savedExpert =  expertRepo.save(expert);
        return new RegisterResponse(
                savedExpert.getId(),
                savedExpert.getUsername(),
                "EXPERT"
        );
    }

    public ResponseEntity<?> login(LoginRequest req) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        req.getUsername(),
                        req.getPassword()
                )
        );
        UserDetails userDetails = accountDetailsService.loadUserByUsername(req.getUsername());
        String token = jwtUtil.generateToken(userDetails);
        Map<String , Object> response = new HashMap<>();
        response.put("token" , token);
        response.put("username" , userDetails.getUsername());
        Account account = accountRepo.findByUsername(req.getUsername());
        if(account instanceof User) response.put("role" , "USER");
        else if(account instanceof Expert) response.put("role" , "EXPERT");
        else if (account instanceof Admin) response.put("role" , "ADMIN");

        return ResponseEntity.ok(response);
    }
}
