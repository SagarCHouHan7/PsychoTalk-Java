package com.psychotalk.model;

import com.psychotalk.model.account.Expert;
import com.psychotalk.model.account.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "appointments")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4000)
    private String reason;

    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdTime;
    private LocalDateTime appointmentTime;
    private String requestedDuration;

    private boolean isConfirmed;
    private boolean isDone;

    @JoinColumn(name = "expert_id", nullable = false)
    @ManyToOne
    private Expert expert;

    @JoinColumn(name = "user_id" , nullable = false)
    @ManyToOne
    private User user;


}
