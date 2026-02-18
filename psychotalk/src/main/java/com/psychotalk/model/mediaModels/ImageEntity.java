package com.psychotalk.model.mediaModels;

import com.psychotalk.model.account.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ImageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "caption" , length = 4000)
    private String caption;
    private long likeCount;
    private long commentCount;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;

    @ManyToOne
    @JoinColumn(name = "account_id" , nullable = false)
    private Account account;
}
