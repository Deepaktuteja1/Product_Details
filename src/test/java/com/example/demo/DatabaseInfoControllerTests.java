package com.example.demo;
import com.example.demo.controller.DatabaseInfoController;
import org.junit.jupiter.api.Test;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DatabaseInfoControllerTests {

    @Test
    void testGetDatabaseInfo_Success() throws SQLException {
        // Mocking
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        DatabaseMetaData metaData = mock(DatabaseMetaData.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.getMetaData()).thenReturn(metaData);
        when(metaData.getDatabaseProductName()).thenReturn("H2");
        when(metaData.getDatabaseProductVersion()).thenReturn("1.8.1");
        when(metaData.getURL()).thenReturn("jdbc:h2:mem:testdb");

        // Execution
        DatabaseInfoController controller = new DatabaseInfoController(dataSource);
        String response = controller.getDatabaseInfo();

        // Verification
        assertTrue(response.contains("Database Type: H2"));
        assertTrue(response.contains("Database Version: 1.8.1"));
        assertTrue(response.contains("Database URL: jdbc:h2:mem:testdb"));
    }

    @Test
    void testGetDatabaseInfo_Failure() throws SQLException {
        // Mocking
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException("Connection failed"));

        // Execution
        DatabaseInfoController controller = new DatabaseInfoController(dataSource);
        String response = controller.getDatabaseInfo();

        // Verification
        assertTrue(response.contains("Failed to retrieve database information."));
    }

    @Test
    void testGetDatabaseInfo_ExceptionHandling() throws SQLException {
        // Mocking
        DataSource dataSource = mock(DataSource.class);
        when(dataSource.getConnection()).thenThrow(new SQLException("Connection failed"));

        // Execution
        DatabaseInfoController controller = new DatabaseInfoController(dataSource);
        String response = controller.getDatabaseInfo();

        // Verification
        assertTrue(response.contains("Failed to retrieve database information."));
    }


    @Test
    void testGetDatabaseInfo_ConnectionClosedAndException() throws SQLException {
        // Mocking
        DataSource dataSource = mock(DataSource.class);
        Connection connection = mock(Connection.class);
        when(dataSource.getConnection()).thenReturn(connection);
        when(connection.isClosed()).thenReturn(true);
        when(connection.getMetaData()).thenThrow(new SQLException("Connection closed"));

        // Execution
        DatabaseInfoController controller = new DatabaseInfoController(dataSource);
        String response = controller.getDatabaseInfo();

        // Verification
        assertTrue(response.contains("Failed to retrieve database information."));
    }

    // Add more test cases as needed...
}

