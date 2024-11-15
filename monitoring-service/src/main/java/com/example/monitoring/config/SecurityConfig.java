package com.example.monitoring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.Customizer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // Disable CSRF for WebSocket
                .cors(Customizer.withDefaults())  // Enable CORS
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/actuator/**").permitAll()  // Allow actuator endpoints
                        .requestMatchers("/ws/**").permitAll()        // Allow WebSocket connections
                        .anyRequest().authenticated()                 // Require auth for other endpoints
                );

        return http.build();
    }
}