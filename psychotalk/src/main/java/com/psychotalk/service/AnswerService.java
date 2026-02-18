package com.psychotalk.service;

import com.psychotalk.dto.AnswerDto.AnswerResponseDto;
import com.psychotalk.dto.AnswerDto.CreateAnswerDto;
import com.psychotalk.dto.PageResponseDto;
import com.psychotalk.model.Answer;
import com.psychotalk.model.Question;
import com.psychotalk.model.account.Account;
import com.psychotalk.model.account.Expert;
import com.psychotalk.model.account.User;
import com.psychotalk.repository.AccountRepo;
import com.psychotalk.repository.AnswerRepo;
import com.psychotalk.repository.QuestionRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private AnswerRepo answerRepo;
    @Autowired
    private QuestionRepo questionRepo;

    public AnswerResponseDto postAnswer(CreateAnswerDto answer) {
        Account account = getCurrentAccount();
        Question question = questionRepo.findById(answer.getQuestionId()).orElseThrow(()->new RuntimeException("Question doesn't exist"));
        if( answer.getAnswer() == null || answer.getAnswer().trim().isEmpty()) throw new RuntimeException("Empty post not allowed");

        Answer newAnswer = new Answer();
        newAnswer.setAnswer(answer.getAnswer());
        newAnswer.setAnsweredBy(account);
        newAnswer.setQuestion(question);
        newAnswer.setCreatedAt(LocalDateTime.now());
        newAnswer.setUpdatedAt(LocalDateTime.now());
        newAnswer.setLikes(0);

        Answer save = answerRepo.save(newAnswer);
        question.setAnswerCount(question.getAnswerCount()+1);
        questionRepo.save(question);

        return mapToAnswerResponseDto(save);

    }

    public PageResponseDto<AnswerResponseDto> getAllAnswersByQuestionId(Long id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("likes").descending());
        Page<Answer> answerPage = answerRepo.findAllAnswersByQuestionId(id , pageable);
        List<AnswerResponseDto> content = answerPage.getContent()
                .stream()
                .map(this::mapToAnswerResponseDto)
                .toList();
        return mapToPageResponseDto(answerPage , content);
    }

    @Transactional
    public AnswerResponseDto updateMyAnswer(long id, CreateAnswerDto answer) {
        Answer answer1 = answerRepo.findById(id).orElseThrow(()->new RuntimeException("answer doesn't exist"));
        Account account = getCurrentAccount();
        if(!answer1.getAnsweredBy().getUsername().equals(account.getUsername())) throw new RuntimeException("Answer doesn't belongs to current user");
        answer1.setUpdatedAt(LocalDateTime.now());
        answer1.setAnswer(answer.getAnswer());
        return mapToAnswerResponseDto(answerRepo.save(answer1));
    }

    @Transactional
    public void deleteAnswerById(Long id) {
        Account account = getCurrentAccount();
        Answer answer = answerRepo.findById(id).orElseThrow(()-> new RuntimeException("answer doesn't exist"));
        Question question = questionRepo.findById(answer.getQuestion().getId()).orElseThrow(()-> new RuntimeException("parent doesn't exists"));
        if(!answer.getAnsweredBy().getUsername().equals(account.getUsername())) throw new RuntimeException("Answer doesn't belongs to current user");
        question.setAnswerCount(question.getAnswerCount()-1);
        questionRepo.save(question);
        answerRepo.delete(answer);
    }

    private PageResponseDto<AnswerResponseDto> mapToPageResponseDto(Page<Answer> answerPage , List<AnswerResponseDto> content){
        PageResponseDto<AnswerResponseDto> dto = new PageResponseDto<>();
        dto.setPage(answerPage.getNumber());
        dto.setTotalPages(answerPage.getTotalPages());
        dto.setContent(content);
        dto.setSize(answerPage.getSize());
        dto.setLast(answerPage.isLast());
        dto.setTotalElements(answerPage.getTotalElements());
        return dto;
    }

    private AnswerResponseDto mapToAnswerResponseDto(Answer answer){
        AnswerResponseDto dto = new AnswerResponseDto();
        Account answeredBy = answer.getAnsweredBy();
        if(answeredBy instanceof User) dto.setRole("USER");
        else if(answeredBy instanceof Expert) dto.setRole("EXPERT");
        dto.setAccountId(answeredBy.getId());
        dto.setDisplayName(answeredBy.getUsername());
        dto.setId(answer.getId());
        dto.setLikes(answer.getLikes());
        dto.setCreatedAt(answer.getCreatedAt());
        dto.setUpdatedAt(answer.getUpdatedAt());
        dto.setAnswer(answer.getAnswer().trim());
        dto.setQuestionId(answer.getQuestion().getId());
        return  dto;
    }

    private Account getCurrentAccount(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Account account =  accountRepo.findByUsername(username);
        if(account == null) throw new RuntimeException("username not found");
        return account;
    }

}

