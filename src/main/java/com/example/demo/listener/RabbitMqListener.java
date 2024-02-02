package com.example.demo.listener;

import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class  RabbitMqListener {

    private static final Logger logger = LoggerFactory.getLogger(RabbitMqListener.class);

    private final ProductService productService;

    public RabbitMqListener(ProductService productService) {
        this.productService = productService;
    }

    @RabbitListener(queues = "Products")
    public void receiveMessage(String message) {
        logger.info("Received message: {}", message);

        // Convert JSON message to ProductDTO
        ProductDTO receivedProduct = convertJsonToProductDTO(message);

        // Process the received ProductDTO by saving it to the database
        saveProductToDatabase(receivedProduct);
    }

    public ProductDTO convertJsonToProductDTO(String json) {
        try {
            // Use Jackson ObjectMapper to convert JSON to ProductDTO
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ProductDTO.class);
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it)
            logger.error("Failed to convert JSON to ProductDTO", e);
            return null; // Return null or throw an exception based on your requirements
        }
    }

    public void saveProductToDatabase(ProductDTO productDTO) {
        if (productDTO != null) {
            logger.debug("Received ProductDTO: {}", productDTO);

            // Set product ID to null explicitly for new products
            productDTO.setId(null);

            // Save the received productDTO to the database using ProductService
            productService.saveProduct(productDTO);

            logger.info("Saved product to the database: {}", productDTO);
            // Add your additional processing logic here if needed
        } else {
            logger.warn("Failed to save product to the database. ProductDTO is null.");
            // Handle the case where deserialization failed or productDTO is null
        }
    }
}

