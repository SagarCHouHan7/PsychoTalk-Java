package com.psychotalk.controller;

import com.psychotalk.model.Questions;
import com.psychotalk.service.QuestionService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;
    @PostMapping("/createQuestion")
    private JSONObject createQuestion(@RequestBody Questions question){
        return questionService.createQuestion(question);
    }

    @GetMapping("/getQuestionById")
    private Questions getQuestionById(@RequestParam int id){
        return questionService.getQuestionById(id);

    }

    @GetMapping("/getAllQuestions")
    private List<Questions> getAllQuestions(){

        return questionService.getAllQuestions();
    }

    @GetMapping("/getQuestionByUserId")
    private List<Questions> getQuestionsByUserId(@RequestParam("id") int userId){
        return questionService.getQuestionsByUserId(userId);
    }

    @PutMapping("/updateQuestionById")
    private JSONObject updateQuestionById(@RequestBody Questions question){
        return questionService.updateQuestionById(question);
    }

    @GetMapping("/deleteById/{id}")
    private boolean deleteById(@PathVariable("id") int id){
        return questionService.deleteById(id);
    }
}
