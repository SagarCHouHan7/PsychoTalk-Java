package com.psychotalk.service;

import com.psychotalk.model.Experts;
import com.psychotalk.model.JwtUtil;
import com.psychotalk.repository.ExpertRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ExpertService {

    @Autowired
    ExpertRepo expertRepo;
    public String expertRegistration(Experts expert) {
        Experts dbExpert = expertRepo.findExpertByEmailAndPassword(expert.getEmail() , expert.getPassword());
        JSONObject json = new JSONObject();
        if(dbExpert==null){
            Date date = getDate();
            expert.setJoiningDate(date);
            expertRepo.save(expert);
            return  "{\"status\":\"success\"}";
        }
        else{
            System.out.println(expert.getQualification());
            return "{\"status\":\"failure\":\"Already Registered\"}";
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

    public JSONObject getExpertByEmailAndPassword(Experts expert) {
        Experts dbExpert = expertRepo.findExpertByEmailAndPassword(expert.getEmail() , expert.getPassword());
        JSONObject json = new JSONObject();
        if(dbExpert != null){
            String token = JwtUtil.generateToken(dbExpert.getEmail());
            json.put("token" , token);
            Experts loggedInExpert = expertRepo.getExpertByExpertId(dbExpert.getId());
            json.put("expert" , loggedInExpert);
            json.put("status" , "success");
        }
        else{

            json.put("status" , "failure");
            json.put("errorMsg" , "Incorrect UserName or Password");
        }

        return json;

    }

    public List<Experts> getAllExpertsProfile() {
        return expertRepo.getAllExpertsProfile();
    }

    public Experts getExpertByExpertId(Integer expertId) {
        return expertRepo.getExpertByExpertId(expertId);
    }
}
