package com.rentcar.product.messaging.publisher;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ProductPublisher {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public ProductPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Value("${rabbit.product.exchange}")
    private String productExchange;
    @Value("${rabbit.product.key}")
    private String productKey;

    @Value("${rabbit.product.error-exchange}")
    private String productErrorExchange;
    @Value("${rabbit.product.error-key}")
    private String productErrorKey;

    @Value("${rabbit.product.success-exchange}")
    private String productSuccessExchange;
    @Value("${rabbit.product.success-key}")
    private String productSuccessKey;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public void sendMessageWithObjectSuccess(Object object) {
        try {
            if(object != null) {
                rabbitTemplate.convertAndSend(productSuccessExchange, productSuccessKey,  objectMapper.writeValueAsString(object));
                System.out.println("Sent object with success>");
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessageWithObjectError(Object object) {
        try {
            if(object != null) {
                rabbitTemplate.convertAndSend(productErrorExchange, productErrorKey,  objectMapper.writeValueAsString(object));
                System.out.println("Sent object with error>");
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessageWithObject(Object object) {
        try {
            if(object != null) {
                rabbitTemplate.convertAndSend(productExchange, productKey,  objectMapper.writeValueAsString(object));
                System.out.println("Sent object>");
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
