package com.rentcar.product.messaging.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String PRODUCT_REQUEST_QUEUE = "product-request-queue";
    public static final String PRODUCT_REQUEST_EXCHANGE = "security-request-exchange";
    public static final String PRODUCT_REQUEST_KEY = "product-request-rout-key";

    public static final String PRODUCT_ERROR_QUEUE = "product-response-error-queue";
    public static final String PRODUCT_ERROR_EXCHANGE = "security-response-error-exchange";
    public static final String PRODUCT_ERROR_KEY = "product-response-error-rout-key";

    public static final String PRODUCT_SUCCESS_QUEUE = "product-response-success-queue";
    public static final String PRODUCT_SUCCESS_EXCHANGE = "security-response-success-exchange";
    public static final String PRODUCT_SUCCESS_KEY = "product-response-success-rout-key";

    @Value("${spring.rabbitmq.username}")
    private String username;

    @Value("${spring.rabbitmq.password}")
    private String password;

    @Value("${spring.rabbitmq.addresses}")
    private String address;

    @Value("${spring.rabbitmq.vhost}")
    private String vHost;

    // Helper methods to create queues, exchanges, and bindings
    private Queue createQueue(String name) {
        return new Queue(name, true);
    }

    private DirectExchange createExchange(String name) {
        return new DirectExchange(name);
    }

    private Binding createBinding(Queue queue, DirectExchange exchange, String routingKey) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


    // Beans for Queues
    @Bean
    public Queue productRequestQueue() {
        return createQueue(PRODUCT_REQUEST_QUEUE);
    }

    @Bean
    public Queue productErrorQueue() {
        return createQueue(PRODUCT_ERROR_QUEUE);
    }

    @Bean
    public Queue productSuccessQueue() {
        return createQueue(PRODUCT_SUCCESS_QUEUE);
    }

    // Beans for Exchanges
    @Bean
    public DirectExchange productRequestExchange() {
        return createExchange(PRODUCT_REQUEST_EXCHANGE);
    }

    @Bean
    public DirectExchange productErrorExchange() {
        return createExchange(PRODUCT_ERROR_EXCHANGE);
    }

    @Bean
    public DirectExchange productSuccessExchange() {
        return createExchange(PRODUCT_SUCCESS_EXCHANGE);
    }

    // Beans for Bindings
    @Bean
    public Binding productRequestBinding(Queue productRequestQueue, DirectExchange productRequestExchange) {
        return createBinding(productRequestQueue, productRequestExchange, PRODUCT_REQUEST_KEY);
    }

    @Bean
    public Binding productErrorBinding(Queue productErrorQueue, DirectExchange productErrorExchange) {
        return createBinding(productErrorQueue, productErrorExchange, PRODUCT_ERROR_KEY);
    }

    @Bean
    public Binding productSuccessBinding(Queue productSuccessQueue, DirectExchange productSuccessExchange) {
        return createBinding(productSuccessQueue, productSuccessExchange, PRODUCT_SUCCESS_KEY);
    }
}
