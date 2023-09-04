package com.mk.crud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data
public class ProductDTO {
    private Long id;
    private String title;
    private String description;
    private Integer price;
    private Double discountPercentage;
    private Integer rating;
    private Integer stock;
    private String brand;
    private String category;
    private String thumbnail;
    private List<String> images;
}
