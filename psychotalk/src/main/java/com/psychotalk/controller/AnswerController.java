package com.psychotalk.controller;

import com.psychotalk.dto.AnswerDto.AnswerResponseDto;
import com.psychotalk.dto.AnswerDto.CreateAnswerDto;
import com.psychotalk.dto.PageResponseDto;
import com.psychotalk.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/answer")
public class AnswerController {
   @Autowired
    private AnswerService answerService;

    @PostMapping
    public ResponseEntity<AnswerResponseDto> postAnswer(@RequestBody CreateAnswerDto answer){
        return new ResponseEntity<>(answerService.postAnswer(answer) ,HttpStatus.CREATED);
    }

    @GetMapping("/getAllAnswersByQuestionId/{id}")
    public ResponseEntity<PageResponseDto<AnswerResponseDto>>  getAllAnswers
            (@PathVariable("id") Long id,
             @RequestParam(defaultValue = "0") int page,
             @RequestParam(defaultValue = "25") int size
    ){
        return ResponseEntity.ok(answerService.getAllAnswersByQuestionId(id , page, size));
    }

    @DeleteMapping("/deleteAnswer/{id}")
    public ResponseEntity<Void> deleteAnswerById(@PathVariable("id") Long id){
        answerService.deleteAnswerById(id);
        return ResponseEntity.noContent().build();

    }

    @PutMapping("/updateAnswer/{id}")
    public ResponseEntity<AnswerResponseDto> updateAnswerById(@PathVariable("id") long id , @RequestBody CreateAnswerDto answer){
        return ResponseEntity.ok(answerService.updateMyAnswer(id , answer));
    }

}
