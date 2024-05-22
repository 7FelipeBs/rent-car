package com.rentcar.product.messaging.config;

import com.rentcar.product.messaging.listener.ProductErrorListener;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    private final AmqpAdmin amqpAdmin;


    @Value("${rabbit.product.queue}")
    private String productQueue;
    @Value("${rabbit.product.exchange}")
    private String productExchange;
    @Value("${rabbit.product.key}")
    private String productKey;

    @Value("${rabbit.product.error-queue}")
    private String productErrorQueue;
    @Value("${rabbit.product.error-exchange}")
    private String productErrorExchange;
    @Value("${rabbit.product.error-key}")
    private String productErrorKey;

    @Value("${rabbit.product.success-queue}")
    private String productSuccessQueue;
    @Value("${rabbit.product.success-exchange}")
    private String productSuccessExchange;
    @Value("${rabbit.product.success-key}")
    private String productSuccessKey;

    public RabbitConfig(AmqpAdmin amqpAdmin){
        this.amqpAdmin = amqpAdmin;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

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
        Queue productQueueRabbit = this.createQueue(productQueue);
        Queue productErrorQueueRabbit = this.createQueue(productErrorQueue);
        Queue productSuccessQueueRabbit = this.createQueue(productSuccessQueue);

        DirectExchange productExgRabbit = createExchange(productExchange);
        DirectExchange productErrorExgRabbit = createExchange(productErrorExchange);
        DirectExchange productSuccessExgRabbit = createExchange(productSuccessExchange);

        Binding productBindingRabbit = createBinding(productQueueRabbit, productExgRabbit, productKey);

        Binding productErrorBindingRabbit = createBinding(productErrorQueueRabbit, productErrorExgRabbit, productErrorKey);
        Binding productSuccessBindingRabbit = createBinding(productSuccessQueueRabbit, productSuccessExgRabbit, productSuccessKey);

        this.amqpAdmin.declareQueue(productQueueRabbit);
        this.amqpAdmin.declareQueue(productErrorQueueRabbit);
        this.amqpAdmin.declareQueue(productSuccessQueueRabbit);

        this.amqpAdmin.declareExchange(productExgRabbit);
        this.amqpAdmin.declareExchange(productErrorExgRabbit);
        this.amqpAdmin.declareExchange(productSuccessExgRabbit);

        this.amqpAdmin.declareBinding(productBindingRabbit);
        this.amqpAdmin.declareBinding(productErrorBindingRabbit);
        this.amqpAdmin.declareBinding(productSuccessBindingRabbit);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(ProductErrorListener receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}
