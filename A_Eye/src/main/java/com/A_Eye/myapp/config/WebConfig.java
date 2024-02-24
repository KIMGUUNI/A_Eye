package com.A_Eye.myapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
       registry.addMapping("/**")
        // .allowedOrigins("http://localhost:3000")
        .allowedOrigins("http://43.201.117.185:8089/A_Eye")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .exposedHeaders("Authorization")
        .allowCredentials(true)
        .maxAge(3600);
    }
}