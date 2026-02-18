package com.psychotalk.model;

import com.psychotalk.model.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "answer" , length = 40000)
    private String answer;

    @ManyToOne
    @JoinColumn(name = "question_id" , nullable = false)
    Question question;

    @ManyToOne
    @JoinColumn(name = "answered_by_account_id" , nullable = false)
    private Account answeredBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long likes;
}
