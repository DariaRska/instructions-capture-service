
package com.example.instructions.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
http://localhost:8080/swagger-ui/index.html#/trade-controller/uploadFile
 */

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Instructions Capture Service API")
                        .version("1.0")
                        .description("API documentation for trade upload and Kafka integration"));
    }
}
