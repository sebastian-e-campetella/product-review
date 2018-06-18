
package com.api.controllers;


import org.apache.log4j.Logger;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.StandaloneMockMvcBuilder;
import org.springframework.web.client.HttpClientErrorException;

import com.api.dao.ProductRepository;
import com.api.entities.Product;
import com.api.services.ProductServiceBean;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.rmi.ServerError;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.server.ServerErrorException;


@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(
	properties={"net.sf.ehcache.disabled=true"})
@SpringBootTest
@Transactional
public class ProductControllerTest {
	private final static Logger logger = Logger.getLogger("garbaLogger");
    
	@Autowired
    private WebApplicationContext wac;
	 
	@Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductServiceBean productServiceBean;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }
             
    
    @Test
    public void testGetProductLegacyOk() throws Exception {
       	productServiceBean.save(
    				new Product("Tv","des",(float) 222.00,(float) 222.00,33,false)
    			);

    	this.mockMvc.perform(get("/legacy/products/1").
    			accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$.name").value("Tv"));
    }

    @Test
    public void testGetProductLegacyNotFound() throws Exception {
    	this.mockMvc.perform(get("/legacy/products/99"))
		.andExpect(status().isNotFound());
    }
    
}