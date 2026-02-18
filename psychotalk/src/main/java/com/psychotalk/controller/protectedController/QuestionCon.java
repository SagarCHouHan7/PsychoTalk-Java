package com.psychotalk.controller.protectedController;

import com.psychotalk.dto.questionDto.QuestionResponseDto;
import com.psychotalk.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/question")
public class QuestionCon {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/getById/{id}")
    private ResponseEntity<QuestionResponseDto> getQuestionById(@PathVariable Long id){
        return new ResponseEntity<>(questionService.getQuestionById(id) , HttpStatus.OK);
    }

    @GetMapping("/getAll")
    private ResponseEntity<?> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ){
        return ResponseEntity.ok(questionService.getAll(page , size));
    }
}
