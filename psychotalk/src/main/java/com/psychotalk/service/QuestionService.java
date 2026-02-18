package com.psychotalk.service;

import com.psychotalk.dto.PageResponseDto;
import com.psychotalk.dto.questionDto.CreateQuestionDto;
import com.psychotalk.dto.questionDto.QuestionResponseDto;
import com.psychotalk.model.Question;
import com.psychotalk.model.account.User;
import com.psychotalk.repository.QuestionRepo;
import com.psychotalk.repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;
    @Autowired
    UserRepo userRepo;

    public QuestionResponseDto createQuestion(CreateQuestionDto questionDto){
        User user = getCurrentUser();
        if(questionRepo.existsQuestionByStatementAndUser(questionDto.getQuestion() , user)){
            throw new RuntimeException("Question already exists");
        }
        Question question = new Question();
        question.setQuestion(questionDto.getQuestion());
        question.setAnswerCount(0);
        question.setLikes(0);
        question.setCreatedTime(LocalDateTime.now());
        question.setModifiedTime(LocalDateTime.now());
        question.setUser(user);
        question.setUsername(user.getUsername());
        return mapQuestionResponseDto(questionRepo.save(question));
    }

    public QuestionResponseDto getQuestionById(long id){
        Question question =  questionRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Not Found"));
        return mapQuestionResponseDto(question);
    }

    public PageResponseDto<QuestionResponseDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page , size , Sort.by("createdTime").descending());
        Page<Question> questionPage = questionRepo.findAll(pageable);
        List<QuestionResponseDto> content =
                questionPage.getContent().stream()
                        .map(this::mapQuestionResponseDto)
                        .toList();
        return mapToPageResponse(questionPage , content);
    }

    public Object getMyQuestions(int page, int size) {
        User user = getCurrentUser();
        Pageable pageable = PageRequest.of(page , size , Sort.by("createdTime").descending());

        Page<Question> questionPage = questionRepo.findByUser(user.getId() , pageable);
        List<QuestionResponseDto> content =
                questionPage.getContent().stream()
                        .map(this::mapQuestionResponseDto)
                        .toList();

        return mapToPageResponse(questionPage , content);

    }

    @Transactional
    public QuestionResponseDto updateQuestionById(long id, String questionStatement) {
        Question question = questionRepo.findById(id).orElseThrow(()->new RuntimeException("question doesn't exists"));
        User user = getCurrentUser();
        if(!question.getUser().getId().equals(user.getId())) throw new RuntimeException("Anonymous update not allowed");
        question.setModifiedTime(LocalDateTime.now());
        question.setQuestion(questionStatement);
        return mapQuestionResponseDto(questionRepo.save(question));
    }

    @Transactional
    public void deleteById(Long id) {
        Question question = questionRepo.findById(id).orElseThrow(()-> new RuntimeException("question doesn't exists"));
        if(! question.getUser().getId().equals(getCurrentUser().getId())) throw new RuntimeException("Anonymous deletion not allowed");
        questionRepo.delete(question);
    }

    private QuestionResponseDto mapQuestionResponseDto(Question question){
        QuestionResponseDto q = new QuestionResponseDto();
        q.setId(question.getId());
        q.setAnswerCount(question.getAnswerCount());
        q.setLikes(question.getLikes());
        q.setModifiedTime(question.getModifiedTime());
        q.setCreatedTime(question.getCreatedTime());
        q.setUsername(question.getUsername());
        q.setQuestion(question.getQuestion());
        q.setUserId(question.getUser().getId());
        return q;
    }

    private PageResponseDto<QuestionResponseDto> mapToPageResponse(Page<Question> page , List<QuestionResponseDto> content){
            PageResponseDto<QuestionResponseDto> dto = new PageResponseDto<>();
            dto.setContent(content);
            dto.setPage(page.getNumber());
            dto.setTotalPages(page.getTotalPages());
            dto.setSize(page.getSize());
            dto.setLast(page.isLast());
            dto.setTotalElements(page.getTotalElements());
            return dto;
    }

    private User getCurrentUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return userRepo.findByUsername(username).orElseThrow(()-> new RuntimeException("user not found"));
    }

}
