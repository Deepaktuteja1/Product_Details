package com.example.demo;

import com.example.demo.config.SwaggerConfig;

import org.junit.jupiter.api.Test;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTests {

    @Test
    void testSwaggerConfigBeanExists() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(SwaggerConfig.class, SecurityAutoConfiguration.class);

        contextRunner.run(context -> {
            assertTrue(context.containsBean("swaggerConfig"));
            assertNotNull(context.getBean(SwaggerConfig.class));
        });
    }

    @Test
    void testOpenApiCustomizerBeanExists() {
        ApplicationContextRunner contextRunner = new ApplicationContextRunner()
                .withUserConfiguration(SwaggerConfig.class, SecurityAutoConfiguration.class);

        contextRunner.run(context -> {
            assertTrue(context.containsBean("openApiCustomizer"));
            assertNotNull(context.getBean(OpenApiCustomizer.class));
        });
    }


}
