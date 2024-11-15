package com.example.dataingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient  // For service registration and discovery
@EnableFeignClients    // If you plan to call other services
@EnableScheduling      // For scheduled tasks if needed
public class DataIngestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataIngestionApplication.class, args);
    }
}