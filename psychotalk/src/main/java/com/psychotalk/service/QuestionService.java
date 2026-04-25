package com.psychotalk.service;

import com.psychotalk.dto.PageResponseDto;
import com.psychotalk.dto.questionDto.CreateQuestionDto;
import com.psychotalk.dto.questionDto.QuestionResponseDto;
import com.psychotalk.model.Question;
import com.psychotalk.model.account.User;
import com.psychotalk.repository.QuestionRepo;
import com.psychotalk.repository.UserRepo;
import com.psychotalk.service.AIservices.AIResponseService;
import com.psychotalk.service.Utils.CurrentRoleService;
import jakarta.transaction.Transactional;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;


@Service
public class QuestionService {
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CurrentRoleService currentRoleService;
    @Autowired
    private ChatClient chatClient;
    @Autowired
    private AIResponseService aiResponseService;

    public QuestionResponseDto createQuestion(CreateQuestionDto questionDto){
        User user = currentRoleService.getCurrentUser();
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
        Question saved = questionRepo.save(question);
        // async job done here : AI response will generate and save in DB
        aiResponseService.getAIAnswerAndSaveInDB(saved);

        return mapQuestionResponseDto(saved);
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
        User user = currentRoleService.getCurrentUser();
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
        User user = currentRoleService.getCurrentUser();
        if(!question.getUser().getId().equals(user.getId())) throw new RuntimeException("Anonymous update not allowed");
        question.setModifiedTime(LocalDateTime.now());
        question.setQuestion(questionStatement);
        return mapQuestionResponseDto(questionRepo.save(question));
    }

    @Transactional
    public boolean deleteById(Long id) {
        Question question = questionRepo.findById(id).orElseThrow(()-> new RuntimeException("question doesn't exists"));
        if(! question.getUser().getId().equals(currentRoleService.getCurrentUser().getId())) throw new RuntimeException("Anonymous deletion not allowed");
        questionRepo.delete(question);
        return true;
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


}
