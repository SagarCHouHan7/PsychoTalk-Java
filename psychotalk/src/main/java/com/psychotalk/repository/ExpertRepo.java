package com.psychotalk.repository;

import com.psychotalk.model.account.Expert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertRepo extends JpaRepository<Expert , Long> {
    Optional<Expert> findByUsername(String username);

    @Query("SELECT e FROM Expert e WHERE e.isVerified=true")
    Page<Expert> findAllVerifiedExpert(Pageable pageable);
}
