package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.dto.ProductDTO;
import com.example.demo.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final EmailService emailService; // Inject EmailService

    @Autowired
    public ProductService(ProductRepository productRepository, EmailService emailService) {
        this.productRepository = productRepository;
        this.emailService = emailService;
    }

    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();

        // Send an email when all products are retrieved
        String emailTo = "recipient@example.com";
        String subject = "All Products Retrieved";
        String body = "List of all products: " + products.toString();

        emailService.sendSimpleMessage(emailTo, subject, body);

        return products.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ProductDTO> getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Optional<ProductDTO> productDTO = productOptional.map(this::convertToDTO);

        // Send an email when a product is retrieved by ID
        productDTO.ifPresent(dto -> {
            String emailTo = "recipient@example.com";
            String subject = "Product Retrieved";
            String body = "Product details: " + dto.toString();

            emailService.sendSimpleMessage(emailTo, subject, body);
        });

        return productDTO;
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        Product savedProduct = productRepository.save(product);

        // Send an email when a new product is saved
        String emailTo = "recipient@example.com";
        String subject = "New Product Saved";
        String body = "New product details: " + savedProduct.toString();

        emailService.sendSimpleMessage(emailTo, subject, body);

        return convertToDTO(savedProduct);
    }

    public void deleteProduct(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        productOptional.ifPresent(product -> {
            productRepository.deleteById(id);

            // Send an email when a product is deleted
            String emailTo = "recipient@example.com";
            String subject = "Product Deleted";
            String body = "Product with ID " + id + " has been deleted.";

            emailService.sendSimpleMessage(emailTo, subject, body);
        });
    }

    public ProductDTO convertToDTO(Product product) {
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
