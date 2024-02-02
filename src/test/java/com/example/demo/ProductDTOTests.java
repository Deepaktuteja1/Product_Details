package com.example.demo;
import com.example.demo.dto.ProductDTO;
import com.example.demo.model.Product;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

 class ProductDTOTests {

    @Test
    void testFromEntity() {
        // Initialization
        Product product = new Product(1L, "Product Name", "SKU123");

        // Execution
        ProductDTO productDTO = ProductDTO.fromEntity(product);

        // Verification
        assertEquals(product.getId(), productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getProductsku(), productDTO.getProductsku());
    }

    @Test
    void testToEntity() {
        // Initialization
        ProductDTO productDTO = new ProductDTO(1L, "Product Name", "SKU123");

        // Execution
        Product product = ProductDTO.toEntity(productDTO);

        // Verification
        assertEquals(productDTO.getId(), product.getId());
        assertEquals(productDTO.getProductsku(), product.getProductsku());
    }

    @Test
    void testFromEntityWithNullId() {
        // Initialization
        Product product = new Product(null, "Product Name", "SKU123");

        // Execution
        ProductDTO productDTO = ProductDTO.fromEntity(product);

        // Verification
        assertNull(productDTO.getId());
        assertEquals(product.getName(), productDTO.getName());
        assertEquals(product.getProductsku(), productDTO.getProductsku());
    }

    @Test
    void testToEntityWithNullId() {
        // Initialization
        ProductDTO productDTO = new ProductDTO(null, "Product Name", "SKU123");

        // Execution
        Product product = ProductDTO.toEntity(productDTO);

        // Verification
        assertNull(product.getId());
        assertEquals(productDTO.getProductsku(), product.getProductsku());
    }

    @Test
    void testEqualsAndHashCode() {
        // Initialization
        ProductDTO productDTO1 = new ProductDTO(1L, "Product Name", "SKU123");
        ProductDTO productDTO2 = new ProductDTO(1L, "Product Name", "SKU123");

        // Verification
        assertEquals(productDTO1, productDTO2);
        assertEquals(productDTO1.hashCode(), productDTO2.hashCode());
    }

    @Test
    void testEqualsAndHashCodeWithNulls() {
        // Initialization
        ProductDTO productDTO1 = new ProductDTO(null, null, null);
        ProductDTO productDTO2 = new ProductDTO(null, null, null);

        // Verification
        assertEquals(productDTO1, productDTO2);
        assertEquals(productDTO1.hashCode(), productDTO2.hashCode());
    }

    @Test
    void testNotEquals() {
        // Initialization
        ProductDTO productDTO1 = new ProductDTO(1L, "Product Name", "SKU123");
        ProductDTO productDTO2 = new ProductDTO(2L, "Another Product", "SKU456");

        // Verification
        assertNotEquals(productDTO1, productDTO2);
    }

    // Add more test cases as needed...
}
