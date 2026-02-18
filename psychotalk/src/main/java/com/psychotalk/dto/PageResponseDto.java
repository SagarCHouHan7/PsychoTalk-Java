package com.psychotalk.dto;

import com.psychotalk.dto.questionDto.QuestionResponseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PageResponseDto<T> {

    private List<T> content;
    private int page;
    private int size;
    private long totalPages;
    private long totalElements;
    private boolean isLast;

}
