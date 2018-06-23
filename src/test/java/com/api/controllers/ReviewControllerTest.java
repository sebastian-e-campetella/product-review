package com.api.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import com.api.entities.Product;
import com.api.entities.Review;
import com.api.services.ProductServiceBean;
import com.api.services.ReviewServiceBean;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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
public class ReviewControllerTest {	
    @Autowired
    private ProductServiceBean productServiceBean;
    @Autowired
    private ReviewServiceBean reviewServiceBean;
  	 
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetReviewsByIdProductOk() throws Exception {
		Product product = productServiceBean.save(
				new Product("Celular","one description",(float) 222.00,(float) 222.00,33,false)
			);
		reviewServiceBean.save(
				new Review("user1","a good review!", product)
			);

      	this.mockMvc.perform(get("/legacy/reviews?product_id=1"))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.[0].user").value("user1"));
    }
    
    @Test
    public void testGetReviewsByIdProductNotFoud() throws Exception {
    	this.mockMvc.perform(get("/legacy/reviews?product_id=6"))
    		.andExpect(status().isNotFound());
    }
    
    @Test
    public void testPostProductLegacyAdd() throws Exception {
		Product p = productServiceBean.save(
				new Product("Tv","one description",(float) 222.00,(float) 222.00,33,true)
			);
		assertEquals((long)p.getId(),2L);
    	this.mockMvc.perform(
    			post("/legacy/review?user=user_one&review=reviewed&product_id=2")
    	).andExpect(status().isOk());
    }
}
