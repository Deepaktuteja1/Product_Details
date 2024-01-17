package com.example.demo.model;

import lombok.Data;
import org.modelmapper.ModelMapper;

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
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(product, ProductDTO.class);
    }

    public Product toEntity() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, Product.class);
    }
}
