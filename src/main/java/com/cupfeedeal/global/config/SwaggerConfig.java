package com.cupfeedeal.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "API Test",
                description = "CEOS vote service API 명세서",
                version = "v1"
        ),
        servers = {@Server(url = "https://api.cupfeedeal.xyz", description = "ec2 server"),
                @Server(url = "http://localhost:8080", description = "local server")}
)

public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components());
    }
}