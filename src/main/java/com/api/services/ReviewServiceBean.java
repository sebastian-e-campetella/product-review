package com.api.services;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Review;
import com.api.dao.ReviewRepository;

@Service
public class ReviewServiceBean {
    @Autowired
	private ReviewRepository reviewRepository;

    @Transactional
    public Review save(Review review) {
        return reviewRepository.save(review);
    }
    
    public ReviewRepository reviewRepository() {
    	return this.reviewRepository;
    }
}