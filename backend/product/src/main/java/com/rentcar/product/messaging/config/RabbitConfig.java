package com.rentcar.product.messaging.config;

import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private final AmqpAdmin amqpAdmin;

    public RabbitConfig(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    public static final String PRODUCT_QUEUE = "product-request-queue";
    public static final String PRODUCT_EXCHANGE = "security-request-exchange";
    public static final String PRODUCT_KEY = "product-request-rout-key";

    public static final String PRODUCT_ERROR_QUEUE = "product-response-error-queue";
    public static final String PRODUCT_ERROR_EXCHANGE = "security-response-error-exchange";
    public static final String PRODUCT_ERROR_KEY = "product-response-error-rout-key";

    public static final String PRODUCT_SUCCESS_QUEUE = "product-response-success-queue";
    public static final String PRODUCT_SUCCESS_EXCHANGE = "security-response-success-exchange";
    public static final String PRODUCT_SUCCESS_KEY = "product-response-success-rout-key";

    private Queue createQueue(String name) {
        return new Queue(name, true);
    }

    private DirectExchange createExchange(String name) {
        return new DirectExchange(name);
    }

    private Binding createBinding(Queue queue, DirectExchange exchange, String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


    @PostConstruct
    private void createRabbitConfig(){
        Queue productQueueQueue = this.createQueue(PRODUCT_QUEUE);
        Queue productErrorQueue = this.createQueue(PRODUCT_ERROR_QUEUE);
        Queue productSuccessQueue = this.createQueue(PRODUCT_SUCCESS_QUEUE);

        DirectExchange productExg = createExchange(PRODUCT_EXCHANGE);
        DirectExchange productErrorExg = createExchange(PRODUCT_ERROR_EXCHANGE);
        DirectExchange productSuccessExg = createExchange(PRODUCT_SUCCESS_EXCHANGE);

        Binding productBinding = createBinding(productQueueQueue, productExg, PRODUCT_KEY);
        Binding productErrorBinding = createBinding(productErrorQueue, productErrorExg, PRODUCT_ERROR_KEY);
        Binding productSuccessBinding = createBinding(productSuccessQueue, productSuccessExg, PRODUCT_SUCCESS_KEY);

        this.amqpAdmin.declareQueue(productQueueQueue);
        this.amqpAdmin.declareQueue(productErrorQueue);
        this.amqpAdmin.declareQueue(productSuccessQueue);

        this.amqpAdmin.declareExchange(productExg);
        this.amqpAdmin.declareExchange(productErrorExg);
        this.amqpAdmin.declareExchange(productSuccessExg);

        this.amqpAdmin.declareBinding(productBinding);
        this.amqpAdmin.declareBinding(productErrorBinding);
        this.amqpAdmin.declareBinding(productSuccessBinding);
    }
}
