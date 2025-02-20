package com.oekrem.jwt_deneme.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**") // Tüm endpointler için izin ver
                        .allowedOrigins("http://localhost:3000") // Frontend adresi (React gibi)
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "PATCH") // İzin verilen HTTP metodları
                        .allowedHeaders("*")
                        .allowCredentials(true); // Cookies gibi yetkiler için
            }
        };
    }

}
