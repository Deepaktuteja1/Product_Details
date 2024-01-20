package com.example.demo;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
public class ProductDetails {

	public static void main(String[] args) {
		SpringApplication.run(ProductDetails.class, args);
	}

}


//package com.harshproject;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
//import io.swagger.v3.oas.annotations.security.SecurityScheme;
//
//import org.springdoc.core.customizers.OpenApiCustomizer;

//@SpringBootApplication
////@EnableJpaRepositories(repositoryBaseClass = RefreshableCRUDRepositoryImpl.class)
//public class DepartmentSpringbootApplication {
//
//	public static void main(String[] args) {
//		SpringApplication.run(DepartmentSpringbootApplication.class, args);
//	}

//	@Configuration
//	@SecurityScheme(
//			name = "bearer-key",
//			type = SecuritySchemeType.HTTP,
//			scheme = "bearer",
//			bearerFormat = "JWT"
//	)
//	public class SwaggerConfig {
//
//		@Bean
//		public OpenApiCustomizer openApiCustomizer() {
//			return openApi -> openApi.info(
//							new io.swagger.v3.oas.models.info.Info()
//									.title("Department API")
//									.version("1.0")
//									.description("Documentation for Department API"))
//					.addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("bearer-key"));
//		}
//	}
//}
