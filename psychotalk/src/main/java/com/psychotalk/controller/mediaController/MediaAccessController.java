package com.psychotalk.controller.mediaController;

import com.fasterxml.jackson.databind.ser.std.StdKeySerializers;
import com.psychotalk.dto.PageResponseDto;
import com.psychotalk.dto.mediaDto.ImageResponseDto;
import com.psychotalk.service.mediaService.MediaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/media/access")
public class MediaAccessController {

    @Autowired
    private MediaService mediaService;

    @GetMapping("/getAll")
    public ResponseEntity<PageResponseDto<ImageResponseDto>> getAllMedia(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        return ResponseEntity.ok(mediaService.geAllMedia(page,size));
    }
}
