package br.com.erp.queenfitstyle.catalog.config;

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
                registry.addMapping("/**")        // todos os endpoints
                        .allowedOrigins("*")      // qualquer origem
                        .allowedMethods("*")      // qualquer método: GET, POST, etc
                        .allowedHeaders("*")      // qualquer header
                        .allowCredentials(false); // desabilita credenciais para "*" funcionar
            }
        };
    }
}
