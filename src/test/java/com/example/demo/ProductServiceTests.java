package com.example.demo;
import com.example.demo.dto.ProductDTO;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductServiceTests {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    void getAllProducts_ShouldReturnEmptyListWhenNoProducts() {
        // Arrange
        when(productRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<ProductDTO> result = productService.getAllProducts();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void getProductById_WithExistingId_ShouldReturnProductDTO() {
        // Arrange
        long productId = 1L;
        Product mockProduct = new Product(productId, "Product1", "SKU1");
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        Optional<ProductDTO> result = productService.getProductById(productId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Product1", result.get().getName());
    }

    @Test
    void getProductById_WithNonexistentId_ShouldReturnEmptyOptional() {
        // Arrange
        long nonexistentProductId = 999L;
        when(productRepository.findById(nonexistentProductId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.getProductById(nonexistentProductId));
    }


    @Test
    void saveProduct_ShouldReturnSavedProductDTO() {
        // Arrange
        ProductDTO productDTO = new ProductDTO(null, "NewProduct", "NewSKU");
        Product savedProduct = new Product(1L, "NewProduct", "NewSKU");
        when(productRepository.save(any())).thenReturn(savedProduct);

        // Act
        ProductDTO result = productService.saveProduct(productDTO);

        // Assert
        assertNotNull(result.getId());
        assertEquals("NewProduct", result.getName());
        assertEquals("NewSKU", result.getProductsku());
    }


    @Test
    void deleteProduct_WithValidId_ShouldDeleteProduct() {
        // Arrange
        long productId = 1L;
        Product mockProduct = new Product(productId, "Product1", "SKU1");
        when(productRepository.findById(productId)).thenReturn(Optional.of(mockProduct));

        // Act
        productService.deleteProduct(productId);

        // Assert
        verify(productRepository, times(1)).delete(mockProduct);
    }


    @Test
    void deleteProduct_WithInvalidId_ShouldThrowResourceNotFoundException() {
        // Arrange
        long invalidProductId = 999L;
        when(productRepository.findById(invalidProductId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> productService.deleteProduct(invalidProductId));

        // Verify that delete method was not called
        verify(productRepository, never()).delete(any());
    }

    @Test
    void convertToDTO_ShouldConvertProductToProductDTO() {
        // Arrange
        Product product = new Product(1L, "ConversionProduct", "ConversionSKU");

        // Act
        ProductDTO result = productService.convertToDTO(product);

        // Assert
        assertEquals(1L, result.getId());
        assertEquals("ConversionProduct", result.getName());
        assertEquals("ConversionSKU", result.getProductsku());
    }

    @Test
    void convertToEntity_ShouldConvertProductDTOToProduct() {
        // Arrange
        ProductDTO productDTO = new ProductDTO(null, "ConversionProductDTO", "ConversionSKUDTO");

        // Act
        Product result = productService.convertToEntity(productDTO);

        // Assert
        assertNull(result.getId());
        assertEquals("ConversionProductDTO", result.getName());
        assertEquals("ConversionSKUDTO", result.getProductsku());
    }
}

