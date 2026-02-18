//package com.psychotalk.model;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//import java.util.List;
//
//
//@NamedQuery(
//        name = "findExpertDTOByEmail",
//        query = "SELECT new com.psychotalk.model.Experts( e.id, e.email, e.fullName, e.about, e.gender, e.age, e.joiningDate, e.qualification, e.experience, e.fees, e.address, e.rating) FROM Experts e"
//)
//@NamedQuery(
//        name = "findExpertById",
//        query = "SELECT new com.psychotalk.model.Experts( e.id, e.email, e.fullName, e.about, e.gender, e.age, e.joiningDate, e.qualification, e.experience, e.fees, e.address, e.rating) FROM Experts e WHERE e.id=:id"
//)
//@Entity
//public class Experts {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Integer id;
//
//    private String email;
//    private  String password;
//    private String fullName;
//
//    @Column(name="about", length = 4000)
//    private String about;
//    private String gender;
//    private int age;
//    private Date joiningDate;
//    private List<String> qualification;
//    private String experience;
//    private int fees;
//    private String address;
//    private String phoneNo;
//    private int rating;
//
//    public Experts() {
//    }
//
//    public Experts(Integer id, String email, String fullName, String about, String gender, int age, Date joiningDate, List<String> qualification, String experience, int fees, String address, int rating) {
//        this.id = id;
//        this.email = email;
//        this.fullName = fullName;
//        this.about = about;
//        this.gender = gender;
//        this.age = age;
//        this.joiningDate = joiningDate;
//        this.qualification = qualification;
//        this.experience = experience;
//        this.fees = fees;
//        this.address = address;
//        this.rating = rating;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public int getRating() {
//        return rating;
//    }
//
//    public void setRating(int rating) {
//        this.rating = rating;
//    }
//
//    public String getPhoneNo() {
//        return phoneNo;
//    }
//
//    public void setPhoneNo(String phoneNo) {
//        this.phoneNo = phoneNo;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getFullName() {
//        return fullName;
//    }
//
//    public void setFullName(String fullName) {
//        this.fullName = fullName;
//    }
//
//    public String getAbout() {
//        return about;
//    }
//
//    public void setAbout(String about) {
//        this.about = about;
//    }
//
//    public String getGender() {
//        return gender;
//    }
//
//    public void setGender(String gender) {
//        this.gender = gender;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public List<String> getQualification() {
//        return qualification;
//    }
//
//    public void setQualification(List<String> qualification) {
//        this.qualification = qualification;
//    }
//
//    public Date getJoiningDate() {
//        return joiningDate;
//    }
//
//    public void setJoiningDate(Date joiningDate) {
//        this.joiningDate = joiningDate;
//    }
//
//    public String getExperience() {
//        return experience;
//    }
//
//    public void setExperience(String experience) {
//        this.experience = experience;
//    }
//
//    public int getFees() {
//        return fees;
//    }
//
//    public void setFees(int fees) {
//        this.fees = fees;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//
//
//}
