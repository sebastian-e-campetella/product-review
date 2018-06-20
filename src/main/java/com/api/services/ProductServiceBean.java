package com.api.services;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.api.entities.Product;
import com.api.entities.Review;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.api.dao.ProductRepository;

@Service
public class ProductServiceBean {
    @Autowired
	private ProductRepository productRepository;
    
    private final static Logger logger = Logger.getLogger("garbaLogger");

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    public ProductRepository productRepository() {
    	return this.productRepository;
    }
    
    public Product getProduct(long id,final HttpServletRequest request ) {
		Optional<Product> oProduct = this.productRepository().findById((id));
		oProduct.orElseThrow(() -> {
			logger.error("product " + HttpStatus.NOT_FOUND + " " + request.getRequestURL() );
			return new HttpClientErrorException(HttpStatus.NOT_FOUND, "product not found");
		});
		
		return oProduct.get();
    }
    
    public String product_reviews(String id) {
    	final String product_uri = "http://localhost:8080/legacy/products/"+id;
		final String review_uri  = "http://localhost:8080/legacy/reviews?product_id="+id;
    	RestTemplate productRestTemplate = new RestTemplate();
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
		ResponseEntity<Product> response_product = productRestTemplate.exchange(product_uri, HttpMethod.GET, entity, Product.class);
		Product product_result = response_product.getBody();
		
		RestTemplate reviewRestTemplate = new RestTemplate();
		ResponseEntity<Review[]> response_reviews = reviewRestTemplate.exchange(review_uri, HttpMethod.GET, entity, Review[].class);
		Review[] reviews_result = response_reviews.getBody();
	
    	String productJsonStr = null;
    	Set<Review> targetSet = new HashSet<Review>(Arrays.asList(reviews_result));
		product_result.setReviews(targetSet);
		
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(MapperFeature.USE_ANNOTATIONS);
		try {
			productJsonStr = mapper.writeValueAsString(product_result);
		} catch (JsonProcessingException e) {
			logger.fatal(e.toString());
		}
		return productJsonStr;
	}
}