package com.psychotalk.controller;

import com.psychotalk.model.Users;
import com.psychotalk.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:5174")
@RestController()
@RequestMapping("User")
public class UserController {

    @Autowired
    UsersService usersService;

    @PostMapping("/register")
    private String userRegistration(@RequestBody Users user) {
        return usersService.userRegistration(user);
    }

    @PostMapping("/login")
    private String userLogin(@RequestBody Users user){
        return usersService.userLogin(user);
    }

    @PutMapping("/updateUser")
    private Users updateUser(@RequestBody Users user){
        return usersService.updateUser(user);
    }
}
