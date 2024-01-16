package com.example.demo.model;

import org.modelmapper.ModelMapper;

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

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductsku() {
        return productsku;
    }

    public void setProductsku(String productsku) {
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
