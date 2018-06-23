package com.api.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.api.services.ProductServiceBean;
import com.api.entities.Product;

import javax.servlet.http.HttpServletRequest;


import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
public class ProductController {
	@Autowired 
	private ProductServiceBean productServiceBean;

    private final static Logger logger = Logger.getLogger("garbaLogger");
	
	@PostMapping(path = "legacy/product") // Map ONLY POST Requests
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
			return new ResponseEntity<Object>(productServiceBean.getProduct(id, request), HttpStatus.OK);
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
		try {
			String productJsonStr ="";
			productJsonStr = productServiceBean.product_reviews(id);
			return new ResponseEntity<Object>(productJsonStr, HttpStatus.OK);
		}catch(HttpStatusCodeException e) {
			logger.error("product " + HttpStatus.NOT_FOUND + " " + request.getRequestURL() );
			return new ResponseEntity<Object>("product not found",HttpStatus.NOT_FOUND);
		}
	}
}
