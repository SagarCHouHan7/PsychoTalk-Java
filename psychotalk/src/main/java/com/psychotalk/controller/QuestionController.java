package com.psychotalk.controller;

import com.psychotalk.dto.questionDto.CreateQuestionDto;
import com.psychotalk.dto.questionDto.QuestionResponseDto;
import com.psychotalk.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user/question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @PostMapping("/post")
    private ResponseEntity<?> createQuestion(@RequestBody CreateQuestionDto question){
        QuestionResponseDto question1 = questionService.createQuestion(question);
        if(question1 != null)
            return new ResponseEntity<>(question1 , HttpStatus.CREATED);
        else
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getMy")
    private ResponseEntity<?> getMyQuestions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(questionService.getMyQuestions(page , size));
    }

    @PutMapping("/update/{id}")
    private ResponseEntity<QuestionResponseDto> updateQuestionById(@PathVariable("id") long id , @RequestBody CreateQuestionDto question){
        return ResponseEntity.ok(questionService.updateQuestionById(id , question.getQuestion()));
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<Void> deleteById(@PathVariable("id") Long id){
        questionService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
