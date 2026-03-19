package com.psychotalk.service.publicService;

import com.psychotalk.dto.publicDto.TipDto;
import com.psychotalk.repository.TipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class TipService {
    @Autowired
    private TipRepository tipRepo;

    public TipDto getTip() {
        List<String> tips = tipRepo.getTips();
        long days = ChronoUnit.DAYS.between(
                LocalDate.of(2025,1,1),
                LocalDate.now()
        );
        int index = (int) (days % tips.size());
        TipDto dto = new TipDto();
        dto.setTip(tips.get(index));
        dto.setDate(LocalDate.now());
        return dto;
    }
}
