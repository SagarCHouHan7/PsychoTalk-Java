package com.psychotalk.repository;

import com.psychotalk.model.Question;
import com.psychotalk.model.account.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepo extends JpaRepository<Question , Long> {


    @Query( "SELECT CASE" +
            " WHEN count(*) > 0 THEN true" +
            " ELSE false"+
            " END"+
            " FROM Question q WHERE q.question=:question AND q.user=:user")
    boolean existsQuestionByStatementAndUser(@Param("question") String question, @Param("user") User user);

    @Query("SELECT q FROM Question q WHERE q.user.id=:id " +
            "ORDER BY createdTime DESC")
    Page<Question> findByUser(@Param("id") Long id, Pageable pageable);


//
//    @Query("SELECT q FROM Question q WHERE q.id=:id")
//    Question findQuestionById(@Param("id")Long id);
//
//    @Query("SELECT q FROM Question q WHERE q.user.id=:userId")
//    List<Question> getQuestionsByUserId(@Param("userId") Long userId );
//
//    @Query("SELECT count(*) FROM Question q WHERE q.user.id=:userId")
//    int getQuestionsCountByUserId(@Param("userId") Long user );
}
