package com.tekworks.spring_transaction.service;

import com.tekworks.spring_transaction.entity.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NotificationService {

    @Transactional(propagation = Propagation.NEVER)
    public void sendOrderConfirmation(Order order) {
        //send order confirmation via email or sms
        System.out.println(order.getId()+"Order placed successfully");
    }
}
