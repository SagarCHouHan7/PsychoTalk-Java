//package com.psychotalk.service;
//
//import com.psychotalk.model.ImagePost;
//import com.psychotalk.repository.ExpertRepoOld;
//import com.psychotalk.repository.ImagePostRepo;
//import org.springframework.web.multipart.MultipartFile;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.nio.file.*;
//import java.nio.file.StandardCopyOption;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//
//@Service
//public class ImagePostService {
//
//    @Autowired
//    ImagePostRepo imagePostRepo;
//
//    @Autowired
//    ExpertRepoOld expertRepoOld;
//
//    public String createImagePost(MultipartFile  file , Integer expertId) {
//
//        ImagePost image = new ImagePost();
//        try {
//
//            image.setCreatedTime(getDate());
//            image.setModifiedTime(getDate());
//            image.setExpert(expertRepoOld.getExpertByExpertId(expertId));
////            image.setCaption(imageData.getCaption());
//            image = imagePostRepo.save(image);
//
//            Path uploadDir = Paths.get(System.getProperty("user.dir"), "assets", "uploads", expertId.toString(), image.getId().toString());
//            Files.createDirectories(uploadDir);
//
//            // ✅ Create a unique filename with extension
//            String originalFilename = file.getOriginalFilename();
//
//            if (originalFilename != null) {
//
//            Path filePath = uploadDir.resolve(originalFilename);
//
//            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
//            image.setPath(uploadDir.toString());
//            imagePostRepo.save(image);
//            }else{
//                throw  new AssertionError();
//            }
//
//        }catch (Exception e){
//            e.fillInStackTrace();
//        }
//        return image.getPath();
//    }
//
//    private Date getDate(){
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = formatter.format(new Date());
//        try{
//            return  formatter.parse(formattedDate);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//    public List<Map<String , String>> getAllImagePost() {
//        List<ImagePost> dblist = imagePostRepo.findAll();
//        List<Map<String,String>> newList = new ArrayList<>();
//        for(ImagePost im : dblist){
//            Map<String , String> map = new HashMap<>();
//
//            map.put("Path" , im.getPath());
//            map.put("likes" , im.getLikes()+"");
//            map.put("time" , im.getModifiedTime()+"");
//            map.put("caption" , im.getCaption());
//            map.put("expert" , im.getExpert().getFullName());
//            map.put("expertId" , im.getExpert().getId()+"");
//            newList.add(map);
//
//        }
//
//        return newList;
//    }
//}
