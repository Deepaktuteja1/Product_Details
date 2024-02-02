package com.example.demo;
import com.example.demo.controller.HealthController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class HealthControllerTests {

    @Test
    void testGetHealthStatus_Healthy() {
        // Mocking
        HealthIndicator customHealthIndicator = mock(HealthIndicator.class);
        HealthController controller = new HealthController(customHealthIndicator);
        Health healthyStatus = Health.up().build();
        when(customHealthIndicator.health()).thenReturn(healthyStatus);

        // Execution
        Health response = controller.getHealthStatus();

        // Verification
        assertEquals(healthyStatus, response);
    }

    @Test
    void testGetHealthStatus_Unhealthy() {
        // Mocking
        HealthIndicator customHealthIndicator = mock(HealthIndicator.class);
        HealthController controller = new HealthController(customHealthIndicator);
        Health unhealthyStatus = Health.down().withDetail("error", "Database connection failed").build();
        when(customHealthIndicator.health()).thenReturn(unhealthyStatus);

        // Execution
        Health response = controller.getHealthStatus();

        // Verification
        assertEquals(unhealthyStatus, response);
    }

    // Add more test cases as needed...
}
