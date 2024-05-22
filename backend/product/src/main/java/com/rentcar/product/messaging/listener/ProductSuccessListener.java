package com.rentcar.product.messaging.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProductSuccessListener {

//    @RabbitListener(queues = "${rabbit.product.success-queue}")
//    public void receiveMessage(String message) {
//        System.out.println("Received <" + message + ">");
//    }
}
