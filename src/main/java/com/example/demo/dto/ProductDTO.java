package com.example.demo.dto;

import com.example.demo.model.Product;
import lombok.Data;


@Data
public class ProductDTO {

    private Long id;
    private String name;
    private String productsku;

    public ProductDTO() {
    }

    public ProductDTO(Long id, String name, String productsku) {
        this.id = id;
        this.name = name;
        this.productsku = productsku;
    }


public static ProductDTO fromEntity(Product product) {
    return new ProductDTO(
            product.getId(),
            product.getName(),
            product.getProductsku()
    );
}

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setProductsku(productDTO.getProductsku());
        return product;
    }



}
