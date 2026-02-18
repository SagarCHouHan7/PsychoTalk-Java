//package com.psychotalk.model;
//
//import jakarta.persistence.*;
//
//import java.util.Date;
//@Entity
//public class Appointments {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    int appointmentId;
//
//    @Column(name="reason", length = 4000)
//    String reason;
//
////    @OneToOne(mappedBy = "id" , cascade = CascadeType.ALL)
//    @JoinColumn(name = "expert_id" , nullable = false)
//    @ManyToOne
//    Experts expert;
//
//    @JoinColumn(name = "user_id" , nullable = false)
//    @ManyToOne
//    Users user;
//
//    Date createdTime;
//    Date ModifiedTime;
//    Date appointmentTime;
//
//    boolean isScheduled;
//
//    public int getAppointmentId() {
//        return appointmentId;
//    }
//
//    public void setAppointmentId(int appointmentId) {
//        this.appointmentId = appointmentId;
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
//    public Users getUser() {
//        return user;
//    }
//
//    public void setUser(Users user) {
//        this.user = user;
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
//        return ModifiedTime;
//    }
//
//    public void setModifiedTime(Date modifiedTime) {
//        ModifiedTime = modifiedTime;
//    }
//
//    public Date getAppointmentTime() {
//        return appointmentTime;
//    }
//
//    public void setAppointmentTime(Date appointmentTime) {
//        this.appointmentTime = appointmentTime;
//    }
//
//    public boolean isScheduled() {
//        return isScheduled;
//    }
//
//    public void setScheduled(boolean scheduled) {
//        isScheduled = scheduled;
//    }
//
//    public String getReason() {
//        return reason;
//    }
//
//    public void setReason(String reason) {
//        this.reason = reason;
//    }
//}
