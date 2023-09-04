package com.mk.crud.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="quote")
public class Quote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String quote;
    private String author;
}
