// src/main/java/com/example/dataingestion/DataIngestionApplication.java
package com.example.dataingestion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
public class DataIngestionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DataIngestionApplication.class, args);
    }
}