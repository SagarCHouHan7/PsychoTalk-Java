package com.psychotalk.model;

import com.psychotalk.model.account.Expert;
import com.psychotalk.model.account.User;
import com.psychotalk.model.enums.AppointmentStatus;
import com.psychotalk.model.enums.PaymentStatus;
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

    private LocalDateTime createdTime;

    private LocalDateTime appointmentTime;
    private int durationInMinutes;

    @Enumerated(EnumType.STRING)
    private AppointmentStatus appointmentStatus;

    @Column(unique = true, nullable = true)
    private String receiptId;
    private String paymentId;     // razorpay_payment_id
    private String orderId;       // razorpay_order_id

    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;

    private int amountInPaise;
    private String currency = "INR";

    @JoinColumn(name = "expert_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Expert expert;

    @JoinColumn(name = "user_id" , nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;


    @PrePersist
    public void setCreatedTime(){
        this.createdTime = LocalDateTime.now();
        this.appointmentStatus = AppointmentStatus.REQUESTED;
        this.paymentStatus = PaymentStatus.CREATED;
    }
}
