package com.rabbitmq.RestAPI_RabbitMQ.config;

import com.rabbitmq.RestAPI_RabbitMQ.util.JwtUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }
}
