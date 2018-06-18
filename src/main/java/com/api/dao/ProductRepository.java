package com.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.api.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
