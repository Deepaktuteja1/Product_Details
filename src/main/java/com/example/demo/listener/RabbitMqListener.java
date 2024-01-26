package com.example.demo.listener;

import com.example.demo.dto.ProductDTO;
import com.example.demo.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqListener {

    @Autowired
    private ProductService productService;

    @RabbitListener(queues = "Products")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);

        // Convert JSON message to ProductDTO
        ProductDTO receivedProduct = convertJsonToProductDTO(message);

        // Process the received ProductDTO by saving it to the database
        saveProductToDatabase(receivedProduct);
    }

    private ProductDTO convertJsonToProductDTO(String json) {
        try {
            // Use Jackson ObjectMapper to convert JSON to ProductDTO
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(json, ProductDTO.class);
        } catch (Exception e) {
            // Handle the exception appropriately (e.g., log it)
            e.printStackTrace();
            return null; // Return null or throw an exception based on your requirements
        }
    }

    private void saveProductToDatabase(ProductDTO productDTO) {
        if (productDTO != null) {
            // Save the received productDTO to the database using ProductService
            productService.saveProduct(productDTO);
            System.out.println("Saved product to the database: " + productDTO.toString());
            // Add your additional processing logic here if needed
        } else {
            System.out.println("Failed to save product to the database. ProductDTO is null.");
            // Handle the case where deserialization failed or productDTO is null
        }
    }
}
