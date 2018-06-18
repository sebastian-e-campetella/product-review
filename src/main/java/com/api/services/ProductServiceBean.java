package com.api.services;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.entities.Product;
import com.api.dao.ProductRepository;

@Service
public class ProductServiceBean {
    @Autowired
	private ProductRepository productRepository;

    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    public ProductRepository productRepository() {
    	return this.productRepository;
    }
}