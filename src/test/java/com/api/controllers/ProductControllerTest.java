package com.api.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import com.api.entities.Product;
import com.api.services.ProductServiceBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import javax.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(
	properties={"net.sf.ehcache.disabled=true"})
@SpringBootTest
@Transactional
public class ProductControllerTest {
 
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductServiceBean productServiceBean;
  
    @Test
    public void testGetProductLegacyOk() throws Exception {
    	productServiceBean.save(new Product("Tv","des",(float) 222.00,(float) 222.00,33,false));

       	this.mockMvc.perform(get("/legacy/products/1"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.name").value("Tv"));
    }

    @Test
    public void testGetProductLegacyNotFound() throws Exception {
    	this.mockMvc.perform(get("/legacy/products/99"))
		.andExpect(status().isNotFound());
    }

    @Test
    public void testPostProductLegacyAdd() throws Exception {
    	this.mockMvc.perform(
    			post("/legacy/product/add?price=22&stock=3&used=false&description=3erer&name=fff&list_price=33.3")
    	).andExpect(status().isOk());
    }

}