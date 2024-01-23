package com.example.demo.config;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearer-key",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public OpenApiCustomizer openApiCustomizer() {
        return openApi -> openApi.info(
                        new io.swagger.v3.oas.models.info.Info()
                                .title("Product API")
                                .version("1.0")
                                .description("Documentation for Product API"))
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearer-key"));
    }
}
