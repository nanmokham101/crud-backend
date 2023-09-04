package com.mk.crud.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CombinedData {
    @JsonProperty("posts")
    private PostDTO[] posts;

    @JsonProperty("products")
    private ProductDTO[] products;

    @JsonProperty("quotes")
    private QuoteDTO[] quotes;

    public CombinedData(PostDTO[] posts, ProductDTO[] products, QuoteDTO[] quotes) {
        this.posts = posts;
        this.products = products;
        this.quotes = quotes;
    }
}
