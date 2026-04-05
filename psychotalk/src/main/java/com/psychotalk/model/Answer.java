package com.psychotalk.model;

import com.psychotalk.model.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "answer" , length = 40000)
    private String answer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id" , nullable = false)
    Question question;

    @ManyToOne
    @JoinColumn(name = "answered_by_account_id" , nullable = true)
    private Account answeredBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long likes;
}
