package com.testTask.test.serviceImpl;

import com.testTask.test.entity.Payment;
import com.testTask.test.entity.User;
import com.testTask.test.exception.ObjectNotFoundException;
import com.testTask.test.exception.PaymentException;
import com.testTask.test.repository.PaymentRepository;
import com.testTask.test.service.PaymentService;
import com.testTask.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserService userService;

    @Override
    public Payment save(Payment payment){
        return paymentRepository.save(payment);
    }

    @Override
    public Payment pay(Payment payment) {
        User userAccount = userService.findByPhoneNumber(payment.getAccount());
        User userSupplier = userService.findById(payment.getSupplierId());

        if (payment.getAmount() > userAccount.getMoneyAmount())
        {
            throw new PaymentException("Not enough money on the balance sheet");
        }else if(payment.getDate() == null){
            payment.setDate(LocalDateTime.now());
        }
        userAccount.setMoneyAmount(userAccount.getMoneyAmount() - payment.getAmount());
        userSupplier.setMoneyAmount(userSupplier.getMoneyAmount() + payment.getAmount());
        userService.save(userAccount);
        userService.save(userSupplier);
        paymentRepository.save(payment);
        return payment;
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Payment with id \"" + id + "\" doesn't exist"));
    }

    @Override
    public String checkPayment(Payment pay) {
        if (paymentRepository.findById(pay.getId()).isPresent()){
            return "Payment exists";
        }else {
            throw new PaymentException("Payment with id " + pay.getId() + "doesn't exists");
        }
    }

    @Override
    public List<Payment> getAllPayments() {
        try {
            return paymentRepository.findAll();
        } catch (NullPointerException ignored) {
            throw new ObjectNotFoundException("List is empty");
        }
    }

    @Override
    public String deleteById(Long id) {
        Payment payment = findById(id);
        if (payment == null) {
            throw new ObjectNotFoundException("Payment with id \"" + id + "\" doesn't exist");
        } else {
            paymentRepository.delete(payment);
            return "Payment with id \"" + id + "\" is deleted";
        }
    }
}
