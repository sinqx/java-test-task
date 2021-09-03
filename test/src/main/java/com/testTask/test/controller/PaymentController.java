package com.testTask.test.controller;

import com.testTask.test.entity.Payment;
import com.testTask.test.response.PaymentResponse;
import com.testTask.test.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;


    @PostMapping(value = "/pay", produces = "application/xml")
    public ResponseEntity pay(@RequestBody Payment payment) {
        try {
            paymentService.pay(payment);
            PaymentResponse response = PaymentResponse.constructor(payment, "PAYMENT CONFIRMED", 1L);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @PostMapping(value = "/check", produces = "application/xml")
    public ResponseEntity checkPayment(@RequestBody Payment payment) {
        try {
            String answer = paymentService.checkPayment(payment);
            PaymentResponse response = PaymentResponse.constructor(payment, answer, 3L);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }


    @GetMapping(value = "/all", produces = "application/xml")
    public ResponseEntity getAllPayments() {
        try {
            List<Payment> payments = paymentService.getAllPayments();
            return new ResponseEntity<>(payments, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @GetMapping(value = "/find/{paymentId}", produces = "application/xml")
    public ResponseEntity getAllPayments(@PathVariable Long paymentId) {
        try {
            Payment payment = paymentService.findById(paymentId);
            PaymentResponse response = PaymentResponse.constructor(payment, "PAYMENT EXISTS", 2L);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

    @DeleteMapping("/{paymentId}")
    public ResponseEntity deleteById(@PathVariable Long paymentId) {
        try {
            String answer = paymentService.deleteById(paymentId);
            return new ResponseEntity<>(answer, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.OK);
        }
    }

}
