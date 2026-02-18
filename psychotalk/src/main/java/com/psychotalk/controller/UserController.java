//package com.psychotalk.controller;
//
//import com.psychotalk.model.Users;
//import com.psychotalk.service.UsersService;
//import org.json.simple.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//
//@RestController()
//@RequestMapping("User")
//public class UserController {
//
//    @Autowired
//    UsersService usersService;
//
//    @PostMapping("/register")
//    private String userRegistration(@RequestBody Users user) {
//        return usersService.userRegistration(user);
//    }
//
//    @PostMapping("/login")
//    private String userLogin(@RequestBody Users user){
//        return usersService.userLogin(user);
//    }
//
//    @GetMapping("/getUserById/{id}")
//    private String userLogin(@PathVariable("id") int userId){
//        return usersService.getUserById(userId);
//    }
//
//    @PutMapping("/updateUser")
//    private Users updateUser(@RequestBody Users user){
//        return usersService.updateUser(user);
//    }
//}
