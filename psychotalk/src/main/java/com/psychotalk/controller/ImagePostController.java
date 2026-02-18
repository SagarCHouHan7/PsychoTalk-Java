//package com.psychotalk.controller;
//
//import com.psychotalk.model.ImagePost;
//import com.psychotalk.service.ImagePostService;
//
//
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import java.awt.*;
//import java.util.List;
//import java.util.Map;
//
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//@RestController
//@RequestMapping("/postImage")
//public class ImagePostController {
//
//    @Autowired
//    ImagePostService imagePostService;
//
//    @PostMapping("/createImagePost/{expertId}")
//    public String createImagePost(@RequestParam("file") MultipartFile file, @PathVariable("expertId") Integer expertId) {
//
//        return imagePostService.createImagePost(file, expertId);
//    }
//
//    @GetMapping("/getAllImagePost")
//    public List<Map<String , String>> getAllImagePost() {
//
//        return imagePostService.getAllImagePost();
//    }
//
//}
