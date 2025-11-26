package com.sms.gaunsmsservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        
        // Tüm origin'lere izin ver
        configuration.setAllowedOriginPatterns(List.of("*"));
        
        // Tüm HTTP metodlarına izin ver
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "HEAD"));
        
        // Tüm header'lara izin ver
        configuration.setAllowedHeaders(List.of("*"));
        
        // Credentials'a izin verme (wildcard origin ile uyumlu)
        configuration.setAllowCredentials(false);
        
        // Expose edilecek header'lar
        configuration.setExposedHeaders(Arrays.asList("Content-Type", "Authorization", "Content-Length", "X-Requested-With"));
        
        // Preflight cache süresi
        configuration.setMaxAge(3600L);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        
        return source;
    }
}
