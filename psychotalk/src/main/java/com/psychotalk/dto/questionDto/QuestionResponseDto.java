package com.psychotalk.dto.questionDto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class QuestionResponseDto {
    private Long id;
    private String question;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private int likes;
    private String username;
    private int answerCount;
    private long userId;
}
