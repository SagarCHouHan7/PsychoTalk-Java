//package com.psychotalk.service;
//
//import com.psychotalk.model.ContactMessage;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//import org.springframework.stereotype.Service;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//@Service
//public class ContactService {
//    @Autowired
//    private JavaMailSender mailSender;
//    public String sendEmailToPsychoTalk(ContactMessage message) {
//
//        message.setDate(getDate());
//
//        try{
//            SimpleMailMessage mail = new SimpleMailMessage();
//
//            mail.setTo("sagarsinghchouhan1705@gmail.com");
//            mail.setSubject(message.getSubject());
//            mail.setText(
//                    "date :" + message.getDate() +"\n"
//                    +"name :" + message.getName() + "\n"
//                            + "Email :" +message.getEmail() + "\n"
//                            + "Message :" +message.getMessage() +"\n"
//            );
//            System.out.println(message);
//            mailSender.send(mail);
//            System.out.println(message);
//            return  "{\"status\":\"success\"}";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return  "{\"status\":\"failure\"}";
//        }
//    }
//    private Date getDate(){
//        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String formattedDate = formatter.format(new Date());
//        try {
//            return formatter.parse(formattedDate);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
