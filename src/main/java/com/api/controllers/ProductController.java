package com.api.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.api.services.ProductServiceBean;
import com.api.dao.ProductRepository;
import com.api.entities.Product;
import com.api.entities.Review;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ProductController {
	@Autowired 
	private ProductServiceBean productServiceBean;

    private final static Logger logger = Logger.getLogger("garbaLogger");
	
	@PostMapping(path = "legacy/product/add") // Map ONLY POST Requests
	public @ResponseBody ResponseEntity<Product> addNewProduct (@RequestParam String name
	  , @RequestParam String description
	  , @RequestParam Float price
	  , @RequestParam Float list_price
	  , @RequestParam Integer stock
	  , @RequestParam Boolean used
	  ) {

		Product p = productServiceBean.save(
				new Product("Tv","des",(float) 222.00,(float) 222.00,33,false)
			);
	    return new ResponseEntity<Product>(p, HttpStatus.OK);
	}

   
	@Cacheable(value = "product_only")
	@GetMapping(path="legacy/products/{id}")
	public @ResponseBody ResponseEntity<Object> getProductLegacy(
		@PathVariable Long id,
		final HttpServletRequest request
	)
	{	
		try {
			Optional<Product> p = productServiceBean.productRepository().findById(id);
			p.orElseThrow(() -> {
				logger.error("product " + HttpStatus.NOT_FOUND + " " + request.getRequestURL() );
				return new HttpClientErrorException(HttpStatus.NOT_FOUND, "product not found");
			});
			return new ResponseEntity<Object>(p.get(), HttpStatus.OK);
		}catch(HttpClientErrorException e) {
			return new ResponseEntity<Object>(null,HttpStatus.NOT_FOUND);
		}
	}
	
	@Cacheable("product_reviews")
	@GetMapping(path="products/{id}")
	public @ResponseBody ResponseEntity<Object> getProduct(
		@PathVariable String id,
		final HttpServletRequest request
	)
	{	
		final String product_uri = "http://localhost:8080/legacy/products/"+id;
		final String review_uri  = "http://localhost:8080/legacy/reviews?product_id="+id;
		String productJsonStr ="";
		try {
			RestTemplate productRestTemplate = new RestTemplate();
		    HttpHeaders headers = new HttpHeaders();
		    headers.setContentType(MediaType.APPLICATION_JSON);
		    HttpEntity<Object> entity = new HttpEntity<Object>(headers);
			ResponseEntity<Product> response_product = productRestTemplate.exchange(product_uri, HttpMethod.GET, entity, Product.class);
	
			Product product_result = response_product.getBody();
			RestTemplate reviewRestTemplate = new RestTemplate();
			
			ResponseEntity<Review[]> response_reviews = reviewRestTemplate.exchange(review_uri, HttpMethod.GET, entity, Review[].class);
			Review[] reviews_result = response_reviews.getBody();
			
			Set<Review> targetSet = new HashSet<Review>(Arrays.asList(reviews_result));
			product_result.setReviews(targetSet);
			
			
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(MapperFeature.USE_ANNOTATIONS);
			try {
				productJsonStr = mapper.writeValueAsString(product_result);
			} catch (JsonProcessingException e) {
				logger.fatal(e.toString());
			}
		}catch(HttpStatusCodeException e) {
			logger.error("product " + HttpStatus.NOT_FOUND + " " + request.getRequestURL() );
			return new ResponseEntity<Object>("product not found",HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Object>(productJsonStr, HttpStatus.OK);
	}
}
