//package com.psychotalk.model;
//
//import jakarta.persistence.*;
//import jakarta.persistence.criteria.CriteriaBuilder;
//
//import java.util.Date;
//
//@Entity
//public class ImagePost {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    Integer id;
//
//    @JoinColumn(name="expert_id" , nullable = false)
//    @ManyToOne
//    Experts expert;
//
//    String path;
//
//    String fileName;
//
//    int likes;
//
//    Date createdTime;
//    Date modifiedTime;
//
//    String caption;
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public Experts getExpert() {
//        return expert;
//    }
//
//    public void setExpert(Experts expert) {
//        this.expert = expert;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public String getFileName() {
//        return fileName;
//    }
//
//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }
//
//    public int getLikes() {
//        return likes;
//    }
//
//    public void setLikes(int likes) {
//        this.likes = likes;
//    }
//
//    public Date getCreatedTime() {
//        return createdTime;
//    }
//
//    public void setCreatedTime(Date createdTime) {
//        this.createdTime = createdTime;
//    }
//
//    public Date getModifiedTime() {
//        return modifiedTime;
//    }
//
//    public void setModifiedTime(Date modifiedTime) {
//        this.modifiedTime = modifiedTime;
//    }
//
//    public String getCaption() {
//        return caption;
//    }
//
//    public void setCaption(String caption) {
//        this.caption = caption;
//    }
//}
