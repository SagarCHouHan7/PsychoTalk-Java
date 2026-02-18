package com.psychotalk.controller;

import com.psychotalk.dto.expertDto.ExpertVerificationRequestDto;
import com.psychotalk.dto.expertDto.ExpertProfileDto;
import com.psychotalk.dto.expertDto.VerificationResponseDto;
import com.psychotalk.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@RestController
@RequestMapping("/expert")
public class ExpertController {

    @Autowired
    ExpertService expertService;

    @PostMapping(value = "/getVerified" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<VerificationResponseDto> getVerified(
            @RequestPart("file") MultipartFile file,
            @RequestPart("data") ExpertVerificationRequestDto dto) throws IOException {
        return ResponseEntity.ok(expertService.getVerified(file , dto));
    }

    @GetMapping("/myProfile")
    public ResponseEntity<ExpertProfileDto> getMyProfile(){
        return ResponseEntity.ok(expertService.getMyProfile());
    }
}
