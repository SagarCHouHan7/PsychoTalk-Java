package com.psychotalk.controller;

import com.psychotalk.dto.payment.PaymentVerificationRequestDto;
import com.psychotalk.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create-order/{id}")
    public ResponseEntity<?> createOrder(@PathVariable Long id){
        return ResponseEntity.ok(paymentService.createOrder(id));
    }


    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody Map<String,String> data) throws Exception {

        return ResponseEntity.ok(paymentService.verify(data));
    }

}
