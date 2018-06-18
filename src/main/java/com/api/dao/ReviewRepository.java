package com.api.dao;

import java.util.Collection;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.entities.Review;

@Repository("reviewRepository")
public interface ReviewRepository extends CrudRepository<Review, Long> {
	Collection<Review> findByProductId(Long product_id);
}
