package com.rentcar.product.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI config() {

        return new OpenAPI()
                .info(new Info().title("Product - Car Rent")
                        .contact(new Contact().name("felipebs").email("felipe7bs@gmail.com"))
                        .description("Car Rent is MVP (study to microservice), this is api for management a creating, updating, deleting and find to car rent.")
                        .version("v0.0.1"));
    }
}
