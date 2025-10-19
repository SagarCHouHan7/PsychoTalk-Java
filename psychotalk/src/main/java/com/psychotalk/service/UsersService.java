package com.psychotalk.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.psychotalk.model.Users;
import com.psychotalk.repository.UserRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import java.util.logging.SimpleFormatter;

@Service
public class UsersService {

    @Autowired
    UserRepo userRepo;

    public String userRegistration(Users user){
        System.out.println("UsersService userRegistration "+user);
        //email and username already exists error
        Users dbUser = userRepo.findByEmail(user.getEmail());
        Users dbUserName = userRepo.findByUserName(user.getUsername());
        if(dbUser == null && dbUserName == null){
            System.out.println("UserEmail "+user.getEmail());
            System.out.println("UserName "+user.getUsername());
            Date date = getDate();
            user.setCreatedTime(date);
            user.setModifiedTime(date);
            userRepo.save(user);
            return  "{\"status\":\"success\"}";

        }else{
            return "{\"status\":\"failure\",\"errorMsg\":\"Email or UserName already exists.\"}";
        }
    }

    private Date getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(new Date());
        try {
            return formatter.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }



    public String userLogin(Users user){
        Users dbUser = userRepo.findByUserEmailAndUserPassword(user.getEmail() , user.getPassword());
        if(dbUser != null){
            JSONObject json = new JSONObject();
            json.put("id",dbUser.getId());
            json.put("username",dbUser.getUsername());
            json.put("email",dbUser.getEmail());
            json.put("phone",dbUser.getPhone());
            json.put("age" , dbUser.getAge());
            json.put("gender" , dbUser.getGender());
            return  "{\"status\":\"success\", \"user\" : "+json+"}";

        }else{
            return "{\"status\":\"failure\",\"errorMsg\":\"Incorrect Username or password\"}";
        }
    }

    public Users updateUser(Users user) {
        Users dbUser = userRepo.findByUserEmailAndUserPassword(user.getEmail() , user.getPassword());

        if( dbUser.getUsername().equals(user.getUsername())) {
            Date date = getDate();
            dbUser.setModifiedTime(date);
            dbUser.setAge(user.getAge());
            dbUser.setPhone(user.getPhone());
            dbUser.setGender(user.getGender());
            userRepo.save(dbUser);
            return dbUser;
        }else{
            Users newUser =  userRepo.findByUserName(user.getUsername());
            if(newUser == null){
                Date date = getDate();
                dbUser.setModifiedTime(date);
                dbUser.setAge(user.getAge());
                dbUser.setPhone(user.getPhone());
                dbUser.setGender(user.getGender());
                dbUser.setUsername(user.getUsername());
                userRepo.save(dbUser);
                return dbUser;
            }else{
                return null;
            }
        }
    }
}
