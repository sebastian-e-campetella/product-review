package com.api.controllers;

import java.util.Collection;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.api.dao.ProductRepository;
import com.api.dao.ReviewRepository;
import com.api.entities.Product;
import com.api.entities.Review;
import com.api.services.ProductServiceBean;
import com.api.services.ReviewServiceBean;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.cache.annotation.Cacheable;

@RestController
public class ReviewController {
	@Autowired 
	protected ProductServiceBean productServiceBean;
	@Autowired 
	protected ReviewServiceBean reviewServiceBean;

	@PostMapping(path="legacy/review/add")
	public @ResponseBody ResponseEntity<Review> addNewReview (
			@RequestParam String user,
			@RequestParam String review,
			@RequestParam Long product_id
      )
	{
		Optional<Product> oProd= productServiceBean.productRepository().findById(product_id);
		Review reviewObj = new Review(user, review, oProd.get());
	    reviewServiceBean.reviewRepository().save(reviewObj);
	    return new ResponseEntity<Review>(reviewObj, HttpStatus.OK);
	}

	@Cacheable("reviews_only")
	@GetMapping(path="legacy/reviews")
	@ResponseBody
	public ResponseEntity<Object> getAllReviewsForProduct(
		@RequestParam Long product_id,
		final HttpServletRequest request,
		final HttpServletResponse response
	)
	{

		
		if (!productServiceBean.productRepository().findById(product_id).isPresent()){
			response.setStatus(404);
			return new ResponseEntity<Object>( "reviews not found",HttpStatus.NOT_FOUND);
		}
			  
		Collection<Review> reviews = reviewServiceBean.reviewRepository().findByProductId(product_id);
		return new ResponseEntity<Object>(reviews.toArray(), HttpStatus.OK);
	}
}
