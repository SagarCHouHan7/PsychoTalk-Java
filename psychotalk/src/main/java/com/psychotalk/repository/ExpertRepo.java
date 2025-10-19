package com.psychotalk.repository;

import com.psychotalk.model.Experts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpertRepo extends JpaRepository<Experts , Integer> {

    @Query("SELECT e FROM Experts e WHERE e.email=:email AND e.password=:password")
    Experts findExpertByEmailAndPassword(@Param("email") String email , @Param("password") String password);

    @Query(name="findExpertDTOByEmail")
    List<Experts> getAllExpertsProfile();


    @Query(name="findExpertById")
    Experts getExpertByExpertId(@Param("id") Integer expertId);
}
