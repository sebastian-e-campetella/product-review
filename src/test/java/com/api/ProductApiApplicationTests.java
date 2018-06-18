package com.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.api.dao.ProductRepository;
import com.api.entities.Product;
import com.api.entities.Review;
import com.api.services.ProductServiceBean;
import com.api.services.ReviewServiceBean;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@TestPropertySource(properties={"net.sf.ehcache.disabled=true"})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Transactional
public class ProductApiApplicationTests {
	@Value("${server.port:8080}")
    private int port = 8080;
	
	@Autowired
    private MockMvc mockMvc;

    private final static Logger logger = Logger.getLogger("garbaLogger");
   
    @Autowired
    private ProductServiceBean productServiceBean;
    @Autowired
    private ReviewServiceBean reviewServiceBean;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();
	
	@Test
	public void testRequest() throws Exception {

		Product product = new Product("Tv","des",(float) 222.00,(float) 222.00,33,false);
		productServiceBean.save(product);
		reviewServiceBean.save(
				new Review("user1","review 1",product)
			);
		assertEquals( productServiceBean.productRepository().count(),1L);
		
//    	HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);
//		ResponseEntity<Object> response = restTemplate.exchange(
//				createURLWithPort("products/1"),
//				HttpMethod.GET, entity, Object.class);
//		String expected = "Tv";
//		assertEquals(response.getBody().toString().contains(expected),true );
	
	}

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + "/" +uri;
	}

    
}
