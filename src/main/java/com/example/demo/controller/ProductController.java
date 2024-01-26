package com.example.demo.controller;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Details", description = "Endpoints for managing product details")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Operation(summary = "Get all products", description = "Get a list of all products", tags = {"Product Details"})
    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @Operation(summary = "Get a product by ID", description = "Get a product based on its ID", tags = {"Product Details"})
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable @Parameter(description = "ID of the product") Long id) {
        Optional<ProductDTO> productDTO = productService.getProductById(id);
        return productDTO.map(dto -> new ResponseEntity<>(dto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Save a new product", description = "Save a new product", tags = {"Product Details"})
    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> saveProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO savedProductDTO = productService.saveProduct(productDTO);
        return new ResponseEntity<>(savedProductDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete a product by ID", description = "Delete a product based on its ID", tags = {"Product Details"})
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Save a new product by rabbitmq", description = "Save a new product by rabbitmq", tags = {"Product Details"})
    @PostMapping("/rabbitmqpost")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<ProductDTO> rabbitProduct(
            @RequestBody ProductDTO productDTO) {

        // Convert savedEmployeeDTO to JSON or any format you prefer
        String message = convertProductDTOToJson(productDTO);

        // Send the details to RabbitMQ queue
        rabbitTemplate.convertAndSend("Products", message);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private String convertProductDTOToJson(ProductDTO productDTO) {
        try {
            // Use Jackson ObjectMapper to convert EmployeeDTO to JSON
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(productDTO);
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it)
            e.printStackTrace();
            return ""; // Return an empty string or throw an exception based on your requirements
        }
    }


}
