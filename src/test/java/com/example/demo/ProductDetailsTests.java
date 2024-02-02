package com.example.demo;

import com.example.demo.controller.ProductController;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;

 class ProductDetailsTests {

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}
	@Test
	 void testGetAllProducts() {
		// Mock data
		List<ProductDTO> mockProducts = Arrays.asList(new ProductDTO(), new ProductDTO());
		when(productService.getAllProducts()).thenReturn(mockProducts);

		// Test
		ResponseEntity<List<ProductDTO>> responseEntity = productController.getAllProducts();

		// Assertions
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(mockProducts, responseEntity.getBody());
	}

	@Test
	 void testGetProductByIdFound() {
		// Mock data
		ProductDTO mockProduct = new ProductDTO();
		when(productService.getProductById(1L)).thenReturn(Optional.of(mockProduct));

		// Test
		ResponseEntity<ProductDTO> responseEntity = productController.getProductById(1L);

		// Assertions
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(mockProduct, responseEntity.getBody());
	}

	@Test
	 void testGetProductByIdNotFound() {
		// Mock data
		when(productService.getProductById(1L)).thenReturn(Optional.empty());

		// Test
		ResponseEntity<ProductDTO> responseEntity = productController.getProductById(1L);

		// Assertions
		assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
	}

	@Test
	 void testSaveProduct() {
		// Mock data
		ProductDTO inputProduct = new ProductDTO();
		ProductDTO savedProduct = new ProductDTO();
		when(productService.saveProduct(inputProduct)).thenReturn(savedProduct);

		// Test
		ResponseEntity<ProductDTO> responseEntity = productController.saveProduct(inputProduct);

		// Assertions
		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(savedProduct, responseEntity.getBody());
	}

	@Test
	 void testDeleteProduct() {
		// Test
		ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);

		// Assertions
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}




	@Test
	 void testDeleteProductSuccessful() {
		// Mock behavior
		Mockito.doNothing().when(productService).deleteProduct(1L);

		// Test
		ResponseEntity<Void> responseEntity = productController.deleteProduct(1L);

		// Assertions
		assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
	}



	@Test
	void testConvertProductDTOToJson_Success() {
		// Setup
		ProductDTO productDTO = new ProductDTO();

		// Execution
		String json = productController.convertProductDTOToJson(productDTO);

		// Verification
		assertNotNull(json);

	}


}
