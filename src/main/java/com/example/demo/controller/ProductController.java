
package com.example.demo.controller;

import com.example.demo.model.ProductDTO;
import com.example.demo.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Tag(name = "Product Details", description = "Endpoints for managing product details")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get all products", description = "Get a list of all products", tags = {"Product Details"})
    @GetMapping
    public List<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @Operation(summary = "Get a product by ID", description = "Get a product based on its ID", tags = {"Product Details"})
    @GetMapping("/{id}")
    public Optional<ProductDTO> getProductById(
            @PathVariable @Parameter(description = "ID of the product") Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Save a new product", description = "Save a new product", tags = {"Product Details"})
    @PostMapping
    public ProductDTO saveProduct(
            @RequestBody ProductDTO productDTO) {
        return productService.saveProduct(productDTO);
    }

    @Operation(summary = "Delete a product by ID", description = "Delete a product based on its ID", tags = {"Product Details"})
    @DeleteMapping("/{id}")
    public void deleteProduct(
            @PathVariable Long id) {
        productService.deleteProduct(id);
    }
}














