package com.parking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI parkingLotOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Parking Lot Management API")
                        .description("Production-grade Parking Lot System with REST API, JPA persistence, and Strategy pattern")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Parking Lot Team")
                                .email("dev@parking.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}
