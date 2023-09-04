package com.mk.crud.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.crud.dto.PostDTO;
import com.mk.crud.dto.ProductDTO;
import com.mk.crud.dto.QuoteDTO;
import com.mk.crud.dto.ShowDataResponse;
import com.mk.crud.service.ApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
@Log4j2
public class ApiController {
    @Autowired
    private ApiService apiService;
    Map<String, String> response = new HashMap<>();
    @GetMapping("/fetch")
    public ResponseEntity<String> fetchCombinedData() {
        return apiService.fetchCombinedData();
    }

    @GetMapping("/saves")
    public ResponseEntity<Map<String, String>> saveData() {
        Map<String, String> response = new HashMap<>();
        response.put("message", "Data saved successfully11111111111.");
        return ResponseEntity.ok(response);
    }
    @GetMapping("/save")
    public ResponseEntity<Map<String, String>> saveDatas() {
        return apiService.save();
    }

    @GetMapping("/show")
    public ResponseEntity<String> showData() {
        List<ProductDTO> productDTOList = apiService.getAllProducts();
        List<PostDTO> postDTOList = apiService.getAllPosts();
        List<QuoteDTO> quoteDTOList = apiService.getAllQuotes();

        // Create a JSON response using ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonResponse = objectMapper.writeValueAsString(new ShowDataResponse(productDTOList, postDTOList, quoteDTOList));
            return ResponseEntity.ok(jsonResponse);
        } catch (Exception e) {
            log.error("An error occurred while get all data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping("/delete/product")
    public ResponseEntity<Map<String, String>> deleteProduct(@RequestParam Long id) {
        try {
            apiService.deleteProductById(id);
            response.put("message", "Product Data delete successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("An error occurred while deleting the product.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/post")
    public ResponseEntity<Map<String, String>> deletePost(@RequestParam Long id) {
        try {
            apiService.deletePostById(id);
            response.put("message", "Post Data delete successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("An error occurred while deleting the post.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/quote")
    public ResponseEntity<Map<String, String>> deleteQuote(@RequestParam Long id) {
        try {
            apiService.deleteQuoteById(id);
            response.put("message", "Quote Data delete successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("An error occurred while deleting the quote.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
