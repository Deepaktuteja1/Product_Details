package com.example.demo;
import com.example.demo.model.Product;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ProductTests {

    @Test
    void testNoArgsConstructor() {
        // Execution
        Product product = new Product();

        // Verification
        assertNull(product.getId());
        assertNull(product.getName());
        assertNull(product.getProductsku());
    }

    @Test
    void testAllArgsConstructor() {
        // Initialization
        Long id = 1L;
        String name = "Test Product";
        String productSku = "SKU123";

        // Execution
        Product product = new Product(id, name, productSku);

        // Verification
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(productSku, product.getProductsku());
    }

    @Test
    void testGettersAndSetters() {
        // Initialization
        Product product = new Product();

        // Set values using setters
        Long id = 1L;
        String name = "Test Product";
        String productSku = "SKU123";

        product.setId(id);
        product.setName(name);
        product.setProductsku(productSku);

        // Verification
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(productSku, product.getProductsku());
    }

    @Test
    void testEqualsAndHashCode() {
        // Initialization
        Product product1 = new Product(1L, "Product1", "SKU123");
        Product product2 = new Product(1L, "Product1", "SKU123");
        Product product3 = new Product(2L, "Product2", "SKU456");

        // Verification
        assertEquals(product1, product2);
        assertNotEquals(product1, product3);
        assertEquals(product1.hashCode(), product2.hashCode());
        assertNotEquals(product1.hashCode(), product3.hashCode());
    }


    @Test
    void testNoArgsConstructorWithSetter() {
        // Initialization
        Product product = new Product();
        Long id = 1L;
        String name = "Test Product";
        String productSku = "SKU123";

        // Set values using setters
        product.setId(id);
        product.setName(name);
        product.setProductsku(productSku);

        // Verification
        assertEquals(id, product.getId());
        assertEquals(name, product.getName());
        assertEquals(productSku, product.getProductsku());
    }

    @Test
    void testAllArgsConstructorWithNulls() {
        // Execution
        Product product = new Product(null, null, null);

        // Verification
        assertNull(product.getId());
        assertNull(product.getName());
        assertNull(product.getProductsku());
    }

    @Test
    void testEqualsAndHashCodeWithNulls() {
        // Initialization
        Product product1 = new Product(null, null, null);
        Product product2 = new Product(null, null, null);

        // Verification
        assertEquals(product1, product2);
        assertEquals(product1.hashCode(), product2.hashCode());
    }

    // Add more test cases as needed...
}
