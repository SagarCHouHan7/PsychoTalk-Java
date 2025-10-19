package com.psychotalk.service;

import com.psychotalk.model.Answers;
import com.psychotalk.model.Questions;
import com.psychotalk.model.Users;
import com.psychotalk.repository.AnswerRepo;
import com.psychotalk.repository.QuestionRepo;
import com.psychotalk.repository.UserRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class AnswerService {

    @Autowired
    AnswerRepo answerRepo;

    @Autowired
    QuestionRepo questionRepo;

    @Autowired
    UserRepo userRepo;
    public JSONObject postAnswerByQuestionId(Integer questionId, Answers answer) {
        JSONObject json = new JSONObject();
        Date date = getDate();
        Questions question = questionRepo.findById(questionId)
                .orElseThrow(() -> new RuntimeException("Question not found with ID: " ));

        // ✅ 2. Fetch the managed User
        Users user = userRepo.findById(answer.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: "));

        // ✅ 3. Set the managed entities
        answer.setQuestion(question);
        answer.setUser(user);
        answer.setCreatedTime(date);
        answer.setModifiedTime(date);
        answer = answerRepo.save(answer);
        try {
            json.put("status", "success");
            json.put("answer", answer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return json;
    }


    public List<Answers> getAllAnswersByQuestionId(Integer questionId) {

        return answerRepo.getAllAnswersByQuestionId(questionId);
    }

    public boolean deleteAnswerById(Integer answerId) {
         answerRepo.deleteById(answerId);
         return true;
    }

    public Answers updateAnswerById(Answers answer) {
        Answers dbAnswer = answerRepo.findAnswerStatementByAnswerId(answer.getId());
        if(dbAnswer != null){
            dbAnswer.setAnswer(answer.getAnswer());
            Date date = getDate();
            dbAnswer.setModifiedTime(date);
            answerRepo.save(dbAnswer);
            return dbAnswer;
        }
        else{
            return null;
        }
    }

    private Date getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(new Date());
        try {
            return formatter.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}

