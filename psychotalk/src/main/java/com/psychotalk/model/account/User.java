package com.psychotalk.model.account;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "user")
@Setter
@Getter
@NoArgsConstructor
@ToString
public class User extends Account{


    private String email;

    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Temporal(TemporalType.DATE)
    private Date dob;
    private String gender;
}
