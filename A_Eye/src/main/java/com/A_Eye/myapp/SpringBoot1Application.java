package com.A_Eye.myapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.A_Eye.myapp.config.WebConfig;

@EnableScheduling
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class SpringBoot1Application {
    public static void main(String[] args) {
        SpringApplication.run(SpringBoot1Application.class, args);
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                    // .allowedOrigins("http://localhost:3000")
                    .allowedOrigins("http://43.201.117.185:8089/A_Eye")
                    .allowedMethods("GET", "POST", "PUT", "DELETE","OPTION")
                    .allowedHeaders("*")
                    .exposedHeaders("Authorization")
                    .allowCredentials(true)
                    .maxAge(3600);
            }
        };
    }
}