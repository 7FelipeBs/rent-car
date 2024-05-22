package com.rentcar.product.messaging.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class ProductErrorListener {

    @RabbitListener(queues = "${rabbit.product.error-queue}")
    public void receiveMessage(String message) {
        System.out.println("Received <" + message + ">");
    }
}
