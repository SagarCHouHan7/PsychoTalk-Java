//package com.psychotalk.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.web.bind.annotation.*;
//
//import javax.crypto.Mac;
//import javax.crypto.spec.SecretKeySpec;
//import java.util.Base64;
//import java.util.HashMap;
//import java.util.Map;
//
//
//@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
//@RestController
//@RequestMapping("/api/payment")
//public class PaymentVerificationController{
//
//    @Value("${razorpay.key.secret}")
//    private String razorpaySecret;
//
//    @PostMapping("/verify")
//    public Map<String, Object> verifyPayment(@RequestBody Map<String, String> data) {
//        Map<String, Object> response = new HashMap<>();
//        try {
//            String orderId = data.get("razorpay_order_id");
//            String paymentId = data.get("razorpay_payment_id");
//            String signature = data.get("razorpay_signature");
//
//            String generatedSignature = generateSignature(orderId + "|" + paymentId, razorpaySecret);
//
//            if (generatedSignature.equals(signature)) {
//                // ✅ Signature matched → Payment verified
//                response.put("status", "success");
//                response.put("message", "Payment verified successfully");
//            } else {
//                // ❌ Signature mismatch → Possible tampering
//                response.put("status", "failure");
//                response.put("message", "Invalid signature");
//            }
//        } catch (Exception e) {
//            response.put("status", "error");
//            response.put("message", e.getMessage());
//        }
//        return response;
//    }
//
//    private String generateSignature(String data, String secret) throws Exception {
//        Mac sha256Hmac = Mac.getInstance("HmacSHA256");
//        SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
//        sha256Hmac.init(secretKey);
//        byte[] hash = sha256Hmac.doFinal(data.getBytes());
//        //return new String(Base64.getEncoder().encode(hash));
//        return  bytesToHex(hash);
//    }
//
//    // Helper: convert bytes to hex string
//    private static String bytesToHex(byte[] hash) {
//        StringBuilder hexString = new StringBuilder(2 * hash.length);
//        for (byte b : hash) {
//            String hex = Integer.toHexString(0xff & b);
//            if (hex.length() == 1)
//                hexString.append('0');
//            hexString.append(hex);
//        }
//        return hexString.toString();
//    }
//}
//
