package com.mk.crud.service;

import com.mk.crud.dto.PostDTO;
import com.mk.crud.dto.ProductDTO;
import com.mk.crud.dto.QuoteDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface ApiService {
    ResponseEntity<Map<String, String>> save();
    ResponseEntity<String> fetchCombinedData();

    List<ProductDTO> getAllProducts();

    List<PostDTO> getAllPosts();
    List<QuoteDTO> getAllQuotes();

    void deleteProductById(Long id);

    void deletePostById(Long id);

    void deleteQuoteById(Long id);
}
