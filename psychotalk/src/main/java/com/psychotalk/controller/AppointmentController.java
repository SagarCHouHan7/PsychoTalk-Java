//package com.psychotalk.controller;
//
//import com.psychotalk.model.Appointments;
//import com.psychotalk.service.AppointmentService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Map;
//
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//@RestController
//@RequestMapping("/appointment")
//
//public class AppointmentController {
//    @Autowired
//    AppointmentService appointmentService;
//
//    @PostMapping("/createAppointment")
//    public String createAppointment(@RequestBody Appointments appointments, @RequestParam("scheduleTime") String scheduleTime){
//        return appointmentService.createAppointment(appointments,  scheduleTime);
//    }
//
//    @GetMapping("/getAppointmentsByUserId/{id}")
//    public List<Map<String,String>> getAppointmentsByUserId(@PathVariable("id") Integer userId){
//        return appointmentService.getAppointmentsByUserId(userId);
//
//    }
//
//    @GetMapping("/getAppointmentByExpertId/{id}")
//    public List<Map<String,String>> getAppointmentByExpertId(@PathVariable("id") Integer expertId){
//        return  appointmentService.getAppointmentByExpertId(expertId);
//    }
//
//}
