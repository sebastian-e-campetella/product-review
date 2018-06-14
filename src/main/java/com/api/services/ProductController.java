package com.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.api.dao.ProductRepository;
import com.api.entities.Product;

import java.net.URI;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.MediaType;



@RestController
public class ProductController {
	@Autowired 
	private ProductRepository productRepository;
	
	@PostMapping(value = "/legacy/product/add") // Map ONLY POST Requests
	public @ResponseBody ResponseEntity addNewProduct (@RequestParam String name
	  , @RequestParam String description
	  , @RequestParam Float price
	  , @RequestParam Float list_price
	  , @RequestParam Integer stock
	  , @RequestParam Boolean used
	  ) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
	
		Product p = new Product();
		p.setName(name);
		p.setDescription(description);
		p.setPrice(price);
		p.setListPrice(list_price);
		p.setStock(stock);
		p.setUsed(used);
	
		productRepository.save(p);
	    return new ResponseEntity<Object>(p, HttpStatus.OK);
	}

    @GetMapping(path="/legacy/products/all")
    @ResponseBody
	public ResponseEntity<Iterable<Product>> getAllProducts() {
		// This returns a JSON or XML with the users
    	Iterable<Product> products =  productRepository.findAll();
		return new ResponseEntity<Iterable<Product>>(products, HttpStatus.OK);
	}
//  @GetMapping(path="/hello", produces = MediaType.APPLICATION_JSON_VALUE)
	@RequestMapping("/")
	@ResponseBody
	public ResponseEntity<String> hello() {
		return new ResponseEntity<String>("Hello World", HttpStatus.OK);
	}
}
