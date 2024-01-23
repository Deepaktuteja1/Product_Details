package com.example.demo.controller;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
@Tag(name = "Health CheckUp", description = "Endpoints to retrieve information about the health of database")
@RestController
@RequestMapping("/products")
public class HealthController {

    @Autowired
    private HealthIndicator customHealthIndicator;

    @Operation(summary = "Get Health Status", description = "Returns the health status of the application.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation, returns health status."),
            @ApiResponse(responseCode = "500", description = "Internal server error.")
    })
    @GetMapping("/health")
    public Health getHealthStatus() {
        return customHealthIndicator.health();
    }
}
