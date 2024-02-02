package com.example.demo.utils;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    private final JdbcTemplate jdbcTemplate;

    public CustomHealthIndicator(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Health health() {
        if (isDatabaseConnectionHealthy()) {
            return Health.up().withDetail("message", "Database connection is healthy").build();
        } else {
            return Health.down().withDetail("message", "Database connection is not healthy").build();
        }
    }

    private boolean isDatabaseConnectionHealthy() {
        try {

            jdbcTemplate.queryForObject("SELECT 1 FROM dual", Integer.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
