package com.psychotalk.service;

import com.psychotalk.model.Experts;
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
            json.put("email" , dbExpert.getEmail());
            json.put("fullName" , dbExpert.getFullName());
            json.put("about" , dbExpert.getAbout());
            json.put("gender" , dbExpert.getGender());
            json.put("age" , dbExpert.getAge());
            json.put("qualification" , dbExpert.getQualification());
            json.put("experience" , dbExpert.getExperience());
            json.put("fees" , dbExpert.getFees());
            json.put("address" , dbExpert.getAddress());
            json.put("rating" , dbExpert.getRating());

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
