package com.mk.crud.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mk.crud.dto.CombinedData;
import com.mk.crud.dto.PostDTO;
import com.mk.crud.dto.ProductDTO;
import com.mk.crud.dto.QuoteDTO;
import com.mk.crud.entity.Post;
import com.mk.crud.entity.Product;
import com.mk.crud.entity.Quote;
import com.mk.crud.repository.PostsRepository;
import com.mk.crud.repository.ProductRepository;
import com.mk.crud.repository.QuoteRepository;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ApiServiceImpl implements ApiService {
    @Autowired
    private PostsRepository postsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private QuoteRepository quoteRepository;
    @Autowired
    private ModelMapper modelMapper;
    String postsUrl = "https://dummyjson.com/posts?limit=10";
    String productsUrl = "https://dummyjson.com/products?limit=10";
    String quotesUrl = "https://dummyjson.com/quotes?limit=10";

    //Fetches data from the specified API URL using a GET request.
    private ResponseEntity<String> fetchDataFromAPI(String apiUrl) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForEntity(apiUrl, String.class);
    }

/*    Converts a JSON response into a Java object of the specified type.
     <T>: The generic type of the resulting Java object.
     jsonResponse: The JSON response as a String.
     arrayKey: The key of the JSON array within the response.
     valueType: The class representing the type of object to convert to.*/
    private <T> T convertJsonResponseToObject(String jsonResponse, String arrayKey, Class<T> valueType) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode responseJson = objectMapper.readTree(jsonResponse);
        JsonNode dataArray = responseJson.get(arrayKey);
        return objectMapper.treeToValue(dataArray, valueType);
    }
    @Override
    public ResponseEntity<String> fetchCombinedData() {
        try {
            ResponseEntity<String> responsePosts = fetchDataFromAPI(postsUrl);
            PostDTO[] postDTOS = convertJsonResponseToObject(responsePosts.getBody(), "posts", PostDTO[].class);

            ResponseEntity<String> responseProducts = fetchDataFromAPI(productsUrl);
            ProductDTO[] productDTOS = convertJsonResponseToObject(responseProducts.getBody(), "products", ProductDTO[].class);

            ResponseEntity<String> responseQuotes = fetchDataFromAPI(quotesUrl);
            QuoteDTO[] quoteDTOS = convertJsonResponseToObject(responseQuotes.getBody(), "quotes", QuoteDTO[].class);

            // Create a CombinedDataResponse object with the fetched data
            CombinedData combinedData = new CombinedData(postDTOS, productDTOS, quoteDTOS);

            ObjectMapper objectMapper = new ObjectMapper();
            String combinedDataJson = objectMapper.writeValueAsString(combinedData);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            return ResponseEntity.ok().headers(headers).body(combinedDataJson);
        } catch (Exception e) {
            log.error("An error occurred while fetching data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Map<String, String>> save() {
        Map<String, String> response = new HashMap<>();

        try {
            ResponseEntity<String> responsePosts = fetchDataFromAPI(postsUrl);
            PostDTO[] postDTOs = convertJsonResponseToObject(responsePosts.getBody(), "posts", PostDTO[].class);
            // Convert PostDTO[] to List<Post>
            List<Post> postsToSave = Arrays.stream(postDTOs)
                    .map(this::convertPostDtoToEntity) // Call a method to convert PostDTO to Post
                    .collect(Collectors.toList());

            postsRepository.saveAll(postsToSave);

            ResponseEntity<String> responseProducts = fetchDataFromAPI(productsUrl);
            ProductDTO[] productDTOs = convertJsonResponseToObject(responseProducts.getBody(), "products", ProductDTO[].class);
            // Convert ProductDTO[] to List<Product>
            List<Product> productsToSave = Arrays.stream(productDTOs)
                    .map(this::convertProductDtoToEntity) // Call a method to convert ProductDTO to Product
                    .collect(Collectors.toList());

            productRepository.saveAll(productsToSave);

            ResponseEntity<String> responseQuotes = fetchDataFromAPI(quotesUrl);
            QuoteDTO[] quoteDTOs = convertJsonResponseToObject(responseQuotes.getBody(), "quotes", QuoteDTO[].class);
            // Convert QuoteDTO[] to List<Quote>
            List<Quote> quotesToSave = Arrays.stream(quoteDTOs)
                    .map(this::convertQuoteDtoToEntity) // Call a method to convert QuoteDTO to Quote
                    .collect(Collectors.toList());

            quoteRepository.saveAll(quotesToSave);
            response.put("message", "Data saved successfully.");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("An error occurred while saving data.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    //Convert Post Entity to DTO
    private PostDTO convertPostEntityToDto(Post post) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(post, PostDTO.class);
    }
    //Convert Post DTO to Entity
    private Post convertPostDtoToEntity(PostDTO postDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(postDTO, Post.class);
    }


    //Convert Product Entity to DTO
    private ProductDTO convertProductEntityToDto(Product product) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(product, ProductDTO.class);
    }
    //Convert Product DTO to Entity
    private Product convertProductDtoToEntity(ProductDTO productDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(productDTO, Product.class);
    }

    //Convert Quote Entity to DTO
    private QuoteDTO convertQuoteEntityToDto(Quote quote) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(quote, QuoteDTO.class);
    }
    //Convert Quote DTO to Entity
    private Quote convertQuoteDtoToEntity(QuoteDTO quoteDTO) {
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.LOOSE);
        return modelMapper.map(quoteDTO, Quote.class);
    }

    //Get all data in Product Table
    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
                .map(this::convertProductEntityToDto) // Call a method to convert Product to ProductDTO
                .collect(Collectors.toList());
    }

    //Get all data in Post Table
    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> postList = postsRepository.findAll();
        return postList.stream()
                .map(this::convertPostEntityToDto) // Call a method to convert Post to PostDTO
                .collect(Collectors.toList());
    }

    //Get all data in Quote Table
    @Override
    public List<QuoteDTO> getAllQuotes() {
        List<Quote> quoteList = quoteRepository.findAll();
        return quoteList.stream()
                .map(this::convertQuoteEntityToDto) // Call a method to convert Quote to QuoteDTO
                .collect(Collectors.toList());
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void deletePostById(Long id) {
        postsRepository.deleteById(id);
    }

    @Override
    public void deleteQuoteById(Long id) {
        quoteRepository.deleteById(id);
    }
}
