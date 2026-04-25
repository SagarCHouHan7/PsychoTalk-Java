package com.psychotalk.repository;

import com.psychotalk.model.Notification;
import com.psychotalk.model.account.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepo extends JpaRepository<Notification, Long> {

    @Query("SELECT n FROM Notification n WHERE n.account=:account")
    List<Notification> findByAccount(@Param("account") Account account);
}
