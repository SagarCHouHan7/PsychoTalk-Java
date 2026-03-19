package com.psychotalk.dto.publicDto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Getter
@Setter
public class TipDto {
    String tip;
    LocalDate date;
}
