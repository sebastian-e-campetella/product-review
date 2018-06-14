package com.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.dao.ProductRepository;
import com.api.dao.ReviewRepository;
import com.api.entities.Product;
import com.api.entities.Review;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

@RestController
public class ReviewController {
	@Autowired 
	protected ProductRepository productRepository;
	@Autowired 
	protected ReviewRepository reviewRepository;

	@PostMapping(path="legacy/review/add") // Map ONLY POST Requests
	public @ResponseBody ResponseEntity<Review> addNewReview (@RequestParam String user
			, @RequestParam String review
			, @RequestParam Long product_id
      )
  {
	Optional<Product> p = productRepository.findById(product_id);
    Review r = new Review();
	r.setUser(user);
	r.setReview(review);
    r.setProduct(p.get());
    reviewRepository.save(r);
    return new ResponseEntity<Review>(r, HttpStatus.OK);
	}

	@GetMapping(path="legacy/review/all")
	public @ResponseBody ResponseEntity<Object> getAllReviews() {
    return new ResponseEntity<Object>(reviewRepository.findAll(), HttpStatus.OK);
	}
}
