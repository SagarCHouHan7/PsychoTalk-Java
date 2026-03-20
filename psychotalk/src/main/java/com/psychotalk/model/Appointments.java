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

//}
