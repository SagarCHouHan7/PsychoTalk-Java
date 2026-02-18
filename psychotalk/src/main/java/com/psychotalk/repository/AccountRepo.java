package com.psychotalk.repository;

import com.psychotalk.model.account.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account , Long> {

    @Query("SELECT a FROM Account a WHERE a.username = :username")
    Account findByUsername(@Param("username") String username);

    @Query("SELECT a FROM Account a WHERE a.username=:username AND a.password=:password")
    Account findByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}
