package com.example.conversor_moneda.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Appconfig {
    
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
