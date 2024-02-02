package com.example.demo;
import com.example.demo.dto.ProductDTO;
import com.example.demo.listener.RabbitMqListener;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
 class RabbitMqListenerTests {

    @Mock
    private ProductService productService;

    @InjectMocks
    private RabbitMqListener rabbitMqListener;

    @Test
    void testReceiveMessage_SuccessfulProcessing() {
        // Arrange
        String validJson = "{\"name\":\"Test Product\",\"productsku\":\"ABC123\"}";
        ProductDTO expectedProduct = new ProductDTO(null, "Test Product", "ABC123");

        // Act
        rabbitMqListener.receiveMessage(validJson);

        // Assert
        verify(productService, times(1)).saveProduct(expectedProduct);
    }


    @Test
    void testConvertJsonToProductDTO_ExceptionDuringConversion() {
        // Arrange
        String json = "{\"name\":\"Test Product\",\"productsku\":\"ABC123\"}";
        RabbitMqListener mockedListener = mock(RabbitMqListener.class);
        doThrow(new RuntimeException("Simulated exception")).when(mockedListener).convertJsonToProductDTO(anyString());

        // Act and Assert
        assertThrows(RuntimeException.class, () -> mockedListener.convertJsonToProductDTO(json));
    }

    @Test
    void testConvertJsonToProductDTO_SuccessfulConversion() {
        // Arrange
        String validJson = "{\"name\":\"Test Product\",\"productsku\":\"ABC123\"}";
        ProductDTO expectedProduct = new ProductDTO(null, "Test Product", "ABC123");

        // Act
        ProductDTO result = rabbitMqListener.convertJsonToProductDTO(validJson);

        // Assert
        assertEquals(expectedProduct, result);
    }

    @Test
    void testConvertJsonToProductDTO_InvalidJsonFormat() {
        // Arrange
        String invalidJson = "Invalid JSON";

        // Act
        ProductDTO result = rabbitMqListener.convertJsonToProductDTO(invalidJson);

        // Assert
        assertNull(result);
    }

    @Test
    void testReceiveMessage_NullProductDTO() {
        // Arrange
        String validJson = "{\"name\":\"Test Product\",\"productsku\":\"ABC123\"}";
        RabbitMqListener mockedListener = mock(RabbitMqListener.class, withSettings().lenient());
        doReturn(null).when(mockedListener).convertJsonToProductDTO(anyString());

        // Act
        mockedListener.receiveMessage(validJson);

        // Assert
        verify(mockedListener, never()).saveProductToDatabase(any());
    }


    @Test
    void testSaveProductToDatabase_SuccessfulSave() {
        // Arrange
        ProductDTO productDTO = new ProductDTO(null, "Test Product", "ABC123");

        // Act
        rabbitMqListener.saveProductToDatabase(productDTO);

        // Assert
        verify(productService, times(1)).saveProduct(productDTO);
    }



    @Test
    void testSaveProductToDatabase_ExceptionDuringSave() {
        // Arrange
        ProductDTO productDTO = new ProductDTO(null, "Test Product", "ABC123");
        doThrow(new RuntimeException("Simulated exception")).when(productService).saveProduct(any());

        // Act
        // Assert
        assertThrows(RuntimeException.class, () -> rabbitMqListener.saveProductToDatabase(productDTO));
    }

    // Add more tests as needed for additional coverage
}
