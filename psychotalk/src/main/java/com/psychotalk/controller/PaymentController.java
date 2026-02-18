//package com.psychotalk.controller;
//
//import com.razorpay.Order;
//import com.razorpay.RazorpayClient;
//import org.json.JSONObject;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//@RestController
//@RequestMapping("/api/payment")
//public class PaymentController {
//
//    @Value("${razorpay.key.id}")
//    private String razorpayKeyId;
//
//    @Value("${razorpay.key.secret}")
//    private String razorpaySecret;
//
//    @PostMapping("/createOrder")
//    public Map<String, Object> createOrder(@RequestBody Map<String, Object> data) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            RazorpayClient razorpay = new RazorpayClient(razorpayKeyId, razorpaySecret);
//
//            int amount = (int) data.get("amount"); // Amount in paise
//
//            JSONObject orderRequest = new JSONObject();
//            orderRequest.put("amount", amount);
//            orderRequest.put("currency", "INR");
//            orderRequest.put("receipt", "txn_" + System.currentTimeMillis());
//
//            Order order = razorpay.orders.create(orderRequest);
//
//            response.put("orderId", order.get("id"));
//            response.put("amount", order.get("amount"));
//            response.put("currency", order.get("currency"));
//            response.put("key", razorpayKeyId);
//
//            return response;
//        } catch (Exception e) {
//            e.printStackTrace();
//            response.put("error", e.getMessage());
//            return response;
//        }
//    }
//}
