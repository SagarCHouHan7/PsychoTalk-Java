package com.psychotalk.dto.appointmentDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentRequestDto {
    private String reason;
    private LocalDateTime appointmentTime;
    private int durationInMinutes;
    private Long expertId;
}
