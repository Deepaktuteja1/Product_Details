package com.example.demo.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
@Tag(name = "Database Details", description = "Endpoints to retrieve information about the connected database")
@RestController
@RequestMapping("/data")
public class DatabaseInfoController {

    @Autowired
    private DataSource dataSource;
@Operation(summary = "Get Database Information", description = "Retrieve information about the connected database.")
    @GetMapping("/fetch")
    public String getDatabaseInfo() {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();

            String databaseProductName = metaData.getDatabaseProductName();
            String databaseProductVersion = metaData.getDatabaseProductVersion();
            String databaseUrl = metaData.getURL();

            return "Database Type: " + databaseProductName + "\n"
                    + "Database Version: " + databaseProductVersion + "\n"
                    + "Database URL: " + databaseUrl;
        } catch (SQLException e) {
            // Handle SQLException
            return "Failed to retrieve database information.";
        }
    }
}
