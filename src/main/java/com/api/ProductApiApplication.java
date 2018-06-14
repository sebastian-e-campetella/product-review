package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.log4j.Logger;



@SpringBootApplication
public class ProductApiApplication {
    private static final Logger logger = Logger.getLogger("garbaLogger");

	public static void main(String[] args) {
		logger.info("Inicio");
		SpringApplication.run(ProductApiApplication.class, args);
	}
}
