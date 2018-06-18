package com.api.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity(name="Review")
@Table(name="reviews")
public class Review {	
    @SequenceGenerator(name = "review_gen", sequenceName = "review_seq", allocationSize = 1)
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "review_gen")
	private Long id;
	private String user;
	private String review;
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id")
	@JsonIgnore
	protected Product product;
    
    public Review(){}
  	
    public Review(String user, String review, Product product) {
  		this.user = user;
  		this.review = review;
		this.product = product;
  	}
        
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}
 
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
		
}

