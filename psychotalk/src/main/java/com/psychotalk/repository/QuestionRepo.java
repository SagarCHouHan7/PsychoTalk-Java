package com.psychotalk.repository;

import com.psychotalk.model.Questions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Questions , Integer> {

    @Query("SELECT q FROM Questions q WHERE q.question=:question AND q.userId=:userId")
    Questions findByQuestionStatementWithUserId(@Param("question")String question , @Param("userId") int userId);

    @Query("SELECT q FROM Questions q WHERE q.id=:id")
    Questions findQuestionById(@Param("id")int id);

    @Query("SELECT q FROM Questions q WHERE q.userId=:userId")
    List<Questions> getQuestionsByUserId(@Param("userId") int userId );

    @Query("SELECT count(*) FROM Questions q WHERE q.userId=:userId")
    int getQuestionsCountByUserId(@Param("userId") int userId );
}
