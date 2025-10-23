package com.psychotalk.model;

import com.psychotalk.repository.QuestionRepo;
import com.psychotalk.service.QuestionService;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.naming.Context;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private String email;
    private String password;
    private String phone;
    private Date createdTime;
    private  Date modifiedTime;
    private int age;
    private String gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getModifiedTime() {
        return modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return '{' +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", createdTime=" + createdTime +
                ", modifiedTime=" + modifiedTime +
                '}';
    }
}
