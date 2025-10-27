package com.psychotalk.service;

import com.psychotalk.model.Appointments;
import com.psychotalk.repository.AppointmentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AppointmentService {
    @Autowired
    AppointmentRepo appointmentRepo;
    public String createAppointment(Appointments appointments, String scheduleTime) {
        System.out.println(scheduleTime);
        Date appointmentTime = stringToDate(scheduleTime);
        Appointments available =  appointmentRepo.searchIfTimeAvailable(appointmentTime , appointments.getExpert().getId());
        if(available == null){
            Date date = getDate();
            appointments.setAppointmentTime(appointmentTime);
            appointments.setCreatedTime(date);
            appointments.setModifiedTime(date);
            appointments.setScheduled(true);
            appointmentRepo.save(appointments);
            return "{\"status\":\"success\"}";
        }else{
           return "{\"status\":\"failure\",\"errorMsg\":\"This time is not available try with different time slot.\"}";
        }

    }

    private Date getDate(){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = formatter.format(new Date());
        try {
            return formatter.parse(formattedDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

//    private Date stringToDate(String date){
//        try {
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = formatter.format(formatter.parse(date));
//
//            return formatter.parse(formattedDate);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//    }
private Date stringToDate(String date) {
    try {
        date = date.replace("T", " ");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formatter.parse(date);
    } catch (ParseException e) {
        throw new RuntimeException(e);
    }
}

    public List<Map<String ,String>> getAppointmentsByUserId(Integer userId) {
        List<Map<String,String>> mapList = new ArrayList<>();
        List<Appointments> list = appointmentRepo.getAppointmentsBYUserId(userId);
        for (Appointments a: list){
            Map<String , String> map = new HashMap<>();
            map.put("appointmentId",a.getAppointmentId()+"");
            map.put("expertName", a.getExpert().getFullName());
            map.put("expertAbout" , a.getExpert().getAbout());
            map.put("expertRating" , a.getExpert().getRating()+"");
            map.put("expertExperience" , a.getExpert().getExperience());
            map.put("expertAddress" , a.getExpert().getAddress());
            map.put("fees", a.getExpert().getFees()+"");
            map.put("username", a.getUser().getUsername());
            map.put("userEmail" , a.getUser().getEmail());
            map.put("userAge" , a.getUser().getAge()+"");
            map.put("reason" , a.getReason());
            map.put("time" , a.getAppointmentTime()+"");
            map.put("isScheduled" , a.isScheduled()+"");
            mapList.add(map);
        }
        return mapList;
    }

    public List<Map<String, String>> getAppointmentByExpertId(Integer expertId) {
        List<Appointments> list = appointmentRepo.getAppointmentsBYExpertId(expertId);
        List<Map<String,String>> mapList = new ArrayList<>();
        if(list!=null){

            for(Appointments a : list){
                Map<String,String> map = new HashMap<>();
                map.put("appointmentId",a.getAppointmentId()+"");
                map.put("expertName", a.getExpert().getFullName());
                map.put("expertAbout" , a.getExpert().getAbout());
                map.put("expertRating" , a.getExpert().getRating()+"");
                map.put("expertExperience" , a.getExpert().getExperience());
                map.put("expertAddress" , a.getExpert().getAddress());
                map.put("fees", a.getExpert().getFees()+"");
                map.put("username", a.getUser().getUsername());
                map.put("userEmail" , a.getUser().getEmail());
                map.put("userAge" , a.getUser().getAge()+"");
                map.put("reason" , a.getReason());
                map.put("time" , a.getAppointmentTime()+"");
                map.put("isScheduled" , a.isScheduled()+"");
                mapList.add(map);
            }
        }
        return  mapList;
    }
}
