package com.psychotalk.repository;

import com.psychotalk.model.account.Account;
import com.psychotalk.model.account.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    @Query("SELECT a FROM Account a WHERE a.username = :username")
    Optional<User> findByUsername(@Param("username") String username);

}
