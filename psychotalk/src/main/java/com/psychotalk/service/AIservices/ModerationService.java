package com.psychotalk.service.AIservices;

import com.psychotalk.model.Answer;
import com.psychotalk.model.Notification;
import com.psychotalk.model.Question;
import com.psychotalk.model.account.Account;
import com.psychotalk.model.enums.NotificationStatus;
import com.psychotalk.repository.AccountRepo;
import com.psychotalk.repository.AnswerRepo;
import com.psychotalk.repository.QuestionRepo;
import com.psychotalk.service.NotificationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ModerationService {

    @Autowired
    private ChatClient chatClient;
    @Autowired
    private AnswerRepo answerRepo;
    @Autowired
    private QuestionRepo questionRepo;
    @Autowired
    private AccountRepo accountRepo;
    @Autowired
    private NotificationService notificationService;

    @Value("classpath:/prompts/community-support-policy-system-check.st")
    private Resource systemMessage;

    @Async("AITaskExecutor")
    public void checkViolationPolicy(Answer answer, Question question, Account account){

        System.out.println("async execution");
        try{
            String isSafe = chatClient.prompt()
                    .user( u -> u.text(systemMessage)
                            .param("question" , question.getQuestion())
                            .param("answer", answer.getAnswer()))
                    .call()
                    .content();


            System.out.println(isSafe);
            assert isSafe != null;
            if(isSafe.trim().equalsIgnoreCase("true"))
            {
                addNotification("Your response sent successfully", account);
                return;
            }
            answerRepo.deleteById(answer.getId());
            addNotification("Your response is deleted because it was violating the app policies", account);


            question.setAnswerCount(question.getAnswerCount()-1);
            questionRepo.save(question);


        }catch (Exception e){
            e.printStackTrace();
            answerRepo.deleteById(answer.getId());
        }


    }

    private void addNotification(String note, Account account){
        Notification notification = new Notification();
        notification.setAccount(account);
        notification.setTime(LocalDateTime.now());
        notification.setNote(note);
        notification.setStatus(NotificationStatus.UNREAD);
        notificationService.addNotification(notification);
    }
}
