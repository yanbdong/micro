package com.itmuch.cloud.study;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

//@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class ConsumerMovieApplication {

    public static final String userServiceName = "microservice-simple-provider-user";

    public static void main(String[] args) {
        SpringApplication.run(ConsumerMovieApplication.class, args);
    }

    @Bean
    @LoadBalanced // Auto Ribbon (Ribbon is self-contained by eureka)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
