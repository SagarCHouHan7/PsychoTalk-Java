package com.psychotalk.service;

import com.psychotalk.model.Questions;
import com.psychotalk.repository.QuestionRepo;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    QuestionRepo questionRepo;

    public JSONObject createQuestion(Questions question){

        Questions dbQuestion = questionRepo.findByQuestionStatementWithUserId(question.getQuestion(), question.getUserId());
        JSONObject json = new JSONObject();
        if(dbQuestion == null) {
            Date date = getDate();
            question.setCreatedTime(date);
            question.setModifiedTime(date);
            questionRepo.save(question);
            json.put("status","success");
            json.put("question",question);

        }else {
            json.put("status","failure");
            json.put("errorMsg","Question already exist");

        }
        return json;
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

    public Questions getQuestionById(int id){

        Questions dbQuestion =  questionRepo.findQuestionById(id);
        if(dbQuestion != null){
            return dbQuestion;
        } else {
            return new Questions();
        }

    }

    public boolean deleteById(int id) {
        try {
            questionRepo.deleteById(id);
        }catch (Exception ex){
            return false;
        }
        return true;
    }

    public List<Questions> getAllQuestions() {

        return questionRepo.findAll();
    }

    public List<Questions> getQuestionsByUserId(int userId) {

        return questionRepo.getQuestionsByUserId(userId);
    }

    public JSONObject updateQuestionById(Questions question) {
        try {
            Questions dbQuestion = questionRepo.findQuestionById(question.getId());
            JSONObject json = new JSONObject();
            if(dbQuestion!=null){
                dbQuestion.setQuestion(question.getQuestion());
                Date date = getDate();
                dbQuestion.setModifiedTime(date);
                questionRepo.save(dbQuestion);
                json.put("status" , "success");
                json.put("question" , dbQuestion);
                return json;
            }else{
                json.put("status" , "failure");
                json.put("errorMsg" , "Could not Update");
                return json;
            }
        }catch (Exception ex){
            return null;
        }
    }


    public int getQuestionsCountByUserId(int userId){
        return this.questionRepo.getQuestionsCountByUserId(userId);
    }


}
