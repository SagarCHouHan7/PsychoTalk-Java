package com.psychotalk.service;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.psychotalk.model.JwtUtil;
import com.psychotalk.model.Users;
import com.psychotalk.repository.AnswerRepo;
import com.psychotalk.repository.QuestionRepo;
import com.psychotalk.repository.UserRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseCookie;
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

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    AnswerRepo answerRepo;
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
            return  getUserJson(dbUser);
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

    public String getUserById(int userId) {
        Users dbUser = userRepo.getUserById(userId);
        if(dbUser != null){
            return  getUserJson(dbUser);
        }else{
            return "{\"status\":\"failure\",\"errorMsg\":\"Incorrect Username or password\"}";
        }

    }

    private String getUserJson(Users dbUser){
        JSONObject json = new JSONObject();
        JSONObject userJson = new JSONObject();
        userJson.put("id",dbUser.getId());
        userJson.put("username",dbUser.getUsername());
        userJson.put("email",dbUser.getEmail());
        userJson.put("phone",dbUser.getPhone());
        userJson.put("age" , dbUser.getAge());
        userJson.put("gender" , dbUser.getGender());
        userJson.put("questionsCount", questionRepo.getQuestionsCountByUserId(dbUser.getId()));
        userJson.put("answersCount" , answerRepo.getAnswersCountByUserId(dbUser.getId()));
        String token = JwtUtil.generateToken(dbUser.getEmail());
        json.put("token" , token);
        json.put("status" , "success");
        json.put("user", userJson);

        return  json.toString();
    }
}


//            ResponseCookie cookie = ResponseCookie.from("token", token)
//                    .httpOnly(true)   // JavaScript can't access
//                    .secure(false)    //  set true in production (HTTPS)
//                    .path("/")        // cookie available for entire API
//                    .sameSite("Lax")  // prevent CSRF
//                    .maxAge(24 * 60 * 60) // 1 day expiry
//                    .build();
//
//            // 3️⃣ Add cookie to response
//            response.addHeader("Set-Cookie", cookie.toString());

