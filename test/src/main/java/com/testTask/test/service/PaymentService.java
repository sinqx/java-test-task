package com.testTask.test.service;

import com.testTask.test.entity.Payment;

import java.util.List;

public interface PaymentService {
    Payment pay(Payment pay);
    Payment findById(Long id);
    Payment save(Payment payment);
    String checkPayment(Payment pay);
    List<Payment> getAllPayments();
    String deleteById(Long id);
}
