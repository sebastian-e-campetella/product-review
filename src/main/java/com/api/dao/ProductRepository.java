package com.api.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.api.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

}
