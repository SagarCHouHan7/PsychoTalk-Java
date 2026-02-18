package com.psychotalk.dto.AnswerDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AnswerResponseDto {
    //9
    Long id;
    private String answer;
    private long questionId;
    private long accountId;
    private String displayName;
    private String role;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private long likes;
}
