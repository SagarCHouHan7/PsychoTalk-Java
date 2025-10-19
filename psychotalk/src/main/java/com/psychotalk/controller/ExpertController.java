package com.psychotalk.controller;

import com.psychotalk.model.Experts;
import com.psychotalk.service.ExpertService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Expert")
public class ExpertController {

    @Autowired
    ExpertService expertService;

    @PostMapping("/register")
    public String expertRegistration(@RequestBody Experts expert){
        return expertService.expertRegistration(expert);
    }

    @PostMapping("/login")
    public JSONObject expertLogin(@RequestBody Experts expert){
        return expertService.getExpertByEmailAndPassword(expert);
    }

    @GetMapping("/getAllExperts")
    public List<Experts> getAllExperts(){
        return expertService.getAllExpertsProfile();
    }

    @GetMapping("/getExpertByExpertId/{id}")
    public Experts getExpertByExpertId(@PathVariable("id") Integer expertId){
        return expertService.getExpertByExpertId(expertId);
    }
}
