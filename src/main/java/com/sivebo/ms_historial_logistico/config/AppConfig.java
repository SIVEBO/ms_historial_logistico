package com.sivebo.ms_historial_logistico.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class AppConfig {
        
        @Value("${ms.guias_despacho.url}")
        private String guiaDespachoBaseUrl;

        @Value("${ms.sucursales.url}")
        private String sucursalBaseUrl;

        @Bean
        public WebClient.Builder webClientBuilder() {
                return WebClient.builder();
        }

        @Bean
        public WebClient guiaDespachoWebClient(WebClient.Builder webClientBuilder) {
                return webClientBuilder
                        .baseUrl(guiaDespachoBaseUrl)
                        .build();
        }

        @Bean
        public WebClient sucursalWebClient(WebClient.Builder webClientBuilder) {
                return webClientBuilder
                        .baseUrl(sucursalBaseUrl)
                        .build();
        }

}
