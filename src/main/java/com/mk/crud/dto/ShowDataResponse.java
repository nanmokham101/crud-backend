package com.mk.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mk.crud.dto.PostDTO;
import com.mk.crud.dto.ProductDTO;
import com.mk.crud.dto.QuoteDTO;

import java.util.List;

public class ShowDataResponse {
    @JsonProperty("products")
    private List<ProductDTO> products;
    @JsonProperty("posts")
    private List<PostDTO> posts;
    @JsonProperty("quotes")
    private List<QuoteDTO> quotes;

    public ShowDataResponse(List<ProductDTO> products, List<PostDTO> posts, List<QuoteDTO> quotes) {
        this.products = products;
        this.posts = posts;
        this.quotes = quotes;
    }
}
