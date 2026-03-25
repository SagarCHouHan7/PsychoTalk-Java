package com.psychotalk.dto.appointmentDto;

import com.psychotalk.dto.userDto.UserProfileDto;
import com.psychotalk.model.enums.AppointmentStatus;
import com.psychotalk.model.enums.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentResDtoForExpert {
    private Long id;
    private String reason;
    private LocalDateTime createdTime;
    private LocalDateTime appointmentTime;
    private int durationInMinutes;
    private AppointmentStatus appointmentStatus;
    private String orderId;
    private PaymentStatus paymentStatus;
    private int amountInPaise;
    private String currency;
    private UserProfileDto user;
}
