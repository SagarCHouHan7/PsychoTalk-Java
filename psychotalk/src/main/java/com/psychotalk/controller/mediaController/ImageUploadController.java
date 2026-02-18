package com.psychotalk.controller.mediaController;

import com.psychotalk.dto.mediaDto.ImageResponseDto;
import com.psychotalk.service.mediaService.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/media")
public class ImageUploadController {

    @Autowired
    private MediaService mediaService;

    @PostMapping("/upload")
    public ResponseEntity<ImageResponseDto> uploadImage(
            @RequestParam("file") MultipartFile file,
            @RequestParam("caption") String caption
    ) throws IOException {

        return ResponseEntity.ok(mediaService.saveImage(file , caption));
    }

}
