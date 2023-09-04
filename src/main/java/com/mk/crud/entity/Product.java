package com.mk.crud.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name="product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @ElementCollection
    private List<String> images;
}
