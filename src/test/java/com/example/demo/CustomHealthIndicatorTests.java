package com.example.demo;
import com.example.demo.utils.CustomHealthIndicator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.actuate.health.Health;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

 class CustomHealthIndicatorTests {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private CustomHealthIndicator customHealthIndicator;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
     void health_shouldReturnUpWhenDatabaseConnectionIsHealthy() {

        when(jdbcTemplate.queryForObject("SELECT 1 FROM dual", Integer.class)).thenReturn(1);


        Health result = customHealthIndicator.health();

        assertEquals("Database connection is healthy", result.getDetails().get("message"));
    }

    @Test
     void health_shouldReturnDownWhenDatabaseConnectionIsNotHealthy() {

        when(jdbcTemplate.queryForObject("SELECT 1 FROM dual", Integer.class)).thenThrow(new RuntimeException("Connection failed"));


        Health result = customHealthIndicator.health();


        assertEquals("Database connection is not healthy", result.getDetails().get("message"));
    }

    @Test
     void isDatabaseConnectionHealthy_shouldReturnTrueWhenQueryIsSuccessful() {

        when(jdbcTemplate.queryForObject("SELECT 1 FROM dual", Integer.class)).thenReturn(1);


        boolean result = invokeIsDatabaseConnectionHealthy();


        assertTrue(result);
    }

    @Test
     void isDatabaseConnectionHealthy_shouldReturnFalseWhenQueryFails() {

        when(jdbcTemplate.queryForObject("SELECT 1 FROM dual", Integer.class)).thenThrow(new RuntimeException("Connection failed"));


        boolean result = invokeIsDatabaseConnectionHealthy();


        assertFalse(result);
    }

    @Test
     void isDatabaseConnectionHealthy_shouldReturnFalseWhenQueryThrowsException() {

        when(jdbcTemplate.queryForObject("SELECT 1 FROM dual", Integer.class)).thenThrow(new RuntimeException("Some exception"));


        boolean result = invokeIsDatabaseConnectionHealthy();


        assertFalse(result);
    }

    @Test
     void isDatabaseConnectionHealthy_shouldReturnFalseWhenQueryThrowsExceptionWithNullMessage() {

        when(jdbcTemplate.queryForObject("SELECT 1 FROM dual", Integer.class)).thenThrow(new RuntimeException());

        boolean result = invokeIsDatabaseConnectionHealthy();


        assertFalse(result);
    }

    private boolean invokeIsDatabaseConnectionHealthy() {
        return ReflectionTestUtils.invokeMethod(customHealthIndicator, "isDatabaseConnectionHealthy");
    }
}
