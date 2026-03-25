package com.psychotalk.service;

import com.psychotalk.exception.customExceptions.*;
import com.psychotalk.model.Appointment;
import com.psychotalk.model.enums.AppointmentStatus;
import com.psychotalk.model.enums.PaymentStatus;
import com.psychotalk.model.account.User;
import com.psychotalk.repository.AppointmentRepo;
import com.psychotalk.repository.ExpertRepo;
import com.psychotalk.repository.UserRepo;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class PaymentService {
    @Autowired
    private AppointmentRepo appointmentRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ExpertRepo expertRepo;

    @Value("${razorpay.key.id}")
    private String razorpayId;

    @Value("${razorpay.key.secret}")
    private String razorpayKey;

    public Map<String, Object> createOrder(Long id) {
        Appointment appointment = appointmentRepo.findById(id).orElseThrow(AppointmentNotFoundException::new);


        User user = userRepo.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(UserNotFoundException::new);
        if(!Objects.equals(appointment.getUser().getId(), user.getId())) throw new AccessDeniedException();

        if(appointment.getPaymentStatus() == PaymentStatus.SUCCESS) throw new RuntimeException("Already Paid");

        if(appointment.getAppointmentStatus() != AppointmentStatus.PAYMENT_PENDING) throw new AppointmentStatusViolationException();

        if(appointment.getOrderId() != null){
            Map<String,Object> response = new HashMap<>();
            response.put("orderId", appointment.getOrderId());
            response.put("amount", appointment.getAmountInPaise());
            response.put("key", razorpayId);
            return response;
        }
        JSONObject options = new JSONObject();
        options.put("amount", appointment.getAmountInPaise());
        options.put("currency", appointment.getCurrency());
        options.put("receipt", "txn_"+appointment.getId());

        try{
            RazorpayClient client = new RazorpayClient(razorpayId, razorpayKey);
            Order order = client.orders.create(options);

            appointment.setOrderId(order.get("id"));
            appointment.setPaymentStatus(PaymentStatus.CREATED);
            appointment.setReceiptId("txn_"+appointment.getId());

            appointmentRepo.save(appointment);

            Map<String,Object> response = new HashMap<>();
            response.put("orderId", order.get("id"));
            response.put("amount", order.get("amount"));
            response.put("key", razorpayId);
            return response;

        }catch (Exception ex){
            throw new RuntimeException("something went wrong while creating order"+ex);
        }

    }


    public String verify(Map<String, String> data) throws Exception {
        String orderId = data.get("razorpay_order_id");
        String paymentId = data.get("razorpay_payment_id");
        String signature = data.get("razorpay_signature");

        Appointment appointment = appointmentRepo.findByOrderId(orderId)
                .orElseThrow(()->new RuntimeException("Invalid Order"));

        if(appointment.getPaymentStatus() == PaymentStatus.SUCCESS){
            return "Already paid";
        }

        if(appointment.getAppointmentStatus() != AppointmentStatus.PAYMENT_PENDING)
            throw new AppointmentStatusViolationException("Action Not allowed");

        String generatedSignature = HmacSHA256(orderId + "|" + paymentId, razorpayKey);

        if (!generatedSignature.equals(signature)) {
            appointment.setPaymentStatus(PaymentStatus.FAILED);
            appointmentRepo.save(appointment);
            throw new RuntimeException("Invalid payment");
        }

        appointment.setPaymentId(paymentId);
        appointment.setPaymentStatus(PaymentStatus.SUCCESS);
        appointment.setAppointmentStatus(AppointmentStatus.SCHEDULED);
        appointmentRepo.save(appointment);
        return "payment verified successfully";



    }

    private String HmacSHA256(String data, String razorpayKey) throws Exception {
        Mac mac = Mac.getInstance("HmacSHA256");

        SecretKeySpec secretKey = new SecretKeySpec(razorpayKey.getBytes(), "HmacSHA256");
        mac.init(secretKey);

        byte[] hash = mac.doFinal(data.getBytes());

        StringBuilder hex = new StringBuilder(2*hash.length);
        for(byte b : hash){
            String s = Integer.toHexString(0xff & b);
            if (s.length() == 1) {
                hex.append('0');
            }
            hex.append(s);
        }
        return hex.toString();
    }
}
