package com.psychotalk.model.account;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "account")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = false , nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

}
