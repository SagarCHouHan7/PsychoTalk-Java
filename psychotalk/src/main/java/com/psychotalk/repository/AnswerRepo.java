package com.psychotalk.repository;

import com.psychotalk.model.Answers;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerRepo extends JpaRepository<Answers , Integer> {

    @Query("SELECT a FROM Answers a WHERE a.question.id =:questionId")
    List<Answers> getAllAnswersByQuestionId(@Param("questionId") Integer questionId);

    @Query(" SELECT a FROM Answers a WHERE a.id=:id ")
    Answers findAnswerStatementByAnswerId(@Param("id") int id);

    @Query("SELECT count(*) FROM Answers a WHERE a.user.id=:userId")
    int getAnswersCountByUserId(@Param("userId") int userId );
//    @Modifying
//    @Transactional
//    @Query("DELETE  FROM Answers a WHERE a.user.id=:userId AND a.question.id=:questionId")
//    int deleteAnswerByUserIdAndQuestionId(@Param("userId") Integer userId, @Param("questionId") Integer questionId);

}
