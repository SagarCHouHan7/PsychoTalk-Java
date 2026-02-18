package com.psychotalk.dto.mediaDto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ImageResponseDto {

    private Long id;
    private String caption;
    private long likeCount;
    private long commentCount;
    private String fileName;
    private String filePath;
    private LocalDateTime uploadedAt;
    private long accountId;
    private String username;
    private String role;
}
