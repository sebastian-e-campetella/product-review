package com.api;

import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.EnableCaching;

@EnableAutoConfiguration
@EnableCaching
@SpringBootTest
@SpringBootApplication
public class ProductApiApplication {
	
    private static final Logger logger = Logger.getLogger("garbaLogger");

	public static void main(String[] args) {

		logger.info("garba application starting...");
		SpringApplication.run(ProductApiApplication.class, args);
	}
	
}
