package com.example.demo;

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
								.title("Department API")
								.version("1.0")
								.description("Documentation for Department API"))
				.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearer-key"));
	}
}
