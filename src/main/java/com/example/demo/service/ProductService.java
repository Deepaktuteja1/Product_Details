package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.Product;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;



@Service
public class ProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }
    @Cacheable("Products")
    public List<ProductDTO> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .toList();
    }

    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> Optional.ofNullable(convertToDTO(product)))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
    }

    @CacheEvict(value="Products",allEntries = true)
    @CachePut(value = "Products",key = "#result.id")
    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        productRepository.delete(product);
    }

    public ProductDTO convertToDTO(Product product) {
        if (product == null) {
            return null;
        }
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setProductsku(product.getProductsku());
        return productDTO;
    }



    public static Product convertToEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setProductsku(productDTO.getProductsku());
        return product;
    }
}
