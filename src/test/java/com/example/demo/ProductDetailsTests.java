package com.example.demo;

import com.example.demo.controller.ProductController;
import com.example.demo.model.ProductDTO;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@Profile("qa")
class ProductDetailsTests{

	@Mock
	private ProductService productService;

	@InjectMocks
	private ProductController productController;

	private final MockMvc mockMvc;

	private final ObjectMapper objectMapper = new ObjectMapper();

	public ProductDetailsTests() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
	}

	@Test
	void testGetAllProducts() throws Exception {
		when(productService.getAllProducts()).thenReturn(Arrays.asList(
				new ProductDTO(1L, "Product1", "SKU1"),
				new ProductDTO(2L, "Product2", "SKU2")
		));

		mockMvc.perform(get("/products"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$[0].name").value("Product1"))
				.andExpect(jsonPath("$[1].productsku").value("SKU2"));

		verify(productService, times(1)).getAllProducts();
		verifyNoMoreInteractions(productService);
	}

	@Test
	void testGetProductById() throws Exception {
		Long productId = 1L;
		ProductDTO productDTO = new ProductDTO(productId, "Product1", "SKU1");

		when(productService.getProductById(productId)).thenReturn(Optional.of(productDTO));

		mockMvc.perform(get("/products/{id}", productId))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.name").value("Product1"))
				.andExpect(jsonPath("$.productsku").value("SKU1"));

		verify(productService, times(1)).getProductById(productId);
		verifyNoMoreInteractions(productService);
	}

	@Test
	void testGetProductByIdNotFound() throws Exception {
		Long nonExistingProductId = 99L;

		when(productService.getProductById(nonExistingProductId)).thenReturn(Optional.empty());

		mockMvc.perform(get("/products/{id}", nonExistingProductId))
				.andExpect(status().isNotFound());

		verify(productService, times(1)).getProductById(nonExistingProductId);
		verifyNoMoreInteractions(productService);
	}

	@Test
	void testSaveProduct() throws Exception {
		ProductDTO productDTO = new ProductDTO(null, "New Product", "NewSKU");
		ProductDTO savedProductDTO = new ProductDTO(1L, "New Product", "NewSKU");

		when(productService.saveProduct(any(ProductDTO.class))).thenReturn(savedProductDTO);

		mockMvc.perform(post("/products")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(productDTO)))
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(1L))
				.andExpect(jsonPath("$.name").value("New Product"))
				.andExpect(jsonPath("$.productsku").value("NewSKU"));

		verify(productService, times(1)).saveProduct(any(ProductDTO.class));
		verifyNoMoreInteractions(productService);
	}

	@Test
	void testDeleteProduct() throws Exception {
		Long productId = 1L;

		mockMvc.perform(delete("/products/{id}", productId))
				.andExpect(status().isNoContent()); // Use isNoContent() instead of isOk()

		verify(productService, times(1)).deleteProduct(productId);
		verifyNoMoreInteractions(productService);
	}

}




