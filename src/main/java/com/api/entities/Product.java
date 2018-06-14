package com.api.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;
import java.util.Set;
import javax.persistence.OneToMany;
import javax.persistence.CascadeType;
import static javax.persistence.GenerationType.SEQUENCE;
import javax.persistence.SequenceGenerator;

@Entity(name="Product")
@Table(name="products")
public class Product {
  @SequenceGenerator(name = "product_gen", sequenceName = "product_seq", allocationSize = 1)
  @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_gen")
  private Long id;
  private String name;
  private String description;
  private Float price;
  private Float list_price;
  private Integer stock;
  private Boolean used;

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private Set<Review> reviews;


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Float getPrice() {
		return this.price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Float getListPrice() {
		return this.price;
	}

	public void setListPrice(Float price) {
		this.list_price = price;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getStock() {
		return this.stock;
	}

	public void setUsed(Boolean used) {
		this.used = used;
	}

	public Boolean getUsed() {
		return this.used;
	}

  public Set<Review> getReviews() {
      return reviews;
  }

  public void setReviews(Set<Review> reviews) {
      this.reviews = reviews;
  }
}

