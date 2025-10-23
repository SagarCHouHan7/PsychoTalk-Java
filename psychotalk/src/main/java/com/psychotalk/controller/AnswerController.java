package com.psychotalk.controller;

import com.psychotalk.model.Answers;
import com.psychotalk.service.AnswerService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/answer")
public class AnswerController {
   @Autowired
    AnswerService answerService;

    @PostMapping("/postAnswerByQuestionId/{id}")
    public JSONObject postAnswer(@PathVariable("id") Integer questionId , @RequestBody Answers answer){
        return answerService.postAnswerByQuestionId(questionId,answer);
    }

    @GetMapping("/getAllAnswersByQuestionId/{questionId}")
    public List<Answers> getAllAnswers(@PathVariable("questionId") Integer questionId){

        return answerService.getAllAnswersByQuestionId(questionId);
    }

    @DeleteMapping("/deleteAnswerByAnswerId/{id}")
    public boolean deleteAnswerById(@PathVariable("id") Integer answerId){
        return answerService.deleteAnswerById(answerId);
    }

    @PutMapping("/updateAnswerById")
    public Answers updateAnswerById(@RequestBody Answers answer){
        return answerService.updateAnswerById(answer);
    }

}
