package com.mk.crud.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
@Data

public class PostDTO {
    private Long id;
    private String title;
    private String body;
    private Long userId;
    private List<String> tags;
    private Integer reactions;
}
